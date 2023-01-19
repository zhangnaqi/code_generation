package com.znq.freedom.model;

import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.Data;

@Data
public class TemplateConfig {

    private Configuration cfg;
    private Template entityTemplate;
    private Template mapperJavaTemplate;
    private Template mapperXmlTemplate;
    private Template serviceTemplate;
    private Template serviceImplTemplate;
    private Template controllerTemplate;

}
