package com.igniubi.mapper.business.impl;
import com.igniubi.mapper.constant.RedisKeyConstant;
import com.igniubi.mapper.enums.DatabaseEnum;
import com.igniubi.model.enums.common.RedisKeyEnum;
import com.igniubi.redis.operations.RedisValueOperations;
import com.igniubi.redis.util.RedisKeyBuilder;
import io.lettuce.core.cluster.RedisClusterURIUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.support.SQLExceptionTranslator;

import com.igniubi.mapper.business.JdbcTemplateService;
import com.igniubi.mapper.constant.DateSourceConstant;
import com.igniubi.mapper.model.DatabaseInfo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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

    /**
     * 维护一个map，用于缓存连接对象
     */
    private static Map<String, Object> cacheTemplate = new HashMap<>();


    /**
     * 获取jdbc数据连接
     *
     * @param databaseInfo
     * @return JdbcTemplate
     * @author 徐擂
     * @version 1.0.0
     * @date 2018-12-20
     */
    @Override

    public JdbcTemplate getJdbcTemplate(DatabaseInfo databaseInfo) {
        // 组装cache-key
        String cacheKey = getKey(databaseInfo);
        // 缓存中有则优先从缓存拿
        JdbcTemplate jdbcTemplate = (JdbcTemplate) cacheTemplate.get(cacheKey);
        if (jdbcTemplate == null) {
            // 否则获取连接
            jdbcTemplate = new JdbcTemplate(createDataSource(databaseInfo));
            cacheTemplate.put(cacheKey, jdbcTemplate);
        }
        return jdbcTemplate;
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
        dataSource.setDriverClassName(DateSourceConstant.JDBC_DRIVER_CLASS);
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
        if (databaseInfo.getDatabaseType().equals(DateSourceConstant.JDBC_TYPE_MYSQL)) {
            urlBuf.append("mysql://");
        }
        urlBuf.append(databaseInfo.getDatabaseAddress()).append(":").append(databaseInfo.getDatabasePort()).append("/");
        urlBuf.append(databaseInfo.getDatabaseName()).append("?useSSL=false&useUnicode=true&characterEncoding=utf-8");
        return urlBuf.toString();
    }

    /**
     * 由 ip + port + 数据库名称 组成唯一key
     *
     * @param databaseInfo
     * @return
     * @author 徐擂
     * @version 1.0.0
     * @date 2018-12-20
     */
    private String getKey(DatabaseInfo databaseInfo) {
        return RedisKeyConstant.getKey(RedisKeyConstant.JDBC_TEMPLATE + databaseInfo.getDatabaseAddress()
                + RedisKeyBuilder.SEPARATOR_MH + databaseInfo.getDatabasePort() + RedisKeyBuilder.SEPARATOR_MH + databaseInfo.getDatabaseName());
    }
}
