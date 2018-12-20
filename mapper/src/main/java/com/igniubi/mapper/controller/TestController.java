package com.igniubi.mapper.controller;

import com.igniubi.common.page.PagerInfo;
import com.igniubi.mapper.business.DatabaseService;
import com.igniubi.mapper.business.JdbcTemplateService;
import com.igniubi.mapper.dto.DatabaseInfoQueryReq;
import com.igniubi.mapper.model.DatabaseInfo;
import com.igniubi.model.dtos.common.ResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 类说明
 * <p>
 *
 * @author 徐擂
 * @version 1.0.0
 * @date 2018-12-20
 */
@Controller
public class TestController {

    @Autowired
    private JdbcTemplateService jdbcTemplateService;
    @Autowired
    private DatabaseService databaseService;

    @RequestMapping("/test")
    @ResponseBody
    public List register(String id){
        ResultDTO<PagerInfo<DatabaseInfo>> result = databaseService.pageDatabaseInfo(new DatabaseInfoQueryReq());

        JdbcTemplate jdbcTemplate = jdbcTemplateService.getJdbcTemplate(result.getData().getList().get(0));
        String sql = "select table_name from information_schema.tables where table_name='database_info'";
        List<Map<String, Object>> map = jdbcTemplate.queryForList(sql);
        return map;
    }

}
