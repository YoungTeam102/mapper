package com.igniubi.mapper.controller;

import com.igniubi.mapper.service.DatabaseInfoService;
import com.igniubi.model.CommonRsp;
import com.igniubi.model.dtos.common.ResultDTO;
import org.hibernate.validator.constraints.pl.REGON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 类说明
 * <p>
 *
 * @author 徐擂
 * @version 1.0.0
 * @date 2018-12-18
 */
@Controller
@RequestMapping("/test")
public class TestController {

    @Autowired
    private DatabaseInfoService databaseInfoService;

    @RequestMapping("/getDatabase")
    @ResponseBody
    public ResultDTO register(){
        return new ResultDTO();
    }
}
