package com.vegemil.controller;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.LocaleResolver;

import com.vegemil.constant.Method;
import com.vegemil.domain.global.ProductEnDTO;
import com.vegemil.service.ProductGlobalService;
import com.vegemil.util.UiUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class GolobalizeController extends UiUtils {

	private final LocaleResolver localeResolver;

	private final ProductGlobalService productGlobalService;

	@GetMapping("/langSwitch")
	public String bakeLangCookie(HttpServletResponse response, HttpServletRequest request,
			@RequestParam("lang") String lang, @RequestParam("redUrl") String redUrl) {

		log.info("redUrl =  {}", redUrl);
		log.info("lang = {}", lang);

		String[] redUrlArr = { "/company/greetings", "/company/profile", "/company/valueSys", "/product/list",
				"/rnd/introduce", "/rnd/haccp", "/rnd/fssc", "/rnd/halal" };

		Locale locale = Locale.KOREA;

		if ("en".equals(lang)) {
			locale = Locale.ENGLISH;

			if (PatternMatchUtils.simpleMatch(redUrlArr, redUrl) == false
					&& redUrl.contains("/product/detail") == false) {
				redUrl = "/";
			}
		} else {
			// 한국어로 전환
			if (redUrl.contains("/en/")) {
				redUrl = redUrl.substring(4);
				log.info("ko) redUrl = {}", redUrl);
			}

		}

		localeResolver.setLocale(request, response, locale);

		redUrlArr = null;
		return "redirect:" + redUrl;
	}

	@GetMapping("/en")
	public String GlobalIndex(HttpServletResponse response, HttpServletRequest request) {

		localeResolver.setLocale(request, response, Locale.ENGLISH);
		return "en/index";
	}

	@GetMapping(value = "/en/company/{viewName}")
	public String moveEnCompany(@PathVariable(value = "viewName", required = false) String viewName,
			HttpServletResponse response, HttpServletRequest request,
			@CookieValue(value = "lang", required = false) String localCookie) throws Exception {
		if (!("en".equals(localCookie))) {
			localeResolver.setLocale(request, response, Locale.ENGLISH);
		}

		return "en/" + viewName;
	}

	@GetMapping(value = "/en/rnd/{viewName}")
	public String moveEnRnd(@PathVariable(value = "viewName", required = false) String viewName,
			HttpServletResponse response, HttpServletRequest request,
			@CookieValue(value = "lang", required = false) String localCookie) throws Exception {
		if (!("en".equals(localCookie))) {
			localeResolver.setLocale(request, response, Locale.ENGLISH);
		}

		return "en/" + viewName;
	}

	@GetMapping(value = "/en/product/list")
	public String moveEnProductList(HttpServletResponse response, HttpServletRequest request,
			@CookieValue(value = "lang", required = false) String localCookie, Model model,
			@RequestParam(value = "searchKeyword", required = false) String searchKeyword) throws Exception {

		if (!("en".equals(localCookie))) {
			localeResolver.setLocale(request, response, Locale.ENGLISH);
		}

		List<ProductEnDTO> productGlobalList = productGlobalService.getProductList(searchKeyword);
		model.addAttribute("productList", productGlobalList);
		model.addAttribute("productCount", productGlobalList.size());
		if (searchKeyword != null) {
			model.addAttribute("searchKeyword", searchKeyword);
			return "en/product/list_searched";
		} else {
			return "en/product/list";
		}
	}

	@GetMapping(value = "/en/product/detail/{pIdx}")
	public String moveEnProductDetail(@PathVariable(value = "pIdx", required = false) Long pIdx, Model model, 
													HttpServletResponse response, HttpServletRequest request,
												@CookieValue(value = "lang", required = false) String localCookie) {

		if (pIdx == null) {
			return showMessageWithRedirect("It is an incorrect approach.", "/product/list", Method.GET, null, model);
		}
		
		if (!("en".equals(localCookie))) {
			localeResolver.setLocale(request, response, Locale.ENGLISH);
		}

		ProductEnDTO product = productGlobalService.getProductDetail(pIdx);
		
		if (product == null) {
			return showMessageWithRedirect("It's a product that is only distributed in korea", "/product/list", Method.GET, null, model);
		}
		
		List<ProductEnDTO> recProduct = productGlobalService.getRecProduct(product);
		model.addAttribute("product", product);
		model.addAttribute("recProduct", recProduct);
		
		return "en/product/detail";

	}
	
}
