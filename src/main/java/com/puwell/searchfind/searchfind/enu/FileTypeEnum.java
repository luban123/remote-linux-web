package com.puwell.searchfind.searchfind.enu;

public enum FileTypeEnum {
    DERICTORY("目录",0),NODERICTORY("文件",1);
    private String name;
    private Integer code;

    FileTypeEnum(String name, Integer code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
