package com.igniubi.mapper.dto;

import com.igniubi.model.dtos.common.BaseDTO;


public class DatabaseInfoSaveReq extends BaseDTO {

    private Integer id;

    /**
     * 自定义名称
     */
    private String selfDefineName;
    /**
     * 数据库名称
     */
    private String databaseName;
    /**
     * 数据库连接地址
     */
    private String databaseAddress;
    /**
     * 数据库连接端口
     */
    private String databasePort;
    /**
     * 数据库类型
     */
    private Integer databaseType;
    /**
     * 生成包路径
     */
    private String packageUrl;

    private String userName;

    private String password;

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public String getUserName() { return userName; }

    public void setUserName(String userName) { this.userName = userName; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public String getSelfDefineName() {
        return selfDefineName;
    }

    public void setSelfDefineName(String selfDefineName) {
        this.selfDefineName = selfDefineName;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getDatabaseAddress() {
        return databaseAddress;
    }

    public void setDatabaseAddress(String databaseAddress) {
        this.databaseAddress = databaseAddress;
    }

    public String getDatabasePort() {
        return databasePort;
    }

    public void setDatabasePort(String databasePort) {
        this.databasePort = databasePort;
    }

    public Integer getDatabaseType() {
        return databaseType;
    }

    public void setDatabaseType(Integer databaseType) {
        this.databaseType = databaseType;
    }

    public String getPackageUrl() {
        return packageUrl;
    }

    public void setPackageUrl(String packageUrl) {
        this.packageUrl = packageUrl;
    }
}
