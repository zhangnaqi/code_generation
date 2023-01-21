package com.znq.freedom.model.sqlTemplatesData;

public enum SqlConnectionKeyEnum {

    AND("and"),
    IN("in");

    private String name;

    private SqlConnectionKeyEnum() {
    }

    private SqlConnectionKeyEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
