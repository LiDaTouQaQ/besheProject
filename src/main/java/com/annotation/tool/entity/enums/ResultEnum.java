package com.annotation.tool.entity.enums;

import lombok.Data;

/**
 * @ClassName ResultEnum
 * @Author Liyh
 * @Date 2024.04.05 10:39
 * @Description:
 **/
public enum ResultEnum {

    SUCCESS("10000","success"),
    ERROR("10001","error"),
    UNKNOWN("10002","unknown"),
    USER_NOT_EXIST("10003","用户不存在"),
    USERNAME_OR_PASSWORD_NOT_EXIST("10004","用户名或密码缺失"),
    USER_INFO_MISSING("10005","用户信息缺失"),
    USER_EMAIL_REPEAT("10006","存在重复邮箱");

    ResultEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    private String code;
    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
