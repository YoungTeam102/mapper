package com.igniubi.mapper.bean;


import com.igniubi.mybatis.entity.BaseEntity;

/**
 * 表的字段信息
 * <p>
 *
 * @author  徐擂
 * @date    2018-12-21
 * @version  1.0.0
 */
public class ColumnBean extends BaseEntity {
    /**
     * 列名
     */
    private String columnName;
    /**
     * 数据类型
     */
    private String dataType;
    /**
     * 列注释
     */
    private String columnComment;
    /**
     * 如果列是主键则有信息
     */
    private String columnKey;
    /**
     * 额外属性
     */
    private String extra;
    /**
     * 属性名称(第一个字母大写)，如：user_name => UserName
     */
    private String attrNameFirstUp;
    /**
     * 属性名称(第一个字母小写)，如：user_name => userName
     */
    private String attrNameFirstDown;
    /**
     * 属性类型
     */
    private String attrType;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getColumnComment() {
        return columnComment;
    }

    public void setColumnComment(String columnComment) {
        this.columnComment = columnComment;
    }

    public String getColumnKey() {
        return columnKey;
    }

    public void setColumnKey(String columnKey) {
        this.columnKey = columnKey;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getAttrNameFirstUp() { return attrNameFirstUp; }

    public void setAttrNameFirstUp(String attrNameFirstUp) { this.attrNameFirstUp = attrNameFirstUp; }

    public String getAttrNameFirstDown() { return attrNameFirstDown; }

    public void setAttrNameFirstDown(String attrNameFirstDown) { this.attrNameFirstDown = attrNameFirstDown; }

    public String getAttrType() {
        return attrType;
    }

    public void setAttrType(String attrType) {
        this.attrType = attrType;
    }
}
