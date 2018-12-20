package com.igniubi.mapper.business;

import com.igniubi.mapper.model.DatabaseInfo;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 类说明
 * <p>
 *
 * @author 徐擂
 * @version 1.0.0
 * @date 2018-12-20
 */
public interface JdbcTemplateService {

    /**
     * 获取jdbc数据连接
     * @param databaseInfo
     * @return JdbcTemplate
     * @author 徐擂
     * @version 1.0.0
     * @date 2018-12-20
     */
    JdbcTemplate getJdbcTemplate(DatabaseInfo databaseInfo);
}
