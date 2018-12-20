package com.igniubi.mapper.controller;


import com.igniubi.mapper.business.DatabaseService;
import com.igniubi.mapper.model.DatabaseInfo;
import com.igniubi.model.dtos.common.ResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 跳转视图控制器
 * <p>
 *
 * @author 徐擂
 * @version 1.0.0
 * @date 2018-12-18
 */
@Controller
@RequestMapping("/to")
public class RedirectionController {

    @Autowired
    private DatabaseService databaseService;

    /**
     * 跳转数据表页
     * @return
     */
    @RequestMapping({"/tableList"})
    public String tableList(){
        return "gen/tableList";
    }

    /**
     * 跳转数据库列表页
     * @return
     */
    @RequestMapping({"/databaseList"})
    public String databaseList(){
        return "dbs/dbsList";
    }

    /**
     * 跳转新增数据库页
     */
    @RequestMapping({"/addDatabase"})
    public String addDatabase(Model model){
        model.addAttribute("databaseInfo", new DatabaseInfo());
        model.addAttribute("opType", "saveDatabase");
        return "dbs/dbsAdd";
    }

    /**
     * 跳转编辑数据库页
     */
    @RequestMapping({"/editDatabase/{id}"})
    public String editDatabase(Model model,@PathVariable Integer id){
        // 通过id查询
        ResultDTO<DatabaseInfo> resultDTO = databaseService.getDatabaseById(id);

        model.addAttribute("opType", "editDatabase");
        model.addAttribute("databaseInfo", resultDTO.getData());
        return "dbs/dbsAdd";
    }
}
