package com.znq.freedom;

import java.io.IOException;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.google.common.base.CaseFormat;
import com.znq.freedom.model.ColumnClass;
import com.znq.freedom.model.DataBase;
import com.znq.freedom.model.PrimaryKey;
import com.znq.freedom.model.StaticInfo;
import com.znq.freedom.model.TableClass;
import com.znq.freedom.model.TemplatesType;
import com.znq.freedom.utils.DBUtils;
import com.znq.freedom.utils.ObjMapUtils;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@SpringBootTest
public class ControllerPreviewEncodingTest {

    @Test
    void encodingTest() throws Exception {

        Configuration cfg = new Configuration(Configuration.VERSION_2_3_30);
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateLoader(new ClassTemplateLoader(TemplatesType.class,
                "/templates/mybatisFtl"));
        Template template = cfg.getTemplate("Controller.java.ftl");

        DataBase dataBase = new DataBase();
        dataBase.setUrl(
                "jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai");
        dataBase.setUsername("root");
        dataBase.setPassword("@ZNQRoot123456");
        Connection connection = DBUtils.initDB(dataBase);

        TableClass tableClass = new TableClass();
        tableClass.setTableName("tb_news");
        tableClass.setControllerName("TbNewsController");
        tableClass.setEntityName("TbNews");
        tableClass.setMapperName("TbNewsMapper");
        tableClass.setServiceName("TbNewsService");

        // 获取数据库详细信息
        DatabaseMetaData metaData = connection.getMetaData();
        // 通过表名获取表中的字段
        ResultSet columns = metaData.getColumns(connection.getCatalog(),
                null, tableClass.getTableName(), null);
        // 根据表名获取表中的主键
        ResultSet primaryKeys = metaData.getPrimaryKeys(connection.getCatalog(),
                null, tableClass.getTableName());
        // 主键
        PrimaryKey pk = new PrimaryKey();
        while (primaryKeys.next()) {
            pk.setPkName(primaryKeys.getString("COLUMN_NAME"));
            // 主键是否递增
            String sql = "select " + pk.getPkName() + " from " + tableClass.getTableName();
            PreparedStatement prepareStatement = connection.prepareStatement(sql);
            ResultSet res = prepareStatement.executeQuery();
            ResultSetMetaData metaData1 = res.getMetaData();
            if (metaData1.isAutoIncrement(1)) {
                // System.out.println("自动递增id");
                pk.setPKAuto(true);
            }
            // 主键在bean类中的存储
            String newColumnName = pk.getPkName().replace(tableClass.getColumnsPrefix(), "")
                    .replace(tableClass.getColumnsSuffix(), "");
            pk.setPkPropertyName(CaseFormat.LOWER_UNDERSCORE
                    .to(CaseFormat.UPPER_CAMEL, newColumnName));
        }
        primaryKeys.first();
        tableClass.setPk(pk);
        List<ColumnClass> columnClassList = new ArrayList<>();
        // 循环表中的字段
        while (columns.next()) {
            // 字段名
            String column_name = columns.getString("COLUMN_NAME");
            // 字段类型
            String type_name = columns.getString("TYPE_NAME");
            // 备注
            String remarks = columns.getString("REMARKS");
            // 主键类型添加
            if (pk.getPkName().equals(column_name)) {
                pk.setPkType(type_name);
            }
            // 将获取到的数据存储起来
            ColumnClass columnClass = new ColumnClass();
            columnClass.setRemark(remarks);
            columnClass.setColumnName(column_name);
            columnClass.setType(type_name);
            // 处理前缀和后缀
            String newColumnName = column_name.replace(tableClass.getColumnsPrefix(), "")
                    .replace(tableClass.getColumnsSuffix(), "");
            columnClass.setPropertyName(CaseFormat.LOWER_UNDERSCORE
                    .to(CaseFormat.UPPER_CAMEL, newColumnName));
            columnClassList.add(columnClass);
            tableClass.setColumns(columnClassList);
        }

        String packageName = "com.znq.test";
        // 数据
        Map<String, Object> convertObjToMap = ObjMapUtils.convertObjToMap(tableClass);
        convertObjToMap.put("packageName", packageName);

        String res = preview(template, convertObjToMap).replaceAll("\\n|\\r", "<br>");

        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("key", res);

        System.out.println("res ===========> " + res);
        System.out.println("res HasMap ==========>" + hashMap.get("key"));

    }

    private String preview(Template template, Map<String, Object> map)
            throws TemplateException, IOException {
        StringWriter stringWriter = new StringWriter();
        template.process(map, stringWriter);
        return stringWriter.toString();
    }

}
