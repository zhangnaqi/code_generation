package com.znq.freedom.controller;

import com.znq.freedom.model.R;
import com.znq.freedom.model.TableClass;
import com.znq.freedom.service.GenerateCodeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author znq
 * @create 2022/9/20 21:46
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
    public R generateCode(@RequestBody List<TableClass> tableClassList, @PathVariable String path) {
        return generateCodeService.generateCode(tableClassList, path.replace('-', '\\'));
    }

    // 查看代码不生成
    // @PostMapping("/showGenerateCode")
    // public R showGenerateCode(@RequestBody TableClass tableClass) {
    //     return generateCodeService.showGenerateCode(tableClass);
    // }

}
