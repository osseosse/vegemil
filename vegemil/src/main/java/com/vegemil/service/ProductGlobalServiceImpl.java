package com.vegemil.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vegemil.domain.global.ProductEnDTO;
import com.vegemil.mapper.ProductGlobalMapper;

@Service
public class ProductGlobalServiceImpl implements ProductGlobalService {

	@Autowired
	private ProductGlobalMapper productMapper;

	@Override
	public ProductEnDTO getProductDetail(Long pIdx) {
		return productMapper.selectProductDetail(pIdx);
	}

	@Override
	public List<ProductEnDTO> getProductList(String searchKeyword) {
		
		List<ProductEnDTO> productList = Collections.emptyList();

		int productTotalCount = productMapper.selectProductTotalCount();

		if (productTotalCount > 0) {
			productList = productMapper.selectProductList(searchKeyword);
		}

		return productList;
	}
	

	
	@Override
	public List<ProductEnDTO> getRecProduct(ProductEnDTO ProductEnDTO) {
		
		List<ProductEnDTO> productList = new ArrayList<>();
		
		int productTotalCount = productMapper.selectProductTotalCount();
		
		if (productTotalCount > 0) {
			if(ProductEnDTO.getCategoryCode().equals("S") || ProductEnDTO.getCategoryCode().equals("W") || ProductEnDTO.getCategoryCode().equals("P")) {
				ProductEnDTO.setCategoryCode("V");
			}
			productList = productMapper.selectRecProduct(ProductEnDTO);
		}
		return productList;
	}
	
	@Override
	public boolean updateAddCount(Long pIdx) {
		
		int queryResult = 0;
		queryResult = productMapper.updateAddCount(pIdx);

		return (queryResult == 1) ? true : false;
	}


}
