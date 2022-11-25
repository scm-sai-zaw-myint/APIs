package com.scm.api;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@ServletComponentScan
@SpringBootApplication(scanBasePackages = "com.scm")
@EntityScan("com.scm.api.persistence.entity")
public class ApiApplication extends WebMvcConfigurationSupport {

    public static void main(String[] args) {
//        SpringApplication.run(ApiApplication.class, args);
        SpringApplicationBuilder builder = new SpringApplicationBuilder(ApiApplication.class);
        builder.headless(false).run(args);
    }
    
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static");
    }
}
