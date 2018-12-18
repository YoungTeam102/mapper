package com.igniubi.mapper.service.impl;

import com.igniubi.mapper.mapper.DatabaseInfoMapper;
import com.igniubi.mapper.model.DatabaseInfo;
import com.igniubi.mapper.service.DatabaseInfoService;
import com.igniubi.mybatis.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 类说明
 * <p>
 *
 * @author 徐擂
 * @version 1.0.0
 * @date 2018-12-18
 */
@Service
public class DatabaseInfoServiceImpl extends BaseServiceImpl<Integer, DatabaseInfo> implements DatabaseInfoService, InitializingBean {

    @Autowired
    private DatabaseInfoMapper databaseInfoMapper;

    @Override
    public void afterPropertiesSet() throws Exception {
        this.setBaseMapper(databaseInfoMapper);
    }

}
