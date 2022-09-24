package com.znq.freedom.controller;

import com.google.common.base.CaseFormat;
import com.google.errorprone.annotations.Var;
import com.znq.freedom.model.DataBase;
import com.znq.freedom.model.R;
import com.znq.freedom.model.TableClass;
import com.znq.freedom.utils.DBUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

/**
 * @Author znq
 * @create 2022/9/20 16:02
 */
@RestController
@Slf4j
public class DBController {

    @PostMapping("/connect")
    public R connect(@RequestBody DataBase dataBase) {
        Connection con = DBUtils.initDB(dataBase);
        if (con != null) {
            return R.ok("数据库连接成功");
        }
        return R.error("数据库连接失败");
    }

    @PostMapping("/config")
    public R config(@RequestBody Map<String, String> map) {
        String packageName = map.get("packageName");
        try {
            Connection connection = DBUtils.getConnection();
            // 获取数据库信息
            DatabaseMetaData metaData = connection.getMetaData();
            log.info("metaData ===> {}", metaData);
            ResultSet tables = metaData.getTables(connection.getCatalog(), null, null, null);
            log.info("tables ===> {}", tables);
            ArrayList<TableClass> tableClasses = new ArrayList<>();
            while (tables.next()) {
                TableClass tableClass = new TableClass();
                tableClass.setPackageName(packageName);
                String table_name = tables.getString("TABLE_NAME");
                String pojoName = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, table_name);
                tableClass.setTableName(table_name);
                tableClass.setPojoName(pojoName);
                tableClass.setControllerName(pojoName + "Controller");
                tableClass.setMapperName(pojoName + "Mapper");
                tableClass.setServiceName(pojoName + "Service");
                tableClasses.add(tableClass);
            }
            return R.ok("数据库信息读取成功", tableClasses);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return R.error("数据库信息读取失败");
    }
}
