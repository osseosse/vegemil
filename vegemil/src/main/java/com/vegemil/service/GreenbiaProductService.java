package com.vegemil.service;

import java.util.List;

import com.vegemil.domain.GreenbiaProductDTO;

public interface GreenbiaProductService {

	public GreenbiaProductDTO getProductDetail(Long gIdx);
	
	public List<GreenbiaProductDTO> getRecProduct();

	public List<GreenbiaProductDTO> getProductList();
	
	public List<GreenbiaProductDTO> getProductCount();
	
	public List<GreenbiaProductDTO> getCommonProduct();
	
	public List<GreenbiaProductDTO> getProProduct();
	
	public List<GreenbiaProductDTO> getYeonhaProduct();
	
	public List<GreenbiaProductDTO> getHealthProduct();
	
	public List<GreenbiaProductDTO> searchProduct(String searchKeyword);
	
	public List<GreenbiaProductDTO> getKeywordProduct();

	
	


}
