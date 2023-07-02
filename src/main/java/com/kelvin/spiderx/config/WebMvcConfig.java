package com.kelvin.spiderx.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/***
 * @title WebMvcConfig
 * @desctption <TODO description class purpose>
 * @author LTF
 * @create 2023/7/1 14:07
 **/
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        //加载public文件夹数据
        registry.addResourceHandler("/public/**")
                .addResourceLocations("classpath:/public/");

        //加载static文件夹数据
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }

}