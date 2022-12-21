package com.vegemil.service;

import java.util.List;

import com.vegemil.domain.ProductDTO;

public interface ProductService {

	public ProductDTO getProductDetail(Long pIdx);

	public List<ProductDTO> getProductList(String searchKeyword);
	
	public List<ProductDTO> getBrandStroyList();
	
	public List<ProductDTO> getRecProduct(String categoryCode);

}
