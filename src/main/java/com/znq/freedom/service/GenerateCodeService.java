package com.znq.freedom.service;

import com.google.common.base.CaseFormat;
import com.znq.freedom.model.ColumnClass;
import com.znq.freedom.model.R;
import com.znq.freedom.model.TableClass;
import com.znq.freedom.utils.DBUtils;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @Author znq
 * @create 2022/9/20 21:47
 */
@Service
public class GenerateCodeService {

    Configuration cfg = null;

    {
        cfg = new Configuration(Configuration.VERSION_2_3_30);
        cfg.setTemplateLoader(new ClassTemplateLoader(GenerateCodeService.class, "/templates"));
        cfg.setDefaultEncoding("UTF-8");
    }

    /**
     * 生成模板
     * @param tableClassList 表的信息
     * @param realPath 相对路径
     * @return
     */
    public R generateCode(List<TableClass> tableClassList, String realPath) {
        try {
            // 获取模板
            Template pojoTemplate = cfg.getTemplate("PojoMybatisPlus.java.ftl");
            Template mapperJavaTemplate = cfg.getTemplate("MapperMybatisPlus.java.ftl");
            Template mapperXmlTemplate = cfg.getTemplate("MapperMybatisPlus.xml.ftl");
            Template serviceTemplate = cfg.getTemplate("ServiceMybatisPlus.java.ftl");
            Template serviceImplTemplate = cfg.getTemplate("ServiceImplMybatisPlus.java.ftl");
            Template controllerTemplate = cfg.getTemplate("ControllerMybatisPlus.java.ftl");
            // 获取数据库连接
            Connection connection = DBUtils.getConnection();
            // 获取数据库详细信息
            DatabaseMetaData metaData = connection.getMetaData();
            // 遍历前端传过来的要生成的表list
            for (TableClass tableClass : tableClassList) {
                // 通过表名获取表中的字段
                ResultSet columns = metaData.getColumns(connection.getCatalog(), null, tableClass.getTableName(), null);
                // 根据表名获取表中的主键
                ResultSet primaryKeys = metaData.getPrimaryKeys(connection.getCatalog(), null, tableClass.getTableName());
                List<ColumnClass> columnClassList = new ArrayList<>();
                // 循环表中的字段
                while (columns.next()) {
                    // 字段名
                    String column_name = columns.getString("COLUMN_NAME");
                    // 字段类型
                    String type_name = columns.getString("TYPE_NAME");
                    // 备注
                    String remarks = columns.getString("REMARKS");
                    // 将获取到的数据存储起来
                    ColumnClass columnClass = new ColumnClass();
                    columnClass.setRemark(remarks);
                    columnClass.setColumnName(column_name);
                    columnClass.setType(type_name);
                    // 处理前缀和后缀
                    if (tableClass.getColumnsPrefix() == null) tableClass.setColumnsPrefix("");
                    if (tableClass.getColumnsSuffix() == null) tableClass.setColumnsSuffix("");
                    String newColumnName = column_name.replace(tableClass.getColumnsPrefix(), "")
                            .replace(tableClass.getColumnsSuffix(), "");
                    columnClass.setPropertyName(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, newColumnName));
                    while (primaryKeys.next()) {
                        String pkName = primaryKeys.getString("COLUMN_NAME");
                        if (column_name.equals(pkName)) {
                            columnClass.setIsPrimary(true);
                            System.out.println(column_name);
                        }
                    }
                    primaryKeys.first();
                    columnClassList.add(columnClass);
                }
                tableClass.setColumns(columnClassList);
                String path = realPath + "/" + tableClass.getPackageName().replace(".", "/");
                generate(pojoTemplate, tableClass, path + "/pojo/");
                generate(mapperJavaTemplate, tableClass, path + "/mapper/");
                generate(mapperXmlTemplate, tableClass, path + "/mapper/mapper");
                generate(serviceImplTemplate, tableClass, path + "/service/impl/");
                generate(serviceTemplate, tableClass, path + "/service/");
                generate(controllerTemplate, tableClass, path + "/controller/");
            }
            return R.ok("代码已生成", realPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return R.error("代码生成失败");
    }

    // 输出最终代码 生成文件
    private void generate(Template template, TableClass tableClass, String path) throws IOException, TemplateException {
        File folder = new File(path);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String fileName = path + "/" + tableClass.getPojoName() + template.getName().replace("MybatisPlus", "").replace(".ftl", "").replace("Pojo", "");
        FileOutputStream fos = new FileOutputStream(fileName);
        OutputStreamWriter out = new OutputStreamWriter(fos);
        // 使用所提供的数据模型执行模板，并将生成的输出写入所提供的 Writer。
        template.process(tableClass,out);
        fos.close();
        out.close();
    }

}