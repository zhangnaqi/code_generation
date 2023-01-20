package com.znq.freedom.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.znq.freedom.model.TableClass;
import com.znq.freedom.service.GenerateCodeService;
import com.znq.freedom.utils.Result;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author znq
 * @create 2022/9/20 21:46
 *         文件生成
 */
@Api(tags = "模板生成")
@RestController
@RequestMapping("/template")
@Slf4j
public class GenerateCodeController {

    @Resource
    GenerateCodeService generateCodeService;

    // 生成到绝对路径当中
    @ApiOperation("生成文件")
    @PostMapping("/generateCode/{path}")
    public Result<?> generateCode(@RequestBody List<TableClass> tableClassList, @PathVariable String path) {
        log.info("/template/generateCode/{path} ===> tableClassList: {}, path: {}", tableClassList, path);
        return generateCodeService.generateCode(tableClassList, path.replace('-', '/'));
    }

    // 对单独的表进行预览
    @ApiOperation("根据表名对当前表生成代码进行预览")
    @PostMapping("/preview")
    public Result<?> previewCode(@RequestBody TableClass tableClass) {
        log.info("/template/preview ===> {}", tableClass);
        return generateCodeService.previewCode(tableClass);
    }

}
