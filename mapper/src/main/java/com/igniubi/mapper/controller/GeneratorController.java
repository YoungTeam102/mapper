package com.igniubi.mapper.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 数据库 表
 * <p>
 *
 * @author 徐擂
 * @version 1.0.0
 * @date 2018-12-18
 */
@Controller
@RequestMapping("/gen")
public class GeneratorController {

    @RequestMapping({"/tableList"})
    public String tableList(){
        return "gen/tableList";
    }

    @RequestMapping({"/dataBaseList"})
    public String dataBaseList(){
        return "dbs/dbsList";
    }
}
