package ${packageName}.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import java.io.Serializable;
import lombok.Data;

@Data
public class ${entityName}Vo implements Serializable {

<#if columns??>
<#list columns as column>
<#if column.type='VARCHAR' || column.type='TEXT' || column.type='CHAR' || column.type='LONGTEXT'>
    /**
    * ${column.remark}
    */
    private String ${column.propertyName?uncap_first};
</#if>
<#if column.type='INT'>
    /**
    * ${column.remark}
    */
    private Integer ${column.propertyName?uncap_first};
</#if>
<#if column.type='DATETIME'>
    /**
    * ${column.remark}
    */
    private Date ${column.propertyName?uncap_first};
</#if>
<#if column.type='BIGINT'>
    /**
    * ${column.remark}
    */
    private Long ${column.propertyName?uncap_first};
</#if>
<#if column.type='DOUBLE'>
    /**
    * ${column.remark}
    */
    private Double ${column.propertyName?uncap_first};
</#if>
<#if column.type='BIT'>
    /**
    * ${column.remark}
    */
    private Boolean ${column.propertyName?uncap_first};
</#if>
</#list>
</#if>
}