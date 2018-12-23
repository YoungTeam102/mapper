package com.igniubi.mapper.business.impl;

import com.igniubi.common.page.PagerHelper;
import com.igniubi.common.page.PagerInfo;
import com.igniubi.common.utils.DateUtil;
import com.igniubi.mapper.business.DatabaseService;
import com.igniubi.mapper.dto.DatabaseInfoQueryReq;
import com.igniubi.mapper.dto.DatabaseInfoSaveReq;
import com.igniubi.mapper.model.DatabaseInfo;
import com.igniubi.mapper.service.DatabaseInfoService;
import com.igniubi.mapper.util.TestConnectUtil;
import com.igniubi.model.dtos.common.ResultDTO;
import com.igniubi.model.enums.common.ResultEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 业务逻辑层
 */
@Service
public class DatabaseServiceImpl implements DatabaseService {

    private static final Logger log = LoggerFactory.getLogger(DatabaseServiceImpl.class);

    @Autowired
    private DatabaseInfoService databaseInfoService;

    /**
     * 根据id查询数据库
     *
     * @param id
     * @return ResultDTO<DatabaseInfo>
     * @author 徐擂
     * @version 1.0.0
     * @date 2018-12-18
     */
    @Override
    public ResultDTO<DatabaseInfo> getDatabaseById(Integer id){
        ResultDTO resultDTO = new ResultDTO();
        DatabaseInfo databaseInfo = databaseInfoService.get(id);
        if (databaseInfo == null) {
            resultDTO.setCode(ResultEnum.FAIL.getCode());
            resultDTO.setMessage(ResultEnum.FAIL.getMsg());
        } else {
            resultDTO.setData(databaseInfo);
        }
        return resultDTO;
    }

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
     * 查询所有数据库信息列表
     *
     * @return ResultDTO<List<DatabaseInfo>>
     * @author 徐擂
     * @version 1.0.0
     * @date 2018-12-23
     */
    @Override
    public ResultDTO<List<DatabaseInfo>> listAllDatabaseInfo(){
        ResultDTO resultDTO = new ResultDTO();
        List<DatabaseInfo> databaseList = null;
        try {
            databaseList = databaseInfoService.listDatabaseInfo(new DatabaseInfoQueryReq());
            resultDTO.setData(databaseList);
        } catch (Exception e) {
            log.error("查询数据库信息异常", e);
            resultDTO.setCode(ResultEnum.FAIL.getCode());
            resultDTO.setMessage(ResultEnum.FAIL.getMsg());
        }
        return resultDTO;
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
        if (!TestConnectUtil.isAccessable(req.getDatabaseAddress(), Integer.valueOf(req.getDatabasePort()))) {
            return new ResultDTO(ResultEnum.SYSTEM_EXCEPTION.getCode(), "数据库连接失败，请确认", null);
        }
        DatabaseInfo databaseInfo = new DatabaseInfo(req.getSelfDefineName(), req.getDatabaseName(),
                req.getDatabaseAddress(),req.getUserName(), req.getPassword(), req.getDatabasePort(), req.getDatabaseType(), req.getPackageUrl());
        try {
            return new ResultDTO(ResultEnum.OK.getCode(), ResultEnum.OK.getMsg(), databaseInfoService.save(databaseInfo));
        } catch (Exception e) {
            log.error("新增数据库信息异常", e);
        }
        return new ResultDTO(ResultEnum.SYSTEM_EXCEPTION.getCode(), ResultEnum.SYSTEM_EXCEPTION.getMsg(), null);
    }

    /**
     * 编辑数据库信息
     *
     * @param req
     * @return ResultDTO
     * @author 徐擂
     * @version 1.0.0
     * @date 2018-12-18
     */
    @Override
    public ResultDTO editDatabase(DatabaseInfoSaveReq req){
        DatabaseInfo databaseInfo = databaseInfoService.get(req.getId());
        BeanUtils.copyProperties(req, databaseInfo);
        databaseInfo.setUpdatedAt(DateUtil.getcurrentDateTime());
        int count = databaseInfoService.update(databaseInfo);
        if (count > 0) {
            return new ResultDTO();
        }
        return new ResultDTO(ResultEnum.FAIL.getCode(), ResultEnum.FAIL.getMsg(), null);
    }

    /**
     * 删除数据库信息
     *
     * @param id
     * @return ResultDTO
     * @author 徐擂
     * @version 1.0.0
     * @date 2018-12-18
     */
    @Override
    public ResultDTO delDatabase(Integer id){
        int count = databaseInfoService.remove(id);
        if (count > 0) {
            return new ResultDTO();
        }
        return new ResultDTO(ResultEnum.FAIL.getCode(), ResultEnum.FAIL.getMsg(), null);
    }
}
