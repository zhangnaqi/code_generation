package com.znq.freedom.model.sqlTemplatesData;

import lombok.Data;

@Data
public class SqlClass {

    // select update insert delete
    private String sqlType;
    // all one 是返回对象Object还是列表List
    private String resultType;
    // 是否需要对字段进行if判断
    private boolean columnIf = false;
    // 标签中属性的配置
    private SqlLabelAttribute labelAttribute;
    // 有关表的信息
    private SqlColumn column;

}
