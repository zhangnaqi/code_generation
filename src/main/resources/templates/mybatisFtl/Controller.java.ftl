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

    
}