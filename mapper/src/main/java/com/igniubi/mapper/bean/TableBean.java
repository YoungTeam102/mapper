package com.igniubi.mapper.bean;

import java.util.List;

/**
 * 表结构
 * <p>
 *
 * @author 徐擂
 * @version 1.0.0
 * @date 2018-12-21
 */
public class TableBean {
    /**
     * 表名
     */
    private String tableName;
    /**
     * 表存储类型
     */
    private String engine;
    /**
     * 表注释
     */
    private String tableComment;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 类名，例如sys_user --> SysUser;
     */
    private String classNameFirstUp;
    /**
     * 类名，例如sys_user --> sysUser;
     */
    private String classNameFirstDown;
    /**
     * 表的列名(不包含主键)
     */
    private List<ColumnBean> columnEntityList;

    public String getTableName() { return tableName; }

    public void setTableName(String tableName) { this.tableName = tableName; }

    public String getEngine() { return engine; }

    public void setEngine(String engine) { this.engine = engine; }

    public String getTableComment() { return tableComment; }

    public void setTableComment(String tableComment) { this.tableComment = tableComment; }

    public String getCreateTime() { return createTime; }

    public void setCreateTime(String createTime) { this.createTime = createTime; }

    public String getClassNameFirstUp() { return classNameFirstUp; }

    public void setClassNameFirstUp(String classNameFirstUp) { this.classNameFirstUp = classNameFirstUp; }

    public String getClassNameFirstDown() { return classNameFirstDown; }

    public void setClassNameFirstDown(String classNameFirstDown) { this.classNameFirstDown = classNameFirstDown; }

    public List<ColumnBean> getColumnEntityList() { return columnEntityList; }

    public void setColumnEntityList(List<ColumnBean> columnEntityList) { this.columnEntityList = columnEntityList; }
}
