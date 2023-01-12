package ${packageName}.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import java.io.Serializable;
import lombok.Data;

@Data
@ApiModel(description = "${entityName}实体DTO")
public class ${entityName}DTO implements Serializable {
<#if columns??>
<#list columns as column>
<#if column.type='VARCHAR' || column.type='TEXT' || column.type='CHAR' || column.type='LONGTEXT'>
    @ApiModelProperty("${column.remark}")
    private String ${column.propertyName?uncap_first};
</#if>
<#if column.type='INT'>
    @ApiModelProperty("${column.remark}")
    private Integer ${column.propertyName?uncap_first};
</#if>
<#if column.type='DATETIME'>
    @ApiModelProperty("${column.remark}")
    private Date ${column.propertyName?uncap_first};
</#if>
<#if column.type='BIGINT'>
    @ApiModelProperty("${column.remark}")
    private Long ${column.propertyName?uncap_first};
</#if>
<#if column.type='DOUBLE'>
    @ApiModelProperty("${column.remark}")
    private Double ${column.propertyName?uncap_first};
</#if>
<#if column.type='BIT'>
    @ApiModelProperty("${column.remark}")
    private Boolean ${column.propertyName?uncap_first};
</#if>
</#list>
</#if>
}