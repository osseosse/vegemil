package com.vegemil.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

/*로컬 이미지 조회 설정을 위한 config파일*/
@Configuration
public class WebMvcConfig implements WebMvcConfigurer { //web 설정파일

//    @Value("${file.path}")
//    private String uploadFolder;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        WebMvcConfigurer.super.addResourceHandlers(registry);

        registry
                .addResourceHandler("/image/**") 
                .addResourceLocations("file:///D:/upload/admin/vegemilbaby/")
                .resourceChain(true)
                .addResolver(new PathResourceResolver());
    }
}

