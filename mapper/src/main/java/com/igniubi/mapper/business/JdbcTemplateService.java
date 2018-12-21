package com.igniubi.mapper.business;

import com.igniubi.mapper.bean.TableBean;
import com.igniubi.mapper.model.DatabaseInfo;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

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
     * 获取数据库所有表结构
     * <p>
     *
     * @param id
     * @return
     * @throws
     * @author  徐擂
     * @date    2018-12-21
     * @version  1.0.0
     */
    List<TableBean> getTableByDatabaseId(Integer id);

}