# 实现自由的后端基础代码生成工具

项目使用freemarker，编写模板文件，实现生成java代码的目的。

项目参考<https://github.com/wz20/generate_code>。

项目目前能够生成MybatisPlus的一套基础代码，由于本项目为简化学校工作室开发，故编写简单。

使用项目可在

本地运行后访问<http://localhost:8080/static/freedom.html>

| 表 TableClass  |                |
| -------------- | -------------- |
| tableName      | 表名           |
| columnsPrefix  | 字段去除前缀   |
| columnsSuffix  | 字段去除后缀   |
| pojoName       | entity名字     |
| serviceName    | service名字    |
| mapperName     | Mapper名字     |
| controllerName | controller名字 |
| packageName    | 包名           |
| columns        | 行数据         |



| 行数据 ColumnClass |                    |
| ------------------ | ------------------ |
| propertyName       | java属性的名字     |
| columnName         | 数组库字段名       |
| type               | 字段类型           |
| remark             | 备注               |
| isPrimary          | 字段是不是一个主键 |

