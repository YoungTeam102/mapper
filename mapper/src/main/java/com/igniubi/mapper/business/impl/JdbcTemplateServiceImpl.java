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
import java.util.concurrent.TimeUnit;

/**
 * 类说明
 * <p>
 *
 * @author 徐擂
 * @version 1.0.0
 * @date 2018-12-20
 */
@Service
public class JdbcTemplateServiceImpl implements JdbcTemplateService {

    @Autowired
    private RedisValueOperations redisTemplate;

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
    public JdbcTemplate getJdbcTemplate(DatabaseInfo databaseInfo){
        // 组装redis-key
        RedisKeyBuilder redisKeyBuilder = RedisKeyBuilder.newInstance().appendFixed(DatabaseEnum.REDIS_KEY_FIX.getCacheKey()).
                appendVar(RedisKeyConstant.JDBC_TEMPLATE + getKey(databaseInfo));
        // 缓存中有则优先从redis拿
        JdbcTemplate jdbcTemplate = redisTemplate.get(redisKeyBuilder, JdbcTemplate.class);
        if (jdbcTemplate == null) {
            // 否则获取连接
            jdbcTemplate = new JdbcTemplate(createDataSource(databaseInfo));
            redisTemplate.set(redisKeyBuilder, jdbcTemplate, DatabaseEnum.REDIS_KEY_FIX.getCacheTime(), DatabaseEnum.REDIS_KEY_FIX.getTimeUnit());
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
    private DataSource createDataSource(DatabaseInfo databaseInfo){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(DateSourceConstant.JDBC_DRIVER_CLASS);
        dataSource.setUrl(getJdbcUrl(databaseInfo));
        dataSource.setUsername(databaseInfo.getUserName());
        dataSource.setPassword(databaseInfo.getPassword());
        return dataSource;
    }

    /**
     * 拼装jdbc url  oracle暂不支持
     * @param databaseInfo
     * @return
     * @author 徐擂
     * @version 1.0.0
     * @date 2018-12-20
     */
    private String getJdbcUrl(DatabaseInfo databaseInfo){
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
    private String getKey(DatabaseInfo databaseInfo){
        return databaseInfo.getDatabaseAddress() + RedisKeyBuilder.SEPARATOR_MH + databaseInfo.getDatabasePort() + RedisKeyBuilder.SEPARATOR_MH + databaseInfo.getDatabaseName();
    }
}
