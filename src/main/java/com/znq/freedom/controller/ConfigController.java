package com.znq.freedom.controller;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.znq.freedom.model.TableClass;
import com.znq.freedom.model.DTO.ConfigDTO;
import com.znq.freedom.service.ConfigService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * 对于生成代码的总体配置
 */
@Api(tags = "全局配置")
@RestController
@RequestMapping("/config")
@Slf4j
public class ConfigController {

    @Resource
    private ConfigService configService;

    // 对数据进行设置
    @ApiOperation("对数据进行设置")
    @PostMapping("/setting")
    public ArrayList<TableClass> config(@RequestBody ConfigDTO configDTO) {
        log.info("/config/setting ===> {}", configDTO);
        return configService.config(configDTO.getGlobalConfig(), configDTO.getDataBase());
    }

}
