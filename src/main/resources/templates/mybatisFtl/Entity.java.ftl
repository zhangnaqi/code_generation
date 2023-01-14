package ${packageName}.entity;

import lombok.Data;
import java.util.Date;

@Data
public class ${entityName} {

<#if columns??>
<#list columns as column>
<#if column.type='VARCHAR'||column.type='TEXT'||column.type='CHAR'>
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