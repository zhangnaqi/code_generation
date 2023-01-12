package ${packageName}.service.impl;

import ${packageName}.entity.${entityName};
import ${packageName}.mapper.${mapperName};
import ${packageName}.service.${serviceName};
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import javax.annotation.Resource;

@Service
@Slf4j
public class ${serviceName}Impl extends ServiceImpl<${mapperName}, ${entityName}>
    implements ${serviceName} {

    @Resource
    private ${mapperName} ${mapperName?uncap_first};

}