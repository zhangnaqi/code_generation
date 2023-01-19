<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${packageName}.mapper.${mapperName}">

    <resultMap id="BaseResultMap" type="${packageName}.entity.${entityName}">
        <#list columns as column>
        <<#if column.columnName == pk.pkName>id<#else>result</#if> column="${column.columnName}" property="${column.propertyName?uncap_first}" jdbcType="<#if column.type='INT'>INTEGER<#elseif column.type='DATETIME'>TIMESTAMP<#elseif column.type='TEXT' || column.type='LONGTEXT'>VARCHAR<#else>${column.type}</#if>" />
        </#list>
    </resultMap>

    <sql id="Base_Column_List">
        <#list columns as column>
        ${column.columnName}<#sep>, </#sep>
        </#list>
    </sql>
    <!--根据主键查询-->
    <select id="selectByPrimaryKey" parameterType="java.lang.Integer" resultMap="BaseResultMap">
        select 
        <include refid="Base_Column_List" />
        from ${tableName}
        <#--  <#noparse> FreeMarker 不会在这个指令体中间寻找FTL标签， 插值和其他特殊的字符序列，除了noparse的结束标记。 -->
        where ${pk.pkName} = <#noparse>#{</#noparse>${pk.pkPropertyName?uncap_first} , jdbcType=<#if pk.pkType='INT'>INTEGER<#elseif pk.pkType='DATETIME'>TIMESTAMP<#elseif pk.pkType='TEXT' || pk.pkType='LONGTEXT'>VARCHAR<#else>${pk.pkType}</#if>}
    </select>
    <!--  查询全部信息  -->
    <select id="selectAll" resultMap="BaseResultMap">
        select
        <include refid="Base_Column_List" />
        from ${tableName}
    </select>
    <!--  根据主键删除  -->
    <delete id="deleteByPrimaryKey" parameterType="java.lang.Integer">
        delete from ${tableName}
        where ${pk.pkName} = <#noparse>#{</#noparse>${pk.pkPropertyName?uncap_first} , jdbcType=<#if pk.pkType='INT'>INTEGER<#elseif pk.pkType='DATETIME'>TIMESTAMP<#elseif pk.pkType='TEXT' || pk.pkType='LONGTEXT'>VARCHAR<#else>${pk.pkType}</#if>}
    </delete>
    <!--  根据主键添加  -->
    <insert id="insert" keyColumn="${pk.pkName}" 
            keyProperty="${pk.pkPropertyName?uncap_first}" 
            parameterType="${packageName}.entity.${entityName}" 
            useGeneratedKeys="true">
        insert into ${tableName} (<#list columns as column><#if column != pk.pkName>${column.columnName}<#sep>, </#sep></#if></#list>)
        values (<#list columns as column><#if column != pk.pkName><#noparse>#{</#noparse>${column.propertyName?uncap_first} , jdbcType=<#if column.type='INT'>INTEGER<#elseif column.type='DATETIME'>TIMESTAMP<#elseif column.type='TEXT' || column.type='LONGTEXT'>VARCHAR<#else>${column.type}</#if>}<#sep>, </#sep></#if></#list>)
    </insert>
    <!-- 根据主键修改  -->
    <update id="updateByPrimaryKey" parameterType="${packageName}.entity.${entityName}">
        update ${tableName}
        set
        <#list columns as column>
            <#if column != pk.pkName>${column.columnName} = <#noparse>#{</#noparse>${column.propertyName?uncap_first} , jdbcType=<#if column.type='INT'>INTEGER<#elseif column.type='DATETIME'>TIMESTAMP<#elseif column.type='TEXT' || column.type='LONGTEXT'>VARCHAR<#else>${column.type}</#if>}<#sep>,</#sep></#if>
        </#list>
        where ${pk.pkName} = <#noparse>#{</#noparse>${pk.pkPropertyName?uncap_first} , jdbcType=<#if pk.pkType='INT'>INTEGER<#elseif pk.pkType='DATETIME'>TIMESTAMP<#elseif pk.pkType='TEXT' || pk.pkType='LONGTEXT'>VARCHAR<#else>${pk.pkType}</#if>}
    </update>
</mapper>