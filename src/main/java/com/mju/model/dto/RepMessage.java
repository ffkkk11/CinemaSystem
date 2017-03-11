package com.mju.model.dto;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by Hua on 2017/3/2.
 */
public class RepMessage implements Serializable{
    private Integer repCode;
    private String repMsg;
    private Map<String,Object> content;

    public Integer getRepCode() {
        return repCode;
    }

    public RepMessage setRepCode(Integer repCode) {
        this.repCode = repCode;
        return this;
    }

    public String getRepMsg() {
        return repMsg;
    }

    public RepMessage setRepMsg(String repMsg) {
        this.repMsg = repMsg;
        return this;
    }

    public Map<String, Object> getContent() {
        return content;
    }

    public RepMessage setContent(Map<String, Object> content) {
        this.content = content;
        return this;
    }

    public RepMessage setStatus(ExceptionMsg exceptionMsg) {
        this.repCode = exceptionMsg.getCode();
        this.repMsg = exceptionMsg.getMsg();
        return this;
    }
}
