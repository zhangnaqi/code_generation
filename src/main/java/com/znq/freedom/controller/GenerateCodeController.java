package com.znq.freedom.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.znq.freedom.model.TableClass;
import com.znq.freedom.service.GenerateCodeService;
import com.znq.freedom.utils.Result;

/**
 * @Author znq
 * @create 2022/9/20 21:46
 * 文件生成
 */
@RestController
public class GenerateCodeController {

    @Resource
    GenerateCodeService generateCodeService;



    // @PostMapping("/generateCode")
    // public R generateCode(@RequestBody List<TableClass> tableClassList, HttpServletRequest req) {
    //     return generateCodeService.generateCode(tableClassList, req.getServletContext().getRealPath("/"));
    // }

    // 生成到绝对路径当中
    @PostMapping("/generateCode/{path}")
    public Result generateCode(@RequestBody List<TableClass> tableClassList, @PathVariable String path) {
        return generateCodeService.generateCode(tableClassList, path.replace('-', '\\'));
    }

    // 查看代码不生成
    // @PostMapping("/showGenerateCode")
    // public R showGenerateCode(@RequestBody TableClass tableClass) {
    //     return generateCodeService.showGenerateCode(tableClass);
    // }

}
