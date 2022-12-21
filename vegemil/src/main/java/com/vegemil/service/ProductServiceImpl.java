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
	public ProductDTO getProductDetail(Long pIdx) {
		return ProductMapper.selectProductDetail(pIdx);
	}

	@Override
	public List<ProductDTO> getProductList(String searchKeyword) {
		
		List<ProductDTO> productList = Collections.emptyList();

		int productTotalCount = ProductMapper.selectProductTotalCount();

		if (productTotalCount > 0) {
			productList = ProductMapper.selectProductList(searchKeyword);
		}

		return productList;
	}
	
	@Override
	public List<ProductDTO> getBrandStroyList() {
		
		List<ProductDTO> brandStroyList = Collections.emptyList();

		int brandStroyCount = ProductMapper.selectBrandStroyCount();

		if (brandStroyCount > 0) {
			brandStroyList = ProductMapper.selectBrandStroyList();
		}

		return brandStroyList;
	}
	
	@Override
	public List<ProductDTO> getRecProduct(String categoryCode) {
		
		List<ProductDTO> productList = Collections.emptyList();

		int productTotalCount = ProductMapper.selectProductTotalCount();

		if (productTotalCount > 0) {
			productList = ProductMapper.selectRecProduct(categoryCode);
		}

		return productList;
	}

}
