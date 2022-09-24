package com.znq.freedom.model;

import lombok.Data;

/**
 * @Author znq
 * @create 2022/9/20 18:39
 * @description 每一列数据
 */
@Data
public class ColumnClass {

    private String propertyName; // 对应java属性的名字（去除不需要的前缀或后缀后的名字）
    private String columnName; // 数据库中的名字（原本数据库中的名字）
    private String type; // 字段类型
    private String remark; // 备注
    private Boolean isPrimary; // 字段是不是一个主键

}
