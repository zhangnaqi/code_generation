package com.znq.freedom.controller;

import java.util.ArrayList;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.znq.freedom.model.DataBase;
import com.znq.freedom.model.GlobalConfig;
import com.znq.freedom.model.TableClass;
import com.znq.freedom.model.DTO.ConfigDTO;
import com.znq.freedom.service.ConfigService;

/**
 * 对于生成代码的总体配置
 */
@RestController
@RequestMapping("/config")
public class ConfigController {

    @Resource
    private ConfigService configService;

    // 对数据进行设置
    @PostMapping("/setting")
    public ArrayList<TableClass> config(@RequestBody ConfigDTO configDTO) {
        return configService.config(configDTO.getGlobalConfig(), configDTO.getDataBase());
    }

}
