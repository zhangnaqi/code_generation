package com.znq.freedom.service;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.google.common.base.CaseFormat;
import com.znq.freedom.model.DataBase;
import com.znq.freedom.model.GlobalConfig;
import com.znq.freedom.model.StaticInfo;
import com.znq.freedom.model.TableClass;
import com.znq.freedom.utils.DBUtils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ConfigService {

    public ArrayList<TableClass> config(GlobalConfig globalConfig, DataBase dataBase) {
        // 记录配置
        StaticInfo.GLOBAL_CONFIG = globalConfig;
        Connection connection = null;
        try {
            log.info("数据库连接基本配置 ====》 {}", dataBase);
            connection = DBUtils.initDB(dataBase);
            // 获取数据库信息
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet tables = metaData.getTables(connection.getCatalog(), null, null, null);
            ArrayList<TableClass> tableClassList = new ArrayList<>();
            while (tables.next()) {
                TableClass tableClass = new TableClass();
                // 表名 数据库
                String table_name = tables.getString("TABLE_NAME");
                // 表名 大小写格式处理
                String name = CaseFormat.LOWER_UNDERSCORE
                        .to(CaseFormat.UPPER_CAMEL,
                                table_name
                                        .replace(globalConfig.getTablePrefix(), "")
                                        .replace(globalConfig.getTableSuffix(), ""));
                // 文件名赋值
                tableClass.setTableName(table_name);
                tableClass.setEntityName(name);
                tableClass.setControllerName(name + "Controller");
                tableClass.setMapperName(name + "Mapper");
                tableClass.setServiceName(name + "Service");
                tableClassList.add(tableClass);
            }
            // DBUtils.close();
            return tableClassList;
        } catch (Exception e) {
            log.info("从数据库中获取更详细的信息失败");
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

}
