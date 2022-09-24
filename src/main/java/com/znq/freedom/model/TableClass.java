package com.znq.freedom.model;

import lombok.Data;

import java.util.List;

/**
 * @Author znq
 * @create 2022/9/20 18:43
 */
@Data
public class TableClass {

    private String tableName; // 表名
    // private String tablePrefix; // 表要去除的前缀
    // private String tableSuffix; // 表要去除的后缀
    private String columnsPrefix; // 字段要去除的前缀
    private String columnsSuffix; // 字段要去除的后缀
    private String pojoName;
    private String serviceName;
    private String mapperName;
    private String controllerName;
    private String packageName;
    private List<ColumnClass> columns; // 字段

}
