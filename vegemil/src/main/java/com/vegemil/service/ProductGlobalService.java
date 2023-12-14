package com.vegemil.service;

import java.util.List;

import com.vegemil.domain.global.ProductEnDTO;


public interface ProductGlobalService {

	public ProductEnDTO getProductDetail(Long pIdx);

	public List<ProductEnDTO> getProductList(String searchKeyword);
	
	public List<ProductEnDTO> getRecProduct(ProductEnDTO ProductEnDTO);

	public boolean updateAddCount(Long pIdx);
	
}
