package com.znq.freedom;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
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
import com.znq.freedom.model.TableClass;
import com.znq.freedom.model.sqlTemplatesData.SqlClass;
import com.znq.freedom.model.sqlTemplatesData.SqlColumn;
import com.znq.freedom.model.sqlTemplatesData.SqlLabelAttribute;
import com.znq.freedom.utils.DBUtils;

import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class SqlFtlTest {

    @Test
    void sqlFtlTest() {
        try {
            // 数据库
            DataBase dataBase = new DataBase();
            dataBase.setUrl(
                    "jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai");
            dataBase.setUsername("root");
            dataBase.setPassword("@ZNQRoot123456");
            Connection connection = DBUtils.initDB(dataBase);
            // 数据库详细信息获取
            DatabaseMetaData metaData = connection.getMetaData();
            TableClass tableClass = new TableClass();
            tableClass.setTableName("tb_news");
            this.getDBInfo(connection, metaData, tableClass);
            // 生成sql的数据整理
            SqlClass sqlClass = new SqlClass();
            sqlClass.setSqlType("select");
            sqlClass.setResultType("one");
            // 属性数据
            SqlLabelAttribute sqlLabelAttribute = new SqlLabelAttribute();
            sqlLabelAttribute.setId("id");
            sqlClass.setLabelAttribute(sqlLabelAttribute);
            // 表中数据
            SqlColumn sqlColumn = new SqlColumn();
            sqlColumn.setTableName(tableClass.getTableName());
            // 选择字段
            ArrayList<ColumnClass> arrayList = new ArrayList<>();
            arrayList.add(tableClass.getColumns().get(0));
            arrayList.add(tableClass.getColumns().get(1));
            arrayList.add(tableClass.getColumns().get(2));
            sqlColumn.setColumnClassList(arrayList);
            // 关键连接字
            sqlColumn.setConnectionKeyList(new String[] { "", "and", "and" });
            sqlClass.setColumn(sqlColumn);
            // where if
            // sqlClass.setColumnIf(true);

            // 数据生成
            Configuration cfg = new Configuration(Configuration.VERSION_2_3_30);
            cfg.setDefaultEncoding("UTF-8");
            cfg.setTemplateLoader(
                    new FileTemplateLoader(
                            new File(
                                    "D:/allproject/base/my_freedom/src/main/resources/templates/sqlTemplates")));
            Template template = cfg.getTemplate("sql.ftl");
            String preview = this.preview(template, sqlClass);
            System.out.println("生成的模板sql\n" + preview);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 输出最终代码，字符串形式返回
    private String preview(Template template, Object map)
            throws TemplateException, IOException {
        StringWriter stringWriter = new StringWriter();
        template.process(map, stringWriter);
        return stringWriter.toString();
    }

    private void getDBInfo(Connection connection,
            DatabaseMetaData metaData,
            TableClass tableClass) {
        try {
            // 通过表名获取表中的字段
            ResultSet columns = metaData.getColumns(connection.getCatalog(), null, tableClass.getTableName(), null);
            // 根据表名获取表中的主键
            ResultSet primaryKeys = metaData.getPrimaryKeys(connection.getCatalog(), null,
                    tableClass.getTableName());
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
            }
            tableClass.setColumns(columnClassList);
        } catch (SQLException e) {
            log.info("获取数据库表详细信息失败 ====》", tableClass.getTableName());
            e.printStackTrace();
            throw new RuntimeException("获取数据库详细信息失败，表名： ====》 " + tableClass.getTableName());
        }
    }

}
