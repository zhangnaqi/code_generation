package ${packageName}.controller;

import ${packageName}.entity.${entityName};
import ${packageName}.service.${serviceName};
import javax.annotation.Resource;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import io.swagger.annotations.Api;
import ${packageName}.utils.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import java.util.List;

@Api(tags = "${entityName}模块")
@RestController
@RequestMapping("/api/${entityName?uncap_first}")
@Slf4j
public class ${controllerName}{

    @Resource
    private ${serviceName} ${serviceName?uncap_first};

    @ApiOperation("添加")
    @PostMapping("/insert")
    public Result<?> insert(@RequestBody ${entityName} ${entityName?uncap_first}) {
        return Result.SUCCESS(${serviceName?uncap_first}.save(${entityName?uncap_first}));
    }

    @ApiOperation("修改")
    @PostMapping("/update")
    public Result<?> update(@RequestBody ${entityName} ${entityName?uncap_first}) {
        return Result.SUCCESS(${serviceName?uncap_first}.updateById(${entityName?uncap_first}));
    }

    @ApiOperation("删除")
    @GetMapping("/delete/{id}")
    public Result<?> delete(@PathVariable Integer id) {
        return Result.SUCCESS(${serviceName?uncap_first}.removeById(id));
    }

    @ApiOperation("查询所有 分页")
    @GetMapping("/selectAllPage/{currentPage}/{size}")
    public Result<?> selectAllPage(@PathVariable Integer currentPage,
                                @PathVariable Integer size) {
        return Result.SUCCESS(${serviceName?uncap_first}.page(new Page<${entityName}>(currentPage, size)));
    }

    @ApiOperation("查询所有")
    @GetMapping("/selectAll")
    public Result<?> selectAll() {
        return Result.SUCCESS(${serviceName?uncap_first}.list());
    }

    @ApiOperation("根据id查询")
    @GetMapping("/selectOne/{id}")
    public Result<?> selectOne(@PathVariable Integer id) {
        return Result.SUCCESS(${serviceName?uncap_first}.getById(id));
    }

}
