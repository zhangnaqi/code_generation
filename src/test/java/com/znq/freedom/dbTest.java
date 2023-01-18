package com.znq.freedom;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class dbTest {

    @Test
    void dbPKTest() {
        Connection con = null;
        try {
            // Registering the Driver
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            // Getting the connection
            String url = "jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai";
            con = DriverManager.getConnection(url, "root", "@ZNQRoot123456");
            System.out.println("Connection established......");
            // Retrieving the meta data object
            DatabaseMetaData metaData = con.getMetaData();
            // Retrieving the columns in the database
            ResultSet rs = metaData.getPrimaryKeys("test", null, "tb_user");
            // Printing the column name and size
            while (rs.next()) {
                String pkName = rs.getString("COLUMN_NAME");
                String tbName = rs.getString("TABLE_NAME");
                String sql = "select " + pkName + " from " + tbName;
                PreparedStatement prepareStatement = con.prepareStatement(sql);
                // prepareStatement.setObject(1, pkName);
                // prepareStatement.setObject(2, tbName);
                ResultSet res = prepareStatement.executeQuery();
                ResultSetMetaData metaData1 = res.getMetaData();
                if (metaData1.isAutoIncrement(1)) {
                    System.out.println("自动递增id");
                }

                System.out.println("Table name:" + rs.getString("TABLE_NAME"));
                System.out.println("Column name:" + rs.getString("COLUMN_NAME"));
                System.out.println("Catalog name:" + rs.getString("TABLE_CAT"));
                System.out.println("Primary key sequence:" + rs.getString("KEY_SEQ"));
                System.out.println("Primary key name:" + rs.getString("PK_NAME"));
                System.out.println("");
            }
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("报错 ====》");
            System.out.println(e);
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

}
