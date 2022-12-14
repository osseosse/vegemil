package com.vegemil.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.vegemil.constant.Method;
import com.vegemil.domain.ProductDTO;
import com.vegemil.service.ProductService;
import com.vegemil.util.UiUtils;

@Controller
public class ProductController extends UiUtils {

	@Autowired
	private ProductService productService;
	
	@GetMapping(value = "/product/list")
	public String openProductList( Model model, @RequestParam(value = "searchKeyword", required = false) String searchKeyword) {
	    
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
	public String openProductDetail(@PathVariable(value = "pIdx", required = false) Long pIdx, Model model) {
		if (pIdx == null) {
			return showMessageWithRedirect("???????????? ?????? ???????????????.", "product/productList", Method.GET, null, model);
		}

		ProductDTO product = productService.getProductDetail(pIdx);
		if (product == null) {
			return showMessageWithRedirect("?????? ?????????????????? ?????? ????????? ??????????????????.", "product/productList", Method.GET, null, model);
		}
		
		List<ProductDTO> recProduct = productService.getRecProduct(product.getCategoryCode());
		model.addAttribute("product", product);
		model.addAttribute("recProduct", recProduct);

		return "product/detail";
	}

}