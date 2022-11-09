package com.vegemil.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.vegemil.constant.Method;
import com.vegemil.domain.ProductDTO;
import com.vegemil.service.ProductService;
import com.vegemil.util.UiUtils;

@Controller
public class ProductController extends UiUtils {

	@Autowired
	private ProductService productService;
	
	@GetMapping(value = "/product/productList")
	public String openProductList( Model model, @RequestParam(value = "searchKeyword", required = false) String searchKeyword) {
	        	
		List<ProductDTO> ProductList = productService.getProductList(searchKeyword);
		model.addAttribute("ProductList", ProductList);
		model.addAttribute("ProductCount", ProductList.size());

		return "product/productList";
	}
	
	@GetMapping(value = "/product/productDetail")
	public String openProductDetail(@ModelAttribute("params") ProductDTO params, @RequestParam(value = "pIdx", required = false) Long pIdx, Model model) {
		if (pIdx == null) {
			return showMessageWithRedirect("올바르지 않은 접근입니다.", "product/productList", Method.GET, null, model);
		}

		ProductDTO Product = productService.getProductDetail(params);
		if (Product == null) {
			return showMessageWithRedirect("없는 게시글이거나 이미 삭제된 게시글입니다.", "product/productList", Method.GET, null, model);
		}
		model.addAttribute("Product", Product);

		return "product/productDetail";
	}

}
