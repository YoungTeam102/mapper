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

@Controller
@RequestMapping("/database")
public class DataBaseController {

    @Autowired
    private DatabaseService databaseService;

    /**
     * 分页搜索数据库信息
     *
     * @param req
     * @return
     */
    @GetMapping("/pageDatabase")
    @ResponseBody
    public Map<String, Object> pageDatabase(DatabaseInfoQueryReq req) {
        Map<String, Object> dataMap = new HashMap<>();
        ResultDTO<PagerInfo<DatabaseInfo>> result = databaseService.pageDatabaseInfo(req);
        if (result != null && result.getCode() == ResultEnum.OK.getCode()) {
            dataMap.put("code", LayUIEnum.OK.getCode());
            dataMap.put("data", result.getData().getList());
            dataMap.put("count", result.getData().getTotal());
        }
        return dataMap;
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
