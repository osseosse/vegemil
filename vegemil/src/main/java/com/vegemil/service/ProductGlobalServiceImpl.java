package com.vegemil.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vegemil.domain.ProductDTO;
import com.vegemil.mapper.ProductGlobalMapper;

@Service
public class ProductGlobalServiceImpl implements ProductGlobalService {

	@Autowired
	private ProductGlobalMapper productMapper;

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
	public List<ProductDTO> getRecProduct(ProductDTO productDto) {
		
		List<ProductDTO> productList = new ArrayList<>();
		
		int productTotalCount = productMapper.selectProductTotalCount();
		
		if (productTotalCount > 0) {
			if(productDto.getCategoryCode().equals("S") || productDto.getCategoryCode().equals("W") || productDto.getCategoryCode().equals("P")) {
				productDto.setCategoryCode("V");
			}
			productList = productMapper.selectRecProduct(productDto);
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
