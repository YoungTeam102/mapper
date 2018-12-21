package com.igniubi.mapper.controller;

import com.igniubi.common.page.PagerInfo;
import com.igniubi.mapper.business.DatabaseService;
import com.igniubi.mapper.dto.DatabaseInfoQueryReq;
import com.igniubi.mapper.dto.DatabaseInfoSaveReq;
import com.igniubi.mapper.enums.LayUIEnum;
import com.igniubi.mapper.model.DatabaseInfo;
import com.igniubi.model.dtos.common.ResultDTO;
import com.igniubi.model.enums.common.ResultEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 代码生成控制器
 * <p>
 *
 * @author  徐擂
 * @date    2018/12/21
 * @version  1.0.0
 */

@Controller
@RequestMapping("/gen")
public class CodeGeneratorController {

    @Autowired
    private DatabaseService databaseService;


}
