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
	private ProductMapper productMapper;

	@Override
	public ProductDTO getProductDetail(Long pIdx) {
		return productMapper.selectProductDetail(pIdx);
	}

	@Override
	public List<ProductDTO> getProductList(String searchKeyword) {
		
		List<ProductDTO> productList = Collections.emptyList();

		int productTotalCount = productMapper.selectProductTotalCount();

		if (productTotalCount > 0) {
			productList = productMapper.selectProductList(searchKeyword);
		}

		return productList;
	}
	
	@Override
	public List<ProductDTO> getBrandStroyList(String categoryCode) {
		
		List<ProductDTO> brandStroyList = Collections.emptyList();

		int brandStroyCount = productMapper.selectBrandStroyCount();

		if (brandStroyCount > 0) {
			brandStroyList = productMapper.selectBrandStroyList(categoryCode);
		}

		return brandStroyList;
	}
	
	@Override
	public List<ProductDTO> getRecProduct(String categoryCode) {
		
		List<ProductDTO> productList = Collections.emptyList();

		int productTotalCount = productMapper.selectProductTotalCount();

		if (productTotalCount > 0) {
			productList = productMapper.selectRecProduct(categoryCode);
		}

		return productList;
	}

}
