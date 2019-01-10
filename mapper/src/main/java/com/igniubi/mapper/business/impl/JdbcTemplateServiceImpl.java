package com.igniubi.mapper.business.impl;
import com.alibaba.fastjson.JSON;
import com.igniubi.common.utils.DateUtil;
import com.igniubi.common.utils.DateUtils;
import com.igniubi.mapper.bean.ColumnBean;
import com.igniubi.mapper.bean.TableBean;
import com.igniubi.mapper.constant.RedisKeyConstant;
import com.igniubi.mapper.enums.ColumnTypeEnum;
import com.igniubi.mapper.service.DatabaseInfoService;
import com.igniubi.mapper.util.MapperDateUtil;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.igniubi.mapper.business.JdbcTemplateService;
import com.igniubi.mapper.constant.DataSourceConstant;
import com.igniubi.mapper.model.DatabaseInfo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * jdbc 对象维护接口
 * <p>
 *
 * @author 徐擂
 * @version 1.0.0
 * @date 2018-12-20
 */
@Service
public class JdbcTemplateServiceImpl implements JdbcTemplateService {

    private static final Logger log  = LoggerFactory.getLogger(JdbcTemplateServiceImpl.class);
    
    @Autowired
    private DatabaseInfoService databaseInfoService;

    /**
     * 维护一个map，用于缓存连接对象
     */
    private static Map<String, Object> cacheTemplate = new HashMap<>();


    /**
     * 获取jdbc数据连接
     *
     * @param id
     * @return JdbcTemplate
     * @author 徐擂
     * @version 1.0.0
     * @date 2018-12-20
     */
    private JdbcTemplate getJdbcTemplate(Integer id) {
        // 组装cache-key
        String cacheKey = getKey(id);
        // 缓存中有则优先从缓存拿
        JdbcTemplate jdbcTemplate = (JdbcTemplate) cacheTemplate.get(cacheKey);
        if (jdbcTemplate == null) {
            // 否则获取连接
            jdbcTemplate = new JdbcTemplate(createDataSource(databaseInfoService.get(id)));
            cacheTemplate.put(cacheKey, jdbcTemplate);
        }
        return jdbcTemplate;
    }

    /**
     * 获取数据库所有表结构
     * <p>
     *
     * @param id
     * @return
     * @author  徐擂
     * @date    2018-12-21
     * @version  1.0.0
     */
    @Override
    public List<TableBean> getTableByDatabaseId(Integer id) {
        // 获取对应数据库的连接对象
        JdbcTemplate jdbcTemplate = getJdbcTemplate(id);
        if (jdbcTemplate == null) {
            return null;
        }
        List<TableBean> tableBeans = new ArrayList<>();
        StringBuffer querySql = new StringBuffer();
        querySql.append("select t.table_name, t.engine, t.table_comment, t.create_time from information_schema.tables t ");
        querySql.append("where table_schema = (select database()) ");
        log.info("query SQL is : " + querySql.toString());
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(querySql.toString());
        log.info("query success !");
        TableBean tableBean = null;
        for (Map<String, Object> row : rows) {
            tableBean = new TableBean();
            tableBean.setTableName((String) row.get("table_name"));
            tableBean.setEngine((String) row.get("engine"));
            tableBean.setTableComment((String) row.get("table_comment"));
            Timestamp createDate = (Timestamp) row.get("create_time");
            tableBean.setCreateTime(MapperDateUtil.formatDate(createDate, null));
            tableBeans.add(tableBean);
        }

        log.info("获取到数据表信息: {}", JSON.toJSONString(tableBeans));
        return tableBeans;
    }

    /**
     * 代码生成
     *
     * @param id
     * @param tableNames
     * @return byte[]
     * @author 徐擂
     * @date 2018-12-21
     * @version 1.0.0
     */
    @Override
    public byte[] genCode(Integer id, String[] tableNames) {
        // 获取对应数据库的连接对象
        JdbcTemplate jdbcTemplate = getJdbcTemplate(id);
        if (jdbcTemplate == null) {
            return null;
        }
        // 查询数据库信息
        DatabaseInfo databaseInfo = databaseInfoService.get(id);
        // 定义输出流
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        // 处理需要生成代码的表
        for (String tableName : tableNames) {
            // 查询表信息
            TableBean tableBean = getTableInfoByTableName(jdbcTemplate, tableName);
            // 查询列信息
            List<ColumnBean> columns = queryColumnInfoByTableName(jdbcTemplate, tableName);
            // 生成代码
            generatorCode(tableBean, columns, databaseInfo.getPackageUrl(), zip);
        }
        IOUtils.closeQuietly(zip);
        return outputStream.toByteArray();
    }

    private void generatorCode(TableBean tableBean, List<ColumnBean> columns, String packageUrl, ZipOutputStream zip) {
        boolean hasBigDecimal = false;
        // 表名转换成Java类名
        String className = WordUtils.capitalizeFully(tableBean.getTableName(), new char[]{'_'}).replace("_", "");
        // 类名，例如sys_user --> SysUser;
        tableBean.setClassNameFirstUp(className);
        // 类名，例如sys_user --> sysUser;
        tableBean.setClassNameFirstDown(StringUtils.uncapitalize(className));
        // 列信息
        for (ColumnBean column : columns) {
            // 列名转换成Java属性名
            String attrName = columnToJava(column.getColumnName());
            // 属性名称(第一个字母大写)，如：user_name => UserName
            column.setAttrNameFirstUp(attrName);
            // 属性名称(第一个字母小写)，如：user_name => userName
            column.setAttrNameFirstDown(StringUtils.uncapitalize(attrName));
            // 列的数据类型，转换成Java类型，如果查找不到就是unknowType
            String attrType = ColumnTypeEnum.getJavaTypeByColumnType(column.getDataType());
            column.setAttrType(attrType);
            if ("BigDecimal".equals(attrType)) {
                hasBigDecimal = true;
            }
            // 是否主键
            if ("PRI".equalsIgnoreCase(column.getColumnKey()) && tableBean.getPk() == null) {
                tableBean.setPk(column);
            }
        }
        // 将所有列信息封装到表实体中
        tableBean.setColumnEntityList(columns);
        // 数据渲染
        renderData(tableBean, packageUrl, hasBigDecimal, zip);
    }

    /**
     * 对velocity模板进行数据渲染
     *
     * @param tableBean
     * @param packageUrl
     * @param hasBigDecimal
     * @param zip
     */
    private void renderData(TableBean tableBean, String packageUrl, boolean hasBigDecimal, ZipOutputStream zip) {
        //设置velocity资源加载器
        Properties prop = new Properties();
        prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(prop);
        packageUrl = StringUtils.isBlank(packageUrl) ? DataSourceConstant.PACKAGE_URL_DEFAULT : packageUrl;
        //封装模板数据
        Map<String, Object> map = new HashMap<>();
        map.put("tableName", tableBean.getTableName());
        map.put("tableComment", tableBean.getTableComment());
        map.put("pk", tableBean.getPk());
        map.put("className", tableBean.getClassNameFirstUp());
        map.put("classname", tableBean.getClassNameFirstDown());
        map.put("pathName", tableBean.getClassNameFirstDown().toLowerCase());
        map.put("columns", tableBean.getColumnEntityList());
        map.put("hasBigDecimal", hasBigDecimal);
        map.put("package", packageUrl);
        map.put("author", DataSourceConstant.AUTHOR_DEFAULT);
        map.put("datetime", DateUtils.getCurrentTime(DateUtils.FORMAT_DATE));
        map.put("pkType", tableBean.getPk().getAttrType());
        VelocityContext context = new VelocityContext(map);
        //获取模板列表
        List<String> templates = getTemplates();
        writerForZip(templates, tableBean, context, zip, packageUrl);
    }

    /**
     * 生成zip文件
     *
     * @param templates
     * @param tableBean
     * @param context
     * @param zip
     * @param packageUrl
     */
    private void writerForZip(List<String> templates, TableBean tableBean, VelocityContext context, ZipOutputStream zip, String packageUrl) {
        for (String template : templates) {
            //渲染模板
            StringWriter sw = new StringWriter();
            Velocity.getTemplate(template, "UTF-8").merge(context, sw);
            try {
                //添加到zip
                zip.putNextEntry(new ZipEntry(getFileName(template, tableBean.getClassNameFirstUp(), packageUrl)));

                IOUtils.write(sw.toString(), zip, "UTF-8");
                IOUtils.closeQuietly(sw);
                zip.closeEntry();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 获取文件名称
     *
     * @param template
     * @param classNameFirstUp
     * @param packageUrl
     * @return
     */
    private String getFileName(String template, String classNameFirstUp, String packageUrl) {
        // 组装前缀 最终文件名称形如：
        // 1.java类 main/java/包名/类名
        // 2.xml main/resources/mapper/*Mapper.xml
        String packagePath = "main" + File.separator + "java" + File.separator;
        // 将包路径转换为文件夹路径
        if (StringUtils.isNotBlank(packageUrl)) {
            packagePath += packageUrl.replace(".", File.separator) + File.separator;
        }
        String templateName = template.split("/")[2];
        switch (templateName) {
            case "Entity.java.vm":
                return packagePath + "entity" + File.separator + classNameFirstUp + "Entity.java";
            case "Mapper.java.vm":
                return packagePath + "mapper" + File.separator + classNameFirstUp + "Mapper.java";
            case "Service.java.vm":
                return packagePath + "service" + File.separator + classNameFirstUp + "Service.java";
            case "ServiceImpl.java.vm":
                return packagePath + "service" + File.separator + "impl" + File.separator + classNameFirstUp + "ServiceImpl.java";
            case "Mapper.xml.vm":
                return "main" + File.separator + "resources" + File.separator + "mapper" + File.separator + classNameFirstUp + "Mapper.xml";
            case "BaseEntity.java.vm":
                return packagePath + "entity" + File.separator + "BaseEntity.java";
            case "BaseMapper.java.vm":
                return packagePath + "mapper" + File.separator + "BaseMapper.java";
            case "BaseService.java.vm":
                return packagePath + "service" + File.separator + "BaseService.java";
            case "BaseServiceImpl.java.vm":
                return packagePath + "service" + File.separator + "impl" + File.separator + "BaseServiceImpl.java";
            case "pom.xml.vm":
                return File.separator + "pom.xml";
            default:
                return null;
        }
    }

    /**
     * 获取所有velocity模板
     *
     * @return
     */
    private List<String> getTemplates() {
        List<String> templates = new ArrayList<String>();
        templates.add("templates/velocity/Entity.java.vm");
        templates.add("templates/velocity/Mapper.java.vm");
        templates.add("templates/velocity/Mapper.xml.vm");
        templates.add("templates/velocity/Service.java.vm");
        templates.add("templates/velocity/ServiceImpl.java.vm");
        return templates;
    }

    /**
     * 将以"_"进行分割的表字段转成驼峰命名(首字母大写)
     *
     * @param columnName
     * @return
     */
    private String columnToJava(String columnName) {
        return WordUtils.capitalizeFully(columnName, new char[]{'_'}).replace("_", "");
    }

    /**
     * 查询表的字段信息
     *
     * @param jdbcTemplate
     * @param tableName
     * @return
     */
    private List<ColumnBean> queryColumnInfoByTableName(JdbcTemplate jdbcTemplate, String tableName) {
        List<ColumnBean> columnBeans = new ArrayList<>();
        StringBuffer querySql = new StringBuffer();
        querySql.append("select col.column_name, col.data_type, col.column_comment, col.column_key, col.extra from information_schema.columns col ");
        querySql.append("where table_schema = (select database()) ");
        if (StringUtils.isNotEmpty(tableName)) {
            querySql.append("and table_name = '").append(tableName).append("'");
        }
        log.info("Query sql is : " + querySql.toString());
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(querySql.toString());
        ColumnBean columnBean = null;
        for (Map<String, Object> row : rows) {
            columnBean = new ColumnBean();
            columnBean.setColumnName((String) row.get("column_name"));
            columnBean.setDataType((String) row.get("data_type"));
            columnBean.setColumnComment((String) row.get("column_comment"));
            columnBean.setColumnKey((String) row.get("column_key"));
            columnBean.setExtra((String) row.get("extra"));
            columnBeans.add(columnBean);
        }
        return columnBeans;
    }

    /**
     * 根据表名查询表信息
     *
     * @param jdbcTemplate
     * @param tableName
     * @return
     */
    private TableBean getTableInfoByTableName(JdbcTemplate jdbcTemplate, String tableName) {

        StringBuffer querySql = new StringBuffer();
        querySql.append("select t.table_name, t.engine, t.table_comment, t.create_time from information_schema.tables t ");
        querySql.append("where table_schema = (select database()) ");
        querySql.append("and table_name = ? ");

        log.info("Query sql is : " + querySql.toString());

        TableBean tableBean = jdbcTemplate.queryForObject(querySql.toString(), new RowMapper<TableBean>() {
            @Override
            public TableBean mapRow(ResultSet rs, int rowNum) throws SQLException {
                TableBean tableBean = new TableBean();
                tableBean.setTableName((String) rs.getString("table_name"));
                tableBean.setEngine((String) rs.getString("engine"));
                tableBean.setTableComment((String) rs.getString("table_comment"));
                Timestamp timestamp = (Timestamp) rs.getTimestamp("create_time");
                tableBean.setCreateTime(MapperDateUtil.formatDate(timestamp, null));
                return tableBean;
            }
        }, tableName);
        return tableBean;
    }

    /**
     * 组装连接属性
     *
     * @param databaseInfo
     * @return DataSource
     * @author 徐擂
     * @version 1.0.0
     * @date 2018-12-20
     */
    private DataSource createDataSource(DatabaseInfo databaseInfo) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(DataSourceConstant.JDBC_DRIVER_CLASS);
        dataSource.setUrl(getJdbcUrl(databaseInfo));
        dataSource.setUsername(databaseInfo.getUserName());
        dataSource.setPassword(databaseInfo.getPassword());
        return dataSource;
    }

    /**
     * 拼装jdbc url  oracle暂不支持
     *
     * @param databaseInfo
     * @return
     * @author 徐擂
     * @version 1.0.0
     * @date 2018-12-20
     */
    private String getJdbcUrl(DatabaseInfo databaseInfo) {
        StringBuilder urlBuf = new StringBuilder("jdbc:");
        if (databaseInfo.getDatabaseType().equals(DataSourceConstant.JDBC_TYPE_MYSQL)) {
            urlBuf.append("mysql://");
        }
        urlBuf.append(databaseInfo.getDatabaseAddress()).append(":").append(databaseInfo.getDatabasePort()).append("/");
        urlBuf.append(databaseInfo.getDatabaseName()).append("?useSSL=false&useUnicode=true&characterEncoding=utf-8");
        return urlBuf.toString();
    }

    /**
     * 由id作为唯一key
     *
     * @param id
     * @return
     * @author 徐擂
     * @version 1.0.0
     * @date 2018-12-20
     */
    private String getKey(Integer id) {
        return RedisKeyConstant.getKey(RedisKeyConstant.JDBC_TEMPLATE + id);
    }
}
