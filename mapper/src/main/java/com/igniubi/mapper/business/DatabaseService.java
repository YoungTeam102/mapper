package com.igniubi.mapper.business;

import com.igniubi.common.page.PagerInfo;
import com.igniubi.mapper.dto.DatabaseInfoQueryReq;
import com.igniubi.mapper.dto.DatabaseInfoSaveReq;
import com.igniubi.mapper.model.DatabaseInfo;
import com.igniubi.model.dtos.common.ResultDTO;

/**
 * 业务逻辑层
 */
public interface DatabaseService {


    /**
     * 根据id查询数据库
     *
     * @param id
     * @return ResultDTO<DatabaseInfo>
     * @author 徐擂
     * @version 1.0.0
     * @date 2018-12-18
     */
    ResultDTO<DatabaseInfo> getDatabaseById(Integer id);

    /**
     * 分页查询数据库信息列表
     *
     * @param req
     * @return ResultDTO<PagerInfo<DatabaseInfo>>
     * @author 徐擂
     * @version 1.0.0
     * @date 2018-12-18
     */
    ResultDTO<PagerInfo<DatabaseInfo>> pageDatabaseInfo(DatabaseInfoQueryReq req);

    /**
     * 新增数据库信息
     *
     * @param req
     * @return ResultDTO
     * @author 徐擂
     * @version 1.0.0
     * @date 2018-12-18
     */
    ResultDTO saveDatabase(DatabaseInfoSaveReq req);

    /**
     * 编辑数据库信息
     *
     * @param req
     * @return ResultDTO
     * @author 徐擂
     * @version 1.0.0
     * @date 2018-12-18
     */
    ResultDTO editDatabase(DatabaseInfoSaveReq req);

    /**
     * 删除数据库信息
     *
     * @param id
     * @return ResultDTO
     * @author 徐擂
     * @version 1.0.0
     * @date 2018-12-18
     */
    ResultDTO delDatabase(Integer id);
}
