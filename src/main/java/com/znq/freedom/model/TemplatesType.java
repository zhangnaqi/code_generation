package com.znq.freedom.model;

public enum TemplatesType {

    MYBATIS("mybatis"),
    MYBATISPLUS("mybatis-plus");

    private String type;

    private TemplatesType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
