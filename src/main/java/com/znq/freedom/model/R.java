package com.znq.freedom.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author znq
 * @create 2022/9/20 15:34
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class R {

    private Integer status;
    private String msg;
    private Object obj;

    public static R ok(String msg, Object obj) {
        return new R(200, msg, obj);
    }

    public static R ok(String msg) {
        return new R(200, msg, null);
    }

    public static R error(String msg, Object obj) {
        return new R(500, msg, obj);
    }

    public static R error(String msg) {
        return new R(500, msg, null);
    }

}
