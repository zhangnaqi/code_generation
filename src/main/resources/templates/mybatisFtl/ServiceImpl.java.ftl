package ${packageName}.service.impl;

import ${packageName}.entity.${entityName};
import ${packageName}.mapper.${mapperName};
import ${packageName}.service.${serviceName};
import ${packageName}.utils.Result;
import ${packageName}.utils.MyPageInfoUtil;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

@Service
public class ${serviceName}Impl implements ${serviceName} {

    @Resource
    ${mapperName} ${mapperName?uncap_first};

    @Override
    public Result<?> deleteByPrimaryKey(Integer id) {
        return ${mapperName?uncap_first}.deleteByPrimaryKey(id)
                > 0 ? Result.SUCCESS() : Result.FAIL();
    }

    @Override
    public Result<?> insert(${entityName} record) {
        return ${mapperName?uncap_first}.insert(record)
                > 0 ? Result.SUCCESS() : Result.FAIL();
    }
    @Override
    public Result<?> updateByPrimaryKey(${entityName} record) {
        return ${mapperName?uncap_first}.updateByPrimaryKey(record)
                > 0 ? Result.SUCCESS() : Result.FAIL();
    }

    @Override
    public Result<?> selectByPrimaryKey(Integer id) {
        return Result.SUCCESS(${mapperName?uncap_first}.selectByPrimaryKey(id));
    }

    @Override
    public Result<?> selectAll() {
        return Result.SUCCESS(${mapperName?uncap_first}.selectAll());
    }
    
    @Override
    public Result<?> selectAllPage(Integer currentPage, Integer size) {
        PageHelper.startPage(currentPage, size);
        return Result.SUCCESS(MyPageInfoUtil.getPageInfoDetail(${mapperName?uncap_first}.selectAll()));
    }

}