package com.vegemil.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vegemil.domain.ProductDTO;
import com.vegemil.mapper.ProductMapper;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductMapper ProductMapper;

	@Override
	public ProductDTO getProductDetail(ProductDTO params) {
		return ProductMapper.selectProductDetail(params);
	}

	@Override
	public List<ProductDTO> getProductList(String searchKeyword) {
		List<ProductDTO> ProductList = Collections.emptyList();

		int ProductTotalCount = ProductMapper.selectProductTotalCount();

		if (ProductTotalCount > 0) {
			ProductList = ProductMapper.selectProductList(searchKeyword);
		}

		return ProductList;
	}

}
