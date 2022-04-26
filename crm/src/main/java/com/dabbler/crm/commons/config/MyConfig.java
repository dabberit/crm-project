package com.dabbler.crm.commons.config;

import com.dabbler.crm.settings.web.interceptor.ResourcesInterceptor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableAutoConfiguration(exclude = {MultipartAutoConfiguration.class})
public class MyConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        ResourcesInterceptor resourcesInterceptor = new ResourcesInterceptor();
        String[] path = {"/settings/**","/workbench/**"};
        String[] exPath = {"/settings/qx/user/login.do"};
        registry.addInterceptor(resourcesInterceptor).addPathPatterns(path).excludePathPatterns(exPath);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/statics/**").addResourceLocations("classpath:/statics/");
    }

    /**
     * 配置文件上传解析器
     * @return
     */
    @Bean(name = "multipartResolver")
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setMaxUploadSize(1024 * 1024 * 80);
        resolver.setResolveLazily(true);
        resolver.setDefaultEncoding("UTF-8");

        return resolver;
    }
}
