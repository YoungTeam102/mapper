package com.igniubi.mapper.controller;

import com.igniubi.common.page.PagerInfo;
import com.igniubi.mapper.bean.TableBean;
import com.igniubi.mapper.business.DatabaseService;
import com.igniubi.mapper.business.JdbcTemplateService;
import com.igniubi.mapper.dto.DatabaseInfoQueryReq;
import com.igniubi.mapper.dto.DatabaseInfoSaveReq;
import com.igniubi.mapper.enums.LayUIEnum;
import com.igniubi.mapper.model.DatabaseInfo;
import com.igniubi.model.dtos.common.ResultDTO;
import com.igniubi.model.enums.common.ResultEnum;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据库相关 控制器
 * <p>
 *
 * @author  徐擂
 * @date    2018/12/20
 * @version  1.0.0
 */

@Controller
@RequestMapping("/database")
public class DatabaseController {

    @Autowired
    private DatabaseService databaseService;
    @Autowired
    private JdbcTemplateService jdbcTemplateService;

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
     * 查询所有数据库信息
     *
     * @return
     */
    @GetMapping("/listAll")
    @ResponseBody
    public Map<String, Object> listAll() {
        Map<String, Object> dataMap = new HashMap<>();
        ResultDTO<List<DatabaseInfo>> result = databaseService.listAllDatabaseInfo();
        if (null != result && result.getCode() == ResultEnum.OK.getCode()) {
            dataMap.put("data", result.getData());
        }
        dataMap.put("code", result.getCode());
        return dataMap;
    }

    /**
     * 查询数据库所有表信息
     * <p>
     *
     * @return RestResult
     * @author: 徐擂
     * @date:
     * @version: 1.0.0
     */
    @RequestMapping("/listTable/{id}")
    @ResponseBody
    public Map<String, Object> listTable(@PathVariable Integer id) {
        Map<String, Object> dataMap = new HashMap<>();
        List<TableBean> tableBeanList = jdbcTemplateService.getTableByDatabaseId(id);
        if (!CollectionUtils.isEmpty(tableBeanList)) {
            dataMap.put("code", "0");
            dataMap.put("data", tableBeanList);
        }
        return dataMap;
    }

    /**
     * 生成代码
     * <p>
     *
     * @return RestResult
     * @author: 徐擂
     * @date:
     * @version: 1.0.0
     */
    @RequestMapping("/genCode")
    @ResponseBody
    public void genCode(Integer databaseId, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String[] tableNames = request.getParameterValues("tableName");
        List<TableBean> tableBeanList = jdbcTemplateService.getTableByDatabaseId(databaseId);
        // 生成代码
        byte[] data = jdbcTemplateService.genCode(databaseId,tableNames);

        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"code.zip\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");

        IOUtils.write(data, response.getOutputStream());
    }

    /**
     * 新增数据库信息
     */
    @PostMapping("/saveDatabase")
    @ResponseBody
    public ResultDTO saveDatabase(@RequestBody DatabaseInfoSaveReq req){
        return databaseService.saveDatabase(req);
    }

    /**
     * 编辑数据库信息
     */
    @PostMapping("/editDatabase")
    @ResponseBody
    public ResultDTO editDatabase(@RequestBody DatabaseInfoSaveReq req){
        return databaseService.editDatabase(req);
    }

    /**
     * 删除数据库信息
     */
    @GetMapping("/delDatabase/{id}")
    @ResponseBody
    public ResultDTO delDatabase(@PathVariable Integer id){
        return databaseService.delDatabase(id);
    }


}
