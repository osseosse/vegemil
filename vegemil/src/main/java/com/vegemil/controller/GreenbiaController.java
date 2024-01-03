package com.vegemil.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import com.vegemil.constant.Method;
import com.vegemil.domain.GreenbiaProductDTO;
import com.vegemil.service.GreenbiaProductService;
import com.vegemil.util.UiUtils;

@Controller
public class GreenbiaController extends UiUtils {
	
	@Autowired
	private GreenbiaProductService greenbiaProductService;
	
	@RequestMapping(value = "/greenbia/{viewName}")
    public String moveGreenbiaPage(@PathVariable(value = "viewName", required = false) String viewName)throws Exception{
		
		return "greenbia/"+viewName;
    }
	
	@RequestMapping(value = "/greenbia")
    public String moveGreenbiaIndex(@PathVariable(value = "viewName", required = false) String viewName)throws Exception{
		
		return "greenbia/index";
    }
	
	@RequestMapping(value = "/main/brandgreenbia/index.aspx")
    public RedirectView moveOldGreenbiaPage(@PathVariable(value = "viewName", required = false) String viewName)throws Exception{
		
		return new RedirectView("/greenbia");
    }
	
	@GetMapping("/Main/BrandGreenbia/{greenbiaAspx}")
	public String greenbiaRedirect(@PathVariable("greenbiaAspx") String greenbiaAspx) {
		/*
		if(greenbiaAspx.contains(".")) {
			greenbiaAspx = greenbiaAspx.substring(0, greenbiaAspx.lastIndexOf("."));
		}
		*/
		return "greenbia/index";
	}
	
	@GetMapping("/greenbia/product/list")
	public String moveGreenbiaProductList(Model model, @RequestParam(required = false) String category) {
		
		model.addAttribute("category", category);
		
		List<GreenbiaProductDTO> greenbiaProductList = greenbiaProductService.getProductList();	
		model.addAttribute("productList", greenbiaProductList);	
		
		//카테고리별 제품 수
		List<GreenbiaProductDTO> countInfoList= greenbiaProductService.getProductCount();		
		model.addAttribute("countInfo", countInfoList);

		//카테고리별 제품 - 일반영양식
		List<GreenbiaProductDTO> commonProductList= greenbiaProductService.getCommonProduct();
		model.addAttribute("commonProduct", commonProductList);
		
		//카테고리별 제품 - 전문영양식
		List<GreenbiaProductDTO> proProductList= greenbiaProductService.getProProduct();
		model.addAttribute("proProduct", proProductList);
		
		//카테고리별 제품 - 연하식
		List<GreenbiaProductDTO> YeonhaProductList= greenbiaProductService.getYeonhaProduct();
		model.addAttribute("yeonhaProduct", YeonhaProductList);
		
		//카테고리별 제품 - 건강식품
		List<GreenbiaProductDTO> HealthProductList= greenbiaProductService.getHealthProduct();
		model.addAttribute("healthProduct", HealthProductList);
		
		return "greenbia/productList";
	}
	
	@GetMapping(value = "/greenbia/product/detail/{gIdx}")
	public String moveGreenbiaProductDetail(@PathVariable(value = "gIdx", required = false) Long gIdx, Model model) {
		if (gIdx == null) {
			return showMessageWithRedirect("올바르지 않은 접근입니다.", "greenbia/product/list", Method.GET, null, model);
		}

		GreenbiaProductDTO product = greenbiaProductService.getProductDetail(gIdx);
		if (product == null) {
			return showMessageWithRedirect("없는 게시글이거나 이미 삭제된 게시글입니다.", "greenbia/product/list", Method.GET, null, model);
		}
		
		List<GreenbiaProductDTO> recProduct = greenbiaProductService.getRecProduct();
		model.addAttribute("product", product);
		model.addAttribute("recProduct", recProduct);

		return "greenbia/productDetail";
	}
	
	@GetMapping("/greenbia/search")
	public String openGreenbiaSearch(Model model, String searchKeyword) {
		
				
		List<GreenbiaProductDTO> searchList = greenbiaProductService.searchProduct(searchKeyword);
		model.addAttribute("searchList", searchList);
				
		List<GreenbiaProductDTO> keywordProductList= greenbiaProductService.getKeywordProduct();
		model.addAttribute("keywordProductList", keywordProductList);
		
		return "greenbia/search";
		
	}

}
