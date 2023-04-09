package netplus.component.freemarker.web;

import cn.org.rapid_framework.freemarker.directive.BlockDirective;
import cn.org.rapid_framework.freemarker.directive.ExtendsDirective;
import cn.org.rapid_framework.freemarker.directive.OverrideDirective;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.annotation.PostConstruct;
import java.util.Properties;


@Configuration
public class FreemarkerConfig {


    @Bean()
    public FreeMarkerConfigurer getFreeMarkerConfigurer() {

        FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
        freeMarkerConfigurer.setPreferFileSystemAccess(false);
        freeMarkerConfigurer.setTemplateLoaderPaths("classpath:/webapp/WEB-INF/ftl/", "classpath:/public/");

        //
        Properties properties = new Properties();
        properties.put("date_format", "yyyy-MM-dd");
        properties.put("time_format", "HH:mm:ss");
        properties.put("datetime_format", "yyyy-MM-dd HH:mm:ss");
        properties.put("number_format", "0.######");
        properties.put("defaultEncoding", "UTF-8");
        properties.put("url_escaping_charset", "UTF-8");
        properties.put("locale", "zh_CN");
        properties.put("template_update_delay", "0");

        freeMarkerConfigurer.setFreemarkerSettings(properties);

        return freeMarkerConfigurer;
    }


    @Autowired
    freemarker.template.Configuration _configuration;

    @PostConstruct
    public void setSharedVariable(){
        _configuration.setSharedVariable("block", new BlockDirective());
        _configuration.setSharedVariable("override", new OverrideDirective());
        _configuration.setSharedVariable("extends", new ExtendsDirective());
    }
}
