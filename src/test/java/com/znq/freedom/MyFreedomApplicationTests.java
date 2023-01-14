package com.znq.freedom;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.znq.freedom.model.DataBase;
import com.znq.freedom.model.GlobalConfig;
import com.znq.freedom.model.TableClass;
import com.znq.freedom.service.ConfigService;
import com.znq.freedom.service.GenerateCodeService;
import com.znq.freedom.utils.Result;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.FileTemplateLoader;
import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
class MyFreedomApplicationTests {

    @Resource
    private ConfigService configService;

    @Resource
    private GenerateCodeService generateCodeService;

    // jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai
    // root
    // @ZNQRoot123456

    @Test
    void contextLoads() {
        DataBase dataBase = new DataBase();
        dataBase.setUrl(
                "jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai");
        dataBase.setUsername("root");
        dataBase.setPassword("@ZNQRoot123456");

        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setPackageName("com.znq.test");
        globalConfig.setTablePrefix(null);
        globalConfig.setTableSuffix(null);
        globalConfig.setTemplatesType(null);

        ArrayList<TableClass> config = configService.config(globalConfig, dataBase);

        Result generateCode = generateCodeService.generateCode(config, "D:/allproject/base/test");
        System.out.println(generateCode);

    }

    @Test
    void ftlTest() {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_30);
        try {
            cfg.setTemplateLoader(
                    new FileTemplateLoader(
                            new File(
                                    "D:/allproject/base/my_freedom/src/test/java/com/znq/freedom/testFtl/")));
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        cfg.setDefaultEncoding("UTF-8");
        try {
            Template template = cfg.getTemplate("test1.java.ftl");
            this.generate(template);
        } catch (TemplateNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (MalformedTemplateNameException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (TemplateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void generate(Template template)
            throws IOException, TemplateException {
        FileOutputStream fos = new FileOutputStream(
                "D:/allproject/base/my_freedom/src/test/java/com/znq/freedom/testFtl/g/test.txt");
        OutputStreamWriter out = new OutputStreamWriter(fos);
        // 使用所提供的数据模型执行模板，并将生成的输出写入所提供的 Writer。
        template.process(null, out);
        fos.close();
        out.close();
    }

    @Test
    void testPackNameMkdir() {
        String packageName = "com.znq.test";
        String substring = packageName.substring(packageName.lastIndexOf(".") + 1);
        System.out.println(substring);
    }

}
