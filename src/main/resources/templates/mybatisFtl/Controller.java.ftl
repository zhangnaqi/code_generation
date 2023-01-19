package ${packageName}.controller;

import ${packageName}.entity.${entityName};
import ${packageName}.service.${serviceName};
import javax.annotation.Resource;
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
        return ${serviceName?uncap_first}.insert(${entityName?uncap_first});
    }

    @ApiOperation("修改")
    @PostMapping("/updateByPrimaryKey")
    public Result<?> updateByPrimaryKey(@RequestBody ${entityName} ${entityName?uncap_first}) {
        return ${serviceName?uncap_first}.updateByPrimaryKey(${entityName?uncap_first});
    }

    @ApiOperation("删除")
    @GetMapping("/deleteByPrimaryKey/{id}")
    public Result<?> deleteByPrimaryKey(@PathVariable Integer id) {
        return ${serviceName?uncap_first}.deleteByPrimaryKey(id);
    }

    @ApiOperation("根据id查询")
    @GetMapping("/selectByPrimaryKey/{id}")
    public Result<?> selectByPrimaryKey(@PathVariable Integer id) {
        return ${serviceName?uncap_first}.selectByPrimaryKey(id);
    }

    @ApiOperation("查询全部")
    @GetMapping("/selectAll")
    public Result<?> selectAll() {
        return ${serviceName?uncap_first}.selectAll();
    }

    @ApiOperation("查询全部 分页")
    @GetMapping("/selectAllPage/{currentPage}/{size}")
    public Result<?> selectAllPage(@PathVariable Integer currentPage,
                                @PathVariable Integer size) {
        return ${serviceName?uncap_first}.selectAllPage(currentPage, size);
    }


}