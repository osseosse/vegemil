package com.vegemil.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vegemil.constant.Method;
import com.vegemil.domain.ProductDTO;
import com.vegemil.domain.global.ProductEnDTO;
import com.vegemil.service.ProductGlobalService;
import com.vegemil.service.ProductService;
import com.vegemil.util.UiUtils;

@Controller
public class ProductController extends UiUtils {

	@Autowired
	private ProductService productService;
	@Autowired
	private ProductGlobalService productGlobalService;
	
	@GetMapping(value = "/product/list")
	public String openProductList( Model model, @RequestParam(value = "searchKeyword", required = false) String searchKeyword,
																@CookieValue(value = "lang", required = false) String localCookie) {
		

		if("en".equals(localCookie)) {
			List<ProductEnDTO> productGlobalList = productGlobalService.getProductList(searchKeyword);
			model.addAttribute("productList", productGlobalList);
			model.addAttribute("productCount", productGlobalList.size());
			if(searchKeyword != null) {
				model.addAttribute("searchKeyword", searchKeyword);
				return "en/product/list_searched";
			}else {
				return "en/product/list";
			}			
		}
	    
		List<ProductDTO> productList = productService.getProductList(searchKeyword);
		model.addAttribute("productList", productList);
		model.addAttribute("productCount", productList.size());
		
		if(searchKeyword != null) {
			model.addAttribute("searchKeyword", searchKeyword);
			return "product/list_searched";
		}
		return "product/list";
	}
	
	@GetMapping(value = "/brandStory/{viewName}")
	public String openBrandStroyList(Model model, @PathVariable(value = "viewName", required = false) String viewName) {
	    
		if(!viewName.equals("") && (viewName.equals("vegemil") || viewName.equals("greenbia"))) {
			List<ProductDTO> brandStroyList = productService.getBrandStroyList(viewName.substring(0,1).toUpperCase());
			model.addAttribute("brandStroyList", brandStroyList);
			model.addAttribute("brandStroyCount", brandStroyList.size());
		}

		return "brandStory/"+viewName;
	}
	
	@GetMapping(value = "/product/cook")
	public String openProductCook() {
	    
		return "product/cook";
	}
	
	
	@GetMapping(value = "/product/detail/{pIdx}")
	public String openProductDetail(@PathVariable(value = "pIdx", required = false) Long pIdx, Model model, 
													@CookieValue(value = "lang", required = false) String localCookie) {
		
		if (pIdx == null) {
			return showMessageWithRedirect("올바르지 않은 접근입니다.", "/product/list", Method.GET, null, model);
		}
		
		if("en".equals(localCookie)) {
			ProductEnDTO product = productGlobalService.getProductDetail(pIdx);
			
			if (product == null) {
				return showMessageWithRedirect("It's a product that is only distributed in korea", "/product/list", Method.GET, null, model);
			}
			
			List<ProductEnDTO> recProduct = productGlobalService.getRecProduct(product);
			model.addAttribute("product", product);
			model.addAttribute("recProduct", recProduct);
			return "en/product/detail";
		}
	    
		
		

		ProductDTO product = productService.getProductDetail(pIdx);
		if (product == null) {
			return showMessageWithRedirect("없는 게시글이거나 이미 삭제된 게시글입니다.", "product/list", Method.GET, null, model);
		}
		
		List<ProductDTO> recProduct = productService.getRecProduct(product);
		model.addAttribute("product", product);
		model.addAttribute("recProduct", recProduct);

		return "product/detail";
	}

	@GetMapping("/product/addCount")
	public @ResponseBody Map<String, Object> updateProductCount(@RequestParam("pIdx") Long pIdx) {
		
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		
		boolean isUpdate = productService.updateAddCount(pIdx);
		rtnMap.put("result", isUpdate);
		
		return rtnMap;
	}
	
}