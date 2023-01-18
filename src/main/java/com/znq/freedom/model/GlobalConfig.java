package com.znq.freedom.model;

import lombok.Data;

@Data
public class GlobalConfig {

    // 表需要去除的前缀
    private String tablePrefix = "";

    // 表需要去除的后缀
    private String tableSuffix = "";

    // 使用的模板类型
    private String templatesType;

    // 生成项目的包路径
    private String packageName;

}
