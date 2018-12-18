package com.igniubi.mapper.controller;

import com.igniubi.mapper.business.DatabaseService;
import com.igniubi.mapper.dto.req.DatabaseInfoQueryReq;
import com.igniubi.mapper.dto.req.DatabaseInfoSaveReq;
import com.igniubi.model.dtos.common.ResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/database")
public class DataBaseController {

    @Autowired
    private DatabaseService databaseService;

    /**
     * 分页搜索数据库信息
     * @param req
     * @return
     */
    @GetMapping("/pageDatabase")
    @ResponseBody
    public ResultDTO pageDatabase(DatabaseInfoQueryReq req){
        return databaseService.pageDatabaseInfo(req);
    }

    /**
     * 新增数据库信息
     */
    @PostMapping("/saveDatabase")
    @ResponseBody
    public ResultDTO saveDatabase(@RequestBody DatabaseInfoSaveReq req){
        return databaseService.saveDatabase(req);
    }

}
