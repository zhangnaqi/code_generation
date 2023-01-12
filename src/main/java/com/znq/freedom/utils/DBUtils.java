package com.znq.freedom.utils;

import com.znq.freedom.model.DataBase;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @Author znq
 * @create 2022/9/20 15:56
 */
@Slf4j
public class DBUtils {

    private static Connection connection;

    public static Connection getConnection() {
        return connection;
    }

    public static Connection initDB(DataBase db) {
        if (connection == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(db.getUrl(), db.getUsername(), db.getPassword());
            } catch (ClassNotFoundException | SQLException e) {
                log.info("数据库连接失败 ===>");
                e.printStackTrace();
                throw new RuntimeException("数据库连接失败");
            }
        }
        return connection;
    }

    public static void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            log.info("数据库关闭失败 ===> ");
            e.printStackTrace();
            throw new RuntimeException("数据库关闭失败");
        }
    }

}
