package com.igniubi.mapper.business.impl;

import com.igniubi.common.page.PagerHelper;
import com.igniubi.common.page.PagerInfo;
import com.igniubi.mapper.business.DatabaseService;
import com.igniubi.mapper.dto.DatabaseInfoQueryReq;
import com.igniubi.mapper.dto.DatabaseInfoSaveReq;
import com.igniubi.mapper.model.DatabaseInfo;
import com.igniubi.mapper.service.DatabaseInfoService;
import com.igniubi.model.dtos.common.ResultDTO;
import com.igniubi.model.enums.common.ResultEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 业务逻辑层
 */
@Service
public class DatabaseServiceImpl implements DatabaseService {

    private static final Logger log = LoggerFactory.getLogger(DatabaseServiceImpl.class);

    @Autowired
    private DatabaseInfoService databaseInfoService;

    /**
     * 分页查询数据库信息列表
     *
     * @param req
     * @return PagerInfo<DatabaseInfo>
     * @author 徐擂
     * @version 1.0.0
     * @date 2018-12-18
     */
    @Override
    public ResultDTO<PagerInfo<DatabaseInfo>> pageDatabaseInfo(DatabaseInfoQueryReq req){
        PagerInfo<DatabaseInfo> pagerInfo = new PagerInfo<>();
        pagerInfo.setPageNum(req.getPageNum());
        pagerInfo.setPageSize(req.getPageSize());
        PagerHelper.startPage(pagerInfo);
        try {
            databaseInfoService.listDatabaseInfo(req);
        } catch (Exception e) {
            log.error("查询异常", e);
            return new ResultDTO(ResultEnum.SYSTEM_EXCEPTION.getCode(), ResultEnum.SYSTEM_EXCEPTION.getMsg(), pagerInfo);
        }
        return new ResultDTO(ResultEnum.OK.getCode(), ResultEnum.OK.getMsg(), pagerInfo);
    }

    /**
     * 新增数据库信息
     *
     * @param req
     * @return ResultDTO
     * @author 徐擂
     * @version 1.0.0
     * @date 2018-12-18
     */
    @Override
    public ResultDTO saveDatabase(DatabaseInfoSaveReq req){
        DatabaseInfo databaseInfo = new DatabaseInfo(req.getSelfDefineName(), req.getDatabaseName(),
                req.getDatabaseAddress(), req.getDatabasePort(), req.getDatabaseType(), req.getPackageUrl());
        try {
            return new ResultDTO(ResultEnum.OK.getCode(), ResultEnum.OK.getMsg(), databaseInfoService.save(databaseInfo));
        } catch (Exception e) {
            log.error("新增数据库信息异常", e);
        }
        return new ResultDTO(ResultEnum.SYSTEM_EXCEPTION.getCode(), ResultEnum.SYSTEM_EXCEPTION.getMsg(), null);
    }
}
