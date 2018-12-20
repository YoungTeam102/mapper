package com.igniubi.mapper.mapper;

import com.igniubi.mapper.dto.DatabaseInfoQueryReq;
import com.igniubi.mapper.model.DatabaseInfo;
import com.igniubi.mybatis.mapper.BaseMapper;

import java.util.List;

/**
 * 类说明
 * <p>
 *
 * @author 徐擂
 * @version 1.0.0
 * @date 2018-12-18
 */
public interface DatabaseInfoMapper extends BaseMapper<Integer, DatabaseInfo> {

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
