package com.igniubi.mapper.enums;

/**
 * 数据库字段类型 和 java类型对应 枚举
 * <p>
 *
 * @author 徐擂
 * @version 1.0.0
 * @date 2018-12-20
 */
public enum ColumnTypeEnum {

    TINYINT("tinyint", "Integer"),
    SMALLINT("smallint", "Integer"),
    MEDIUMINT("mediumint", "Integer"),
    INT("int", "Integer"),
    INTEGER("integer", "Integer"),
    BIGINT("bigint", "Long"),
    FLOAT("float", "Float"),
    DOUBLE("double", "Double"),
    DECIMAL("decimal", "BigDecimal"),
    BIT("bit", "Boolean"),
    CHAR("char", "String"),
    VARCHAR("varchar", "String"),
    TINYTEXT("tinytext", "String"),
    TEXT("text", "String"),
    MEDIUMTEXT("mediumtext", "String"),
    LONGTEXT("longtext", "String"),
    DATE("date", "Date"),
    DATETIME("datetime", "Date"),
    TIMESTAMP("timestamp", "Date");

    private String columnType;
    private String javaType;

    private ColumnTypeEnum(String columnType, String javaType) {
        this.columnType = columnType;
        this.javaType = javaType;
    }

    public static String getJavaTypeByColumnType(String columnType){
        ColumnTypeEnum[] enums = values();
        for (ColumnTypeEnum type : enums) {
            if (type.getColumnType().equals(columnType)) {
                return type.getJavaType();
            }
        }
        return null;
    }

    public String getJavaType() { return javaType; }

    public String getColumnType() { return columnType; }
}
