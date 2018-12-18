package com.igniubi.mapper.dto.req;

import com.igniubi.model.dtos.common.PagerReqDTO;

public class DatabaseInfoQueryReq extends PagerReqDTO {

    private String databaseName;

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }
}
