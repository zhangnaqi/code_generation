package com.znq.freedom.model.sqlTemplatesData;

public enum SqlTypeEnum {

    SELECT("select"),
    UPDATE("update"),
    DELETE("delete"),
    INSERT("insert");

    private String name;

    private SqlTypeEnum() {
    }

    private SqlTypeEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
