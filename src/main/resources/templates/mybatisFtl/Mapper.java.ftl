package ${packageName}.mapper;

import ${packageName}.entity.${entityName};
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface ${mapperName} {
    
    int deleteByPrimaryKey(Integer id);

    int insert(${entityName} record);

    ${entityName} selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(${entityName} record);

    List<${entityName}> selectAll();
}