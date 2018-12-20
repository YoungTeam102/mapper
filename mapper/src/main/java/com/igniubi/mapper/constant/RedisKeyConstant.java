package com.igniubi.mapper.constant;

import com.igniubi.mapper.enums.DatabaseEnum;

/**
 * Redis-KEY 常量
 *
 * <p>
 *
 * @author 徐擂
 * @version 1.0.0
 * @date 2018-12-20
 */
public class RedisKeyConstant {

    /**
     * JdbcTemplate缓存对象前缀
     */
    public static final String JDBC_TEMPLATE = "jdbc_template_";

    /**
     * 组装key： mapper应用公共前缀 + key
     * @param key
     * @return
     */
    public static String getKey(String key){
        return DatabaseEnum.REDIS_KEY_FIX.getCacheKey() + key;
    }
}
