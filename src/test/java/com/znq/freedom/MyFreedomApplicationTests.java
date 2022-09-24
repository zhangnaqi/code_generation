package com.znq.freedom;

import com.znq.freedom.model.TableClass;
import com.znq.freedom.utils.DBUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.sql.*;

@SpringBootTest
class MyFreedomApplicationTests {



    @Test
    void contextLoads() throws SQLException, ClassNotFoundException {
        // 获取数据库连接
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/mybatis?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai",
                "root",
                "@ZNQRoot123456"
        );
        // 获取数据库详细信息
        DatabaseMetaData metaData = connection.getMetaData();
        // 通过表名获取表中的字段
        ResultSet columns = metaData.getColumns(connection.getCatalog(), null, "users", null);
        // 根据表名获取表中的主键
        ResultSet primaryKeys = metaData.getPrimaryKeys(null, null, "users");
        // while (primaryKeys.next()) {
        //     System.out.println(primaryKeys.getString("COLUMN_NAME"));
        // }
        while (columns.next()) {
            String column_name = columns.getString("COLUMN_NAME");
            while (primaryKeys.next()) {
                System.out.println("进来了");
                String pkName = primaryKeys.getString("COLUMN_NAME");
                if (column_name.equals(pkName)) {
                    System.out.println("找到了！！！！！" +  column_name);
                }
            }
            primaryKeys.first();
        }
        // ResultSet rs = null;
        // try {
        //     DatabaseMetaData dbmd = conn.getMetaData();
        //     rs = dbmd.getPrimaryKeys(null, null, "users");
        //     while (rs.next()) {
        //         String tableCat = rs.getString("TABLE_CAT");  //表类别(可为null)
        //         String tableSchemaName = rs.getString("TABLE_SCHEM");//表模式（可能为空）,在oracle中获取的是命名空间,其它数据库未知
        //         String tableName = rs.getString("TABLE_NAME");  //表名
        //         String columnName = rs.getString("COLUMN_NAME");//列名
        //         short keySeq = rs.getShort("KEY_SEQ");//序列号(主键内值1表示第一列的主键，值2代表主键内的第二列)
        //         String pkName = rs.getString("PK_NAME"); //主键名称
        //         System.out.println(tableCat + " - " + tableSchemaName + " - " + tableName + " - " + columnName + " - " + keySeq + " - " + pkName);
        //     }
        // } catch (SQLException e) {
        //     e.printStackTrace();
        // }
    }

}
