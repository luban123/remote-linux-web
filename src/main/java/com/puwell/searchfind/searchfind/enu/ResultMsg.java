package com.puwell.searchfind.searchfind.enu;

public enum  ResultMsg {
    success("成功",200),error("失败",400);
    private String msg;
    private Integer code;

    ResultMsg(String msg, Integer code) {
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}

