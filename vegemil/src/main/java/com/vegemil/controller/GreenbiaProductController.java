package com.vegemil.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vegemil.domain.greenbia.GreenbiaProductCategoryDTO;
import com.vegemil.domain.greenbia.GreenbiaProductCountDTO;
import com.vegemil.domain.greenbia.GreenbiaProductDTO;
import com.vegemil.domain.greenbia.GreenbiaProductKeywordDTO;
import com.vegemil.domain.greenbia.GreenbiaProductSearchDTO;
import com.vegemil.service.GreenbiaProductService;

@Controller
public class GreenbiaProductController {
	
	@Autowired
	private GreenbiaProductService greenbiaProductService;
		
	@GetMapping("/greenbia/guide")
	public String guide() {
		return "greenbia/guide";
	}
		
	@GetMapping("/greenbia/newProduct")
	public String newGreenbiaProduct(Model model, @RequestParam(required = false) String category) {
		
		model.addAttribute("category", category);
		
		List<GreenbiaProductDTO> greenbiaProductList = greenbiaProductService.getProductList();	
		model.addAttribute("productList", greenbiaProductList);	
		
		//카테고리별 제품 수
		List<GreenbiaProductCountDTO> countInfoList= greenbiaProductService.getProductCount();		
		model.addAttribute("countInfo", countInfoList);

		//카테고리별 제품 - 일반영양식
		List<GreenbiaProductCategoryDTO> commonProductList= greenbiaProductService.getCommonProduct();
		model.addAttribute("commonProduct", commonProductList);
		
		//카테고리별 제품 - 전문영양식
		List<GreenbiaProductCategoryDTO> proProductList= greenbiaProductService.getProProduct();
		model.addAttribute("proProduct", proProductList);
		
		//카테고리별 제품 - 연하식
		List<GreenbiaProductCategoryDTO> YeonhaProductList= greenbiaProductService.getYeonhaProduct();
		model.addAttribute("yeonhaProduct", YeonhaProductList);
		
		//카테고리별 제품 - 건강식품
		List<GreenbiaProductCategoryDTO> HealthProductList= greenbiaProductService.getHealthProduct();
		model.addAttribute("healthProduct", HealthProductList);
		
		return "greenbia/newProduct";
	}
	
	
	
	@GetMapping("/greenbia/search")
	public String openGreenbiaSearch(Model model, String searchKeyword) {
		
		System.out.println("=====================ProductControllerGreenbia======================");
				
		List<GreenbiaProductSearchDTO> searchList = greenbiaProductService.searchProduct(searchKeyword);
		model.addAttribute("searchList", searchList);
				
		List<GreenbiaProductKeywordDTO> keywordProductList= greenbiaProductService.getKeywordProduct();
		model.addAttribute("keywordProductList", keywordProductList);
		
		return "greenbia/search";
		
	}
	
	
	

}
