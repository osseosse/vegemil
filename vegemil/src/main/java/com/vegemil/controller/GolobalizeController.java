package com.vegemil.controller;

import java.util.Locale;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.LocaleResolver;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;



@Controller
@RequiredArgsConstructor
@Slf4j
public class GolobalizeController {
	
	private final LocaleResolver localeResolver;
	
	@GetMapping("/langSwitch")
	public String bakeLangCookie(HttpServletResponse response, HttpServletRequest request,
										@RequestParam("lang")String lang, @RequestParam("redUrl") String redUrl) {
			
		log.info("redUrl =  {}", redUrl);
		log.info("lang = {}", lang);
		
		Locale locale = Locale.KOREA;
		
		if("en".equals(lang)) {
			locale = Locale.ENGLISH;
		}
		
		localeResolver.setLocale(request, response, locale);
		
		return "redirect:" + redUrl;						
	}
	
	@GetMapping("/en")
	public String GlobalIndex(HttpServletResponse response, HttpServletRequest request) {
		
		localeResolver.setLocale(request, response, Locale.ENGLISH);		
		return "redirect:/";		
	}
	
	@GetMapping("/en/test")
	public String GlobalController(Model model, HttpServletRequest request , @CookieValue(value = "lang", required = false) String cv) {
		
		log.info("lang >>> " + cv);
		
		Cookie[] co = request.getCookies();
		for(Cookie c : co) {
			log.info("cookie >>>> " + c.getName());
		}
		return "/index";
	}
	
	

}
