package com.vegemil.service;

import java.util.List;

import com.vegemil.domain.ProductDTO;

public interface ProductGlobalService {

	public ProductDTO getProductDetail(Long pIdx);

	public List<ProductDTO> getProductList(String searchKeyword);
	
	public List<ProductDTO> getBrandStroyList(String categoryCode);
	
	public List<ProductDTO> getRecProduct(ProductDTO productDto);

	public boolean updateAddCount(Long pIdx);
	
}