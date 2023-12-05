package com.vegemil.configuration;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties.LocaleResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import com.vegemil.interceptor.LoggerInterceptor;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class MvcConfiguration implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoggerInterceptor())
		.excludePathPatterns("/common/**","/css/**", "/fonts/**", "/img/**", "/scripts/**", "/js/**","/*.ico","/beansoup/css/**",
								"/beansoup/js/**","/webzine/js/**", "/webzine/css/**", "/app-assets/**",
								"/beansoupRecipe/css/**", "beansoupRecipe/js/**", "beansoupRecipe/fonts/**");
	}
	
	@Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }
	


}
