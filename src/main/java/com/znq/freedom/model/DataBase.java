package com.znq.freedom.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author znq
 * @create 2022/9/20 15:31
 *         数据库基础信息
 */
@Data
public class DataBase {

    private String username;
    private String password;
    private String url;

}
