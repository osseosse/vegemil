package com.vegemil.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vegemil.domain.greenbia.GreenbiaProductCategoryDTO;
import com.vegemil.domain.greenbia.GreenbiaProductCountDTO;
import com.vegemil.domain.greenbia.GreenbiaProductDTO;
import com.vegemil.domain.greenbia.GreenbiaProductKeywordDTO;
import com.vegemil.domain.greenbia.GreenbiaProductSearchDTO;
import com.vegemil.mapper.greenbia.GreenbiaProductMapper;

@Service
public class GreenbiaProductServiceImpl implements GreenbiaProductService {

	@Autowired
	private GreenbiaProductMapper greenbiaProductMapper;

//	@Override
//	public ProductDTO getProductDetail(Long pIdx) {
//		return GreenbiaProductMapper.selectProductDetail(pIdx);
//	}

	@Override
	public List<GreenbiaProductDTO> getProductList() {

		List<GreenbiaProductDTO> ProductList = Collections.emptyList();

		int ProductTotalCount = greenbiaProductMapper.selectProductTotalCount();

		if (ProductTotalCount > 0) {
			ProductList = greenbiaProductMapper.selectProductList();
		}

		return ProductList;
	}

	@Override
	public List<GreenbiaProductCountDTO> getProductCount() {

		List<GreenbiaProductCountDTO> ProductCountList = greenbiaProductMapper.selectProductCount();

		return ProductCountList;
	}

	@Override
	public List<GreenbiaProductCategoryDTO> getCommonProduct() {

		List<GreenbiaProductCategoryDTO> CommonProductList = greenbiaProductMapper.selectCommonProduct();

		return CommonProductList;
	}

	@Override
	public List<GreenbiaProductCategoryDTO> getProProduct() {
		List<GreenbiaProductCategoryDTO> ProProductList = greenbiaProductMapper.selectProProduct();
		return ProProductList;
	}

	@Override
	public List<GreenbiaProductCategoryDTO> getYeonhaProduct() {
		List<GreenbiaProductCategoryDTO> YeonhaProductList = greenbiaProductMapper.selectYeonhaProduct();
		return YeonhaProductList;
	}

	@Override
	public List<GreenbiaProductCategoryDTO> getHealthProduct() {
		List<GreenbiaProductCategoryDTO> HealthProductList = greenbiaProductMapper.selectHealthProduct();
		return HealthProductList;
	}

	@Override
	public List<GreenbiaProductSearchDTO> searchProduct(String searchKeyword) {
		System.out.println("=====================GreenbiaProductServiceImpl======================");

		List<GreenbiaProductSearchDTO> SearchProductList = greenbiaProductMapper.searchProduct(searchKeyword);
		return SearchProductList;
	}

	
	

	
	@Override
	public List<GreenbiaProductKeywordDTO> getKeywordProduct() {

		List<GreenbiaProductKeywordDTO> KeywordProductList = greenbiaProductMapper.selectKeywordProduct();

		return KeywordProductList;
	}

}
