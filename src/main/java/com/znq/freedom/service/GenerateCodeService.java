package com.znq.freedom.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
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

import org.springframework.stereotype.Service;

import com.google.common.base.CaseFormat;
import com.znq.freedom.model.ColumnClass;
import com.znq.freedom.model.PrimaryKey;
import com.znq.freedom.model.StaticInfo;
import com.znq.freedom.model.TableClass;
import com.znq.freedom.model.TemplateConfig;
import com.znq.freedom.model.TemplatesType;
import com.znq.freedom.utils.DBUtils;
import com.znq.freedom.utils.ObjMapUtils;
import com.znq.freedom.utils.Result;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author znq
 * @create 2022/9/20 21:47
 */
@Service
@Slf4j
public class GenerateCodeService {

    /**
     * 生成模板
     * 
     * @param tableClassList 表的信息
     * @param realPath       相对路径
     * @return
     */
    public Result<?> generateCode(List<TableClass> tableClassList, String realPath) {
        TemplateConfig tc = null;
        try {
            // 配置模板
            tc = TemplatesType.valueOf(StaticInfo.GLOBAL_CONFIG.getTemplatesType()).template();
            // 获取数据库连接
            Connection connection = DBUtils.getConnection();
            // 获取数据库详细信息
            DatabaseMetaData metaData = connection.getMetaData();
            // 路径包名
            String packageName = StaticInfo.GLOBAL_CONFIG.getPackageName();
            String path = new StringBuffer()
                    .append(realPath)
                    .append("/")
                    .append(packageName.substring(packageName.lastIndexOf(".") + 1))
                    .toString();
            // 遍历前端传过来的要生成的表list
            for (TableClass tableClass : tableClassList) {
                // 对数据库信息的处理
                this.getDBInfo(connection, metaData, tableClass);
                // 数据
                Map<String, Object> convertObjToMap = ObjMapUtils.convertObjToMap(tableClass);
                convertObjToMap.put("packageName", packageName);
                // 根据模板生成文件
                generate(tc.getEntityTemplate(), convertObjToMap, path + "/entity/");
                generate(tc.getMapperJavaTemplate(), convertObjToMap, path + "/mapper/");
                generate(tc.getMapperXmlTemplate(), convertObjToMap, path + "/mapperXml/");
                generate(tc.getServiceTemplate(), convertObjToMap, path + "/service/impl/");
                generate(tc.getServiceImplTemplate(), convertObjToMap, path + "/service/");
                generate(tc.getControllerTemplate(), convertObjToMap, path + "/controller/");
            }
            return Result.success("代码已生成", path);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("代码生成失败");
        }
    }

    public Result<?> previewCode(TableClass tableClass) {
        TemplateConfig tc = null;
        // 返回的数据
        Map<String, Object> resultMap = new HashMap<>();
        try {
            tc = TemplatesType.valueOf(StaticInfo.GLOBAL_CONFIG.getTemplatesType()).template();
            // 获取数据库连接
            Connection connection = DBUtils.getConnection();
            // 获取数据库详细信息
            DatabaseMetaData metaData = connection.getMetaData();
            // 对数据库信息的处理
            this.getDBInfo(connection, metaData, tableClass);
            // 获取包名
            String packageName = StaticInfo.GLOBAL_CONFIG.getPackageName();
            // 数据
            Map<String, Object> convertObjToMap = ObjMapUtils.convertObjToMap(tableClass);
            convertObjToMap.put("packageName", packageName);
            // 根据模板生成文件
            resultMap.put("entityTemplate",
                    preview(tc.getEntityTemplate(), convertObjToMap).replaceAll("\\n|\\r", "<br>"));
            resultMap.put("mapperJavaTemplate",
                    preview(tc.getMapperJavaTemplate(), convertObjToMap).replaceAll("\\n|\\r", "<br>"));
            resultMap.put("mapperXmlTemplate",
                    preview(tc.getMapperXmlTemplate(), convertObjToMap).replaceAll("\\n|\\r", "<br>"));
            resultMap.put("serviceTemplate",
                    preview(tc.getServiceTemplate(), convertObjToMap).replaceAll("\\n|\\r", "<br>"));
            resultMap.put("serviceImplTemplate",
                    preview(tc.getServiceImplTemplate(), convertObjToMap).replaceAll("\\n|\\r", "<br>"));
            resultMap.put("controllerTemplate",
                    preview(tc.getControllerTemplate(), convertObjToMap).replaceAll("\\n|\\r", "<br>"));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("代码生成失败");
        }
        return Result.success(resultMap);
    }

    // 输出最终代码 生成文件
    private void generate(Template template, Map<String, Object> map, String path)
            throws IOException, TemplateException {
        File folder = new File(path);
        if (!folder.exists())
            folder.mkdirs();
        String fileName = path + "/" + map.get("entityName")
                + template.getName()
                        .replace("MybatisPlus", "")
                        .replace(".ftl", "")
                        .replace("Entity", "");
        FileOutputStream fos = new FileOutputStream(fileName);
        OutputStreamWriter out = new OutputStreamWriter(fos);
        // 使用所提供的数据模型执行模板，并将生成的输出写入所提供的 Writer。
        template.process(map, out);
        fos.close();
        out.close();
    }

    // 输出最终代码，字符串形式返回
    private String preview(Template template, Map<String, Object> map)
            throws TemplateException, IOException {
        StringWriter stringWriter = new StringWriter();
        template.process(map, stringWriter);
        return stringWriter.toString();
    }

    /**
     * 获取数据库详细信息
     * 
     * @param connection 数据库连接
     * @param metaData   数据库详细信息
     * @param tableClass 存储数据库的表名等信息
     */
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