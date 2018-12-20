package com.igniubi.mapper.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 跳转首页
 * <p>
 *
 * @author 徐擂
 * @version 1.0.0
 * @date 2018-12-18
 */
@Controller
public class IndexController {

    @RequestMapping({"/", "/index"})
    public String register(){
        return "index";
    }
}
