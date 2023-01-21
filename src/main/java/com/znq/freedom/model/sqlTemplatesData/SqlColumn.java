package com.znq.freedom.model.sqlTemplatesData;

import java.util.List;

import com.znq.freedom.model.ColumnClass;

import lombok.Data;

@Data
public class SqlColumn {
    // 表名
    private String tableName;
    // 字段
    private List<ColumnClass> columnClassList;
    // 连接关键词
    private String[] connectionKeyList;
}
