package com.igniubi.mapper.service;

import com.igniubi.common.page.PagerInfo;
import com.igniubi.mapper.dto.req.DatabaseInfoQueryReq;
import com.igniubi.mapper.model.DatabaseInfo;
import com.igniubi.model.dtos.common.ResultDTO;
import com.igniubi.mybatis.service.BaseService;

import java.util.List;

/**
 * 数据库操作业务
 * <p>
 *
 * @author 徐擂
 * @version 1.0.0
 * @date 2018-12-18
 */
public interface DatabaseInfoService extends BaseService<Integer, DatabaseInfo> {

    /**
     * 查询database列表
     * @param req
     * @return
     * @author 徐擂
     * @version 1.0.0
     * @date 2018-12-18
     */
    List<DatabaseInfo> listDatabaseInfo(DatabaseInfoQueryReq req);

}
