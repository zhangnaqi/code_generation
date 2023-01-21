<${sqlType} id="${labelAttribute.id}" resultMap="${labelAttribute.resultMap}">
<#--  include默认 Base_column_List  -->
<#--  select 开始  -->
<#if sqlType == 'select'>
<#--  需不需要if 开始 -->
<#if !columnIf>
    select <include refid="${labelAttribute.include}"/>
    from ${column.tableName}
    where 
    <#list column.columnClassList as columnItem>
    ${column.connectionKeyList[columnItem_index]} ${columnItem.columnName}=<#noparse>#{</#noparse>${columnItem.propertyName?uncap_first} , jdbcType=<#if columnItem.type='INT'>INTEGER<#elseif columnItem.type='DATETIME'>TIMESTAMP<#elseif columnItem.type='TEXT' || columnItem.type='LONGTEXT'>VARCHAR<#else>${columnItem.type}</#if><#noparse>}</#noparse>
    </#list>
<#else>
    select
    <include refid="${labelAttribute.include}" />
    from ${column.tableName}
    <where>
        <#list column.columnClassList as columnItem>
        <if test="${columnItem.propertyName?uncap_first} != null">
            ${column.connectionKeyList[columnItem_index]} ${columnItem.columnName}=<#noparse>#{</#noparse>${columnItem.propertyName?uncap_first} , jdbcType=<#if columnItem.type='INT'>INTEGER<#elseif columnItem.type='DATETIME'>TIMESTAMP<#elseif columnItem.type='TEXT' || columnItem.type='LONGTEXT'>VARCHAR<#else>${columnItem.type}</#if><#noparse>}</#noparse>
        </if>
        </#list>
    </where>
</#if>
<#--  需不需要if 结束 -->
<#--  select 结束  -->
</#if>
</${sqlType}>