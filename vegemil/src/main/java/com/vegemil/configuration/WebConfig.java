package com.vegemil.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;



@Configuration
public class WebConfig {
	
	@Bean 
	public LocaleResolver localeResolver() {
		
		CookieLocaleResolver localeResolver = new CookieLocaleResolver();
		localeResolver.setCookieName("lang");		
		localeResolver.setCookieMaxAge(60*60);
		
		return localeResolver;
	}
}

