package com.znq.freedom.model;

import java.io.IOException;

import com.znq.freedom.service.TemplateOperation;

import freemarker.cache.ClassTemplateLoader;
import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.TemplateNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum TemplatesType implements TemplateOperation {

    MYBATIS {
        @Override
        public TemplateConfig template() {
            Configuration cfg = new Configuration(Configuration.VERSION_2_3_30);
            cfg.setTemplateLoader(new ClassTemplateLoader(TemplatesType.class,
                    "/templates/mybatisFtl"));
            TemplateConfig tc = new TemplateConfig();
            // 获取模板
            try {
                tc.setEntityTemplate(cfg.getTemplate("Entity.java.ftl"));
                tc.setMapperJavaTemplate(cfg.getTemplate("Mapper.java.ftl"));
                tc.setMapperXmlTemplate(cfg.getTemplate("ServiceImpl.java.ftl"));
                tc.setServiceTemplate(cfg.getTemplate("Service.java.ftl"));
                tc.setServiceImplTemplate(cfg.getTemplate("ServiceImpl.java.ftl"));
                tc.setControllerTemplate(cfg.getTemplate("Controller.java.ftl"));
                cfg.setDefaultEncoding("UTF-8");
                tc.setCfg(cfg);
            } catch (TemplateNotFoundException e) {
                log.info("TemplatesType mybatis 模板未找到异常 =====》");
                e.printStackTrace();
                throw new RuntimeException("模板未找到异常");
            } catch (MalformedTemplateNameException e) {
                log.info("TemplatesType mybatis 畸形的模板名称 =====》");
                e.printStackTrace();
                throw new RuntimeException("畸形的模板名称");
            } catch (ParseException e) {
                log.info("TemplatesType mybatis 模板解析失败 =====》");
                e.printStackTrace();
                throw new RuntimeException("模板解析失败");
            } catch (IOException e) {
                log.info("TemplatesType mybatis IO异常 =====》");
                e.printStackTrace();
                throw new RuntimeException("IO异常");
            }
            return tc;
        }
    },
    MYBATISPLUS {
        @Override
        public TemplateConfig template() {

            Configuration cfg = new Configuration(Configuration.VERSION_2_3_30);
            cfg.setTemplateLoader(new ClassTemplateLoader(TemplatesType.class,
                    "/templates/mybatisPlusFtl"));
            TemplateConfig tc = new TemplateConfig();
            // 获取模板
            try {
                tc.setEntityTemplate(cfg.getTemplate("EntityMybatisPlus.java.ftl"));
                tc.setMapperJavaTemplate(cfg.getTemplate("MapperMybatisPlus.java.ftl"));
                tc.setMapperXmlTemplate(cfg.getTemplate("MapperMybatisPlus.xml.ftl"));
                tc.setServiceTemplate(cfg.getTemplate("ServiceMybatisPlus.java.ftl"));
                tc.setServiceImplTemplate(cfg.getTemplate("ServiceImplMybatisPlus.java.ftl"));
                tc.setControllerTemplate(cfg.getTemplate("ControllerMybatisPlus.java.ftl"));
                cfg.setDefaultEncoding("UTF-8");
                tc.setCfg(cfg);
            } catch (TemplateNotFoundException e) {
                log.info("TemplatesType mybatis 模板未找到异常 =====》");
                e.printStackTrace();
                throw new RuntimeException("模板未找到异常");
            } catch (MalformedTemplateNameException e) {
                log.info("TemplatesType mybatis 畸形的模板名称 =====》");
                e.printStackTrace();
                throw new RuntimeException("畸形的模板名称");
            } catch (ParseException e) {
                log.info("TemplatesType mybatis 模板解析失败 =====》");
                e.printStackTrace();
                throw new RuntimeException("模板解析失败");
            } catch (IOException e) {
                log.info("TemplatesType mybatis IO异常 =====》");
                e.printStackTrace();
                throw new RuntimeException("IO异常");
            }
            return tc;
        }
    };

}
