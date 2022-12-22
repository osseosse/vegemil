package com.vegemil.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vegemil.domain.GreenbiaProductDTO;
import com.vegemil.mapper.GreenbiaProductMapper;

@Service
public class GreenbiaProductServiceImpl implements GreenbiaProductService {

	@Autowired
	private GreenbiaProductMapper greenbiaProductMapper;

	@Override
	public GreenbiaProductDTO getProductDetail(Long gIdx) {
		return greenbiaProductMapper.selectProductDetail(gIdx);
	}
	
	@Override
	public List<GreenbiaProductDTO> getRecProduct() {
		
		List<GreenbiaProductDTO> productList = Collections.emptyList();

		int productTotalCount = greenbiaProductMapper.selectProductTotalCount();

		if (productTotalCount > 0) {
			productList = greenbiaProductMapper.selectRecProduct();
		}

		return productList;
	}

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
	public List<GreenbiaProductDTO> getProductCount() {

		List<GreenbiaProductDTO> ProductCountList = greenbiaProductMapper.selectProductCount();

		return ProductCountList;
	}

	@Override
	public List<GreenbiaProductDTO> getCommonProduct() {

		List<GreenbiaProductDTO> CommonProductList = greenbiaProductMapper.selectCommonProduct();

		return CommonProductList;
	}

	@Override
	public List<GreenbiaProductDTO> getProProduct() {
		List<GreenbiaProductDTO> ProProductList = greenbiaProductMapper.selectProProduct();
		return ProProductList;
	}

	@Override
	public List<GreenbiaProductDTO> getYeonhaProduct() {
		List<GreenbiaProductDTO> YeonhaProductList = greenbiaProductMapper.selectYeonhaProduct();
		return YeonhaProductList;
	}

	@Override
	public List<GreenbiaProductDTO> getHealthProduct() {
		List<GreenbiaProductDTO> HealthProductList = greenbiaProductMapper.selectHealthProduct();
		return HealthProductList;
	}

	@Override
	public List<GreenbiaProductDTO> searchProduct(String searchKeyword) {
		System.out.println("=====================GreenbiaProductServiceImpl======================");

		List<GreenbiaProductDTO> SearchProductList = greenbiaProductMapper.searchProduct(searchKeyword);
		return SearchProductList;
	}

	
	

	
	@Override
	public List<GreenbiaProductDTO> getKeywordProduct() {

		List<GreenbiaProductDTO> KeywordProductList = greenbiaProductMapper.selectKeywordProduct();

		return KeywordProductList;
	}

}
