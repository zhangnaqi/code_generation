<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${packageName}.mapper.${mapperName}">

    <resultMap id="BaseResultMap" type="${packageName}.entity.${entityName}">
        <#list columns as column>
        <<#if column.primary??>id<#else>result</#if> column="${column.columnName}" property="${column.propertyName?uncap_first}" jdbcType="<#if column.type='INT'>INTEGER<#elseif column.type='DATETIME'>TIMESTAMP<#elseif column.type='TEXT' || column.type='LONGTEXT'>VARCHAR<#else>${column.type}</#if>" />
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
        from users
        where user_id = #{userId,jdbcType=INTEGER}
    </select>

    
</mapper>