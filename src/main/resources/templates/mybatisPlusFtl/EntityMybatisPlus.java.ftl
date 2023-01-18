package ${packageName}.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@TableName(value = "${tableName}")
@Data
public class ${entityName} implements Serializable {
<#if columns??>
<#list columns as column>
<#if column.type='VARCHAR' || column.type='TEXT' || column.type='CHAR' || column.type='LONGTEXT'>
    /**
    * ${column.remark}
    */
    <#if column.columnName == pk.pkName>
    <#if pk.PKAuto>
    @TableId(value = "${column.columnName}", type = IdType.AUTO)
    <#else>
    @TableId(value = "${column.columnName}")
    </#if>
    <#else>
    @TableField(value = "${column.columnName}")
    </#if>
    private String ${column.propertyName?uncap_first};
</#if>
<#if column.type='INT'>
    /**
    * ${column.remark}
    */
    <#if column.columnName == pk.pkName>
    <#if pk.PKAuto>
    @TableId(value = "${column.columnName}", type = IdType.AUTO)
    <#else>
    @TableId(value = "${column.columnName}")
    </#if>
    <#else>
    @TableField(value = "${column.columnName}")
    </#if>
    private Integer ${column.propertyName?uncap_first};
</#if>
<#if column.type='DATETIME'>
    /**
    * ${column.remark}
    */
    <#if column.columnName == pk.pkName>
    <#if pk.PKAuto>
    @TableId(value = "${column.columnName}", type = IdType.AUTO)
    <#else>
    @TableId(value = "${column.columnName}")
    </#if>
    <#else>
    @TableField(value = "${column.columnName}")
    </#if>
    private Date ${column.propertyName?uncap_first};
</#if>
<#if column.type='BIGINT'>
    /**
    * ${column.remark}
    */
    <#if column.columnName == pk.pkName>
    <#if pk.PKAuto>
    @TableId(value = "${column.columnName}", type = IdType.AUTO)
    <#else>
    @TableId(value = "${column.columnName}")
    </#if>
    <#else>
    @TableField(value = "${column.columnName}")
    </#if>
    private Long ${column.propertyName?uncap_first};
</#if>
<#if column.type='DOUBLE'>
    /**
    * ${column.remark}
    */
    <#if column.columnName == pk.pkName>
    <#if pk.PKAuto>
    @TableId(value = "${column.columnName}", type = IdType.AUTO)
    <#else>
    @TableId(value = "${column.columnName}")
    </#if>
    <#else>
    @TableField(value = "${column.columnName}")
    </#if>
    private Double ${column.propertyName?uncap_first};
</#if>
<#if column.type='BIT'>
    /**
    * ${column.remark}
    */
    <#if column.columnName == pk.pkName>
    <#if pk.PKAuto>
    @TableId(value = "${column.columnName}", type = IdType.AUTO)
    <#else>
    @TableId(value = "${column.columnName}")
    </#if>
    <#else>
    @TableField(value = "${column.columnName}")
    </#if>
    private Boolean ${column.propertyName?uncap_first};
</#if>
</#list>
</#if>
}


