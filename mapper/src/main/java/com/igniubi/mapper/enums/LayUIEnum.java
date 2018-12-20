package com.igniubi.mapper.enums;

/**
 * Layui适用的枚举
 * <p>
 *
 * @author 徐擂
 * @version 1.0.0
 * @date 2018-12-20
 */
public enum LayUIEnum {

    OK(0, "执行成功!"),
    FAIL(1, "执行失败!");

    private int code;
    private String msg;

    private LayUIEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() { return code; }

    public String getMsg() { return msg; }
}
