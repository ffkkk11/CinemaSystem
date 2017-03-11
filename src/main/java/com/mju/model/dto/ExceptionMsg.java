package com.mju.model.dto;

/**
 * Created by Hua on 2017/3/2.
 */
public enum ExceptionMsg {
    SUCCESS(1,"操作成功"),
    FAILED(-1,"操作失败"),
    EXCEPTION(-999, "操作异常");


    private Integer code;
    private String msg;
    private ExceptionMsg(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public ExceptionMsg setCode(Integer code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public ExceptionMsg setMsg(String msg) {
        this.msg = msg;
        return this;
    }
}
