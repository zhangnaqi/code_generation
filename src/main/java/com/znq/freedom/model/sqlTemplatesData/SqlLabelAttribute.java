package com.znq.freedom.model.sqlTemplatesData;

import lombok.Data;

@Data
public class SqlLabelAttribute {
    // 标签中的id属性
    private String id;
    // 标签中的resultMap属性
    private String resultMap = "BaseResultMap";
    // include 中的 refid 属性
    private String include = "Base_column_List";
}
