package com.igniubi.mapper.enums;

import java.util.concurrent.TimeUnit;

/**
 * Redis-key 前缀
 * <p>
 *
 * @author 徐擂
 * @version 1.0.0
 * @date 2018-12-20
 */
public enum DatabaseEnum {

    REDIS_KEY_FIX("igniubi:mapper:", 3L, TimeUnit.HOURS);

    private String cacheKey;
    private long cacheTime;
    private TimeUnit timeUnit;

    private DatabaseEnum(String cacheKey, long cacheTime, TimeUnit timeUnit) {
        this.cacheKey = cacheKey;
        this.cacheTime = cacheTime;
        this.timeUnit = timeUnit;
    }

    public String getCacheKey() { return cacheKey; }

    public long getCacheTime() { return cacheTime; }

    public TimeUnit getTimeUnit() { return timeUnit; }
}
