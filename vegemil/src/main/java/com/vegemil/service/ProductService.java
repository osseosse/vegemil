package com.vegemil.service;

import java.util.List;

import com.vegemil.domain.ProductDTO;
import com.vegemil.domain.VegemilMetaData;

public interface ProductService {

	public ProductDTO getProductDetail(Long pIdx);

	public List<ProductDTO> getProductList(String searchKeyword);
	
	public List<ProductDTO> getBrandStroyList(String categoryCode);
	
	public List<ProductDTO> getRecProduct(ProductDTO productDto);

	public boolean updateAddCount(Long pIdx);
	
	public VegemilMetaData getVegemilMetaGuide(String viewPath);
	
}
