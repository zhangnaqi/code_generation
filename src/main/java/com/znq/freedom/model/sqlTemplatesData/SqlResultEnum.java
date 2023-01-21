package com.znq.freedom.model.sqlTemplatesData;

public enum SqlResultEnum {

    ALL("all"),
    One("one");

    private String name;

    private SqlResultEnum() {
    }

    private SqlResultEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
