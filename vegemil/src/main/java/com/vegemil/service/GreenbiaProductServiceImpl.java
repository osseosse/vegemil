package com.vegemil.service;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.util.StringUtil;
import com.vegemil.domain.GreenbiaProductDTO;
import com.vegemil.mapper.GreenbiaProductMapper;

import antlr.StringUtils;

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
		
		if(StringUtil.isEmpty(searchKeyword)) {
			return greenbiaProductMapper.searchProduct(searchKeyword);
		}
		List<GreenbiaProductDTO> SearchProductList = new ArrayList<GreenbiaProductDTO>();
		
		if(searchKeyword.equals("경구") || searchKeyword.equals("경관")) {
			SearchProductList = greenbiaProductMapper.selectProductListTube(searchKeyword);
		}else {
			SearchProductList = greenbiaProductMapper.searchProduct(searchKeyword);
		}
		return SearchProductList;
	}
	
	@Override
	public List<GreenbiaProductDTO> getKeywordProduct() {

		List<GreenbiaProductDTO> KeywordProductList = greenbiaProductMapper.selectKeywordProduct();

		return KeywordProductList;
	}

	@Override
	public List<GreenbiaProductDTO> getAllProductList() {
		
		return greenbiaProductMapper.selectAllProduct();
	}

}
