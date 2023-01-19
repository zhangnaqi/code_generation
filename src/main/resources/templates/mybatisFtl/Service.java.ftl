package ${packageName}.service;

import ${packageName}.entity.${entityName};
import ${packageName}.utils.Result;

import java.util.List;

public interface ${serviceName} {

    Result<?> deleteByPrimaryKey(Integer id);

    Result<?> insert(${entityName} record);

    Result<?> updateByPrimaryKey(${entityName} record);
    
    Result<?> selectByPrimaryKey(Integer id);

    Result<?> selectAll();

    Result<?> selectAllPage(Integer currentPage, Integer size);
}
