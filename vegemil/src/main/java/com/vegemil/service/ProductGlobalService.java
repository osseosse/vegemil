package com.vegemil.service;

import java.util.List;

import com.vegemil.domain.global.ProductEnDTO;
import com.vegemil.domain.global.ProductVnDTO;


public interface ProductGlobalService {


	public List<ProductEnDTO> getProductList(String searchKeyword);	
	public List<ProductEnDTO> getRecProduct(ProductEnDTO ProductEnDTO);
	public ProductEnDTO getProductDetail(Long pIdx);
	
	public List<ProductVnDTO> getVnProductList(String searchKeyword);	
	public List<ProductVnDTO> getRecVnProduct(ProductVnDTO ProductVnDTO);
	public ProductVnDTO getVnProductDetail(Long pIdx);
	
	
	public boolean updateAddCount(Long pIdx);
	
	 
	
}
