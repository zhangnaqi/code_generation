package com.znq.freedom.controller;

import java.sql.Connection;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.znq.freedom.model.DataBase;
import com.znq.freedom.utils.DBUtils;
import com.znq.freedom.utils.Result;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author znq
 * @create 2022/9/20 16:02
 *         数据库连接，以及数据库表的设置
 */
@Api(tags = "数据库配置")
@RestController
@RequestMapping("/db")
@Slf4j
public class DBController {

    // 测试数据库连接
    @ApiOperation("测试数据库连接")
    @PostMapping("/connectTest")
    public Result<?> connect(@RequestBody DataBase dataBase) {
        log.info("/db/connectTest ===> {}", dataBase);
        Connection con = DBUtils.initDB(dataBase);
        if (con != null) {
            DBUtils.close();
            return Result.success("数据库连接成功", null);
        }
        throw new RuntimeException("数据库连接失败");
    }

    // 关闭数据库连接
    @ApiOperation("关闭数据库连接")
    @PostMapping("/close")
    public Result<?> closeDB() {
        log.info("/db/close ===> ");
        DBUtils.close();
        return Result.success(null);
    }

}
