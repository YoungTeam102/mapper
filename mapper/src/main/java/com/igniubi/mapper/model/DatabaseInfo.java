package com.igniubi.mapper.model;

import com.igniubi.mybatis.entity.BaseEntity;

/**
 * 数据库信息表
 * <p>
 *
 * @author 徐擂
 * @version 1.0.0
 * @date 2018-12-18
 */
public class DatabaseInfo extends BaseEntity{

    /**
     * 主键
     */
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
     * 用户名
     */
    private String userName;
    /**
     * 密码
     */
    private String password;
    /**
     * 数据库类型 mysql:1 oracle:2
     */
    private Integer databaseType;
    /**
     * 生成包路径
     */
    private String packageUrl;
    /**
     * 创建时间
     */
    private String createdAt;
    /**
     * 最后更新时间
     */
    private String updatedAt;
    /**
     * 是否删除 1=已删除 0=未删除
     */
    private String deleted;

    public DatabaseInfo(){}

    public DatabaseInfo(String selfDefineName, String databaseName, String databaseAddress,
                        String userName, String password, String databasePort, Integer databaseType, String packageUrl) {
        this.selfDefineName = selfDefineName;
        this.databaseName = databaseName;
        this.databaseAddress = databaseAddress;
        this.databasePort = databasePort;
        this.databaseType = databaseType;
        this.packageUrl = packageUrl;
        this.userName = userName;
        this.password = password;
    }

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public String getSelfDefineName() { return selfDefineName; }

    public void setSelfDefineName(String selfDefineName) { this.selfDefineName = selfDefineName; }

    public String getDatabaseName() { return databaseName; }

    public void setDatabaseName(String databaseName) { this.databaseName = databaseName; }

    public String getDatabaseAddress() { return databaseAddress; }

    public void setDatabaseAddress(String databaseAddress) { this.databaseAddress = databaseAddress; }

    public String getDatabasePort() { return databasePort; }

    public void setDatabasePort(String databasePort) { this.databasePort = databasePort; }

    public Integer getDatabaseType() { return databaseType; }

    public void setDatabaseType(Integer databaseType) { this.databaseType = databaseType; }

    public String getPackageUrl() { return packageUrl; }

    public void setPackageUrl(String packageUrl) { this.packageUrl = packageUrl; }

    public String getCreatedAt() { return createdAt; }

    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    public String getUpdatedAt() { return updatedAt; }

    public String getUserName() { return userName; }

    public void setUserName(String userName) { this.userName = userName; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }

    public String getDeleted() { return deleted; }

    public void setDeleted(String deleted) { this.deleted = deleted; }
}
