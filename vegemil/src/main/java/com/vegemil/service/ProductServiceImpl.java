package com.vegemil.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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
	public List<ProductDTO> getRecProduct(ProductDTO productDto) {
		
		List<ProductDTO> productList = new ArrayList<>();
		Set<Integer> randomIdxSet = new HashSet<>();
		
		int productTotalCount = productMapper.selectProductTotalCount();
		
		if (productTotalCount > 0) {
			if(productDto.getCategoryCode().equals("S") || productDto.getCategoryCode().equals("W") || productDto.getCategoryCode().equals("P")) {
				productDto.setCategoryCode("V");
			}
			productList = productMapper.selectRecProduct(productDto);
			
			int listSize = productList.size();
			
			if(listSize < 5) {
				return productList;
			}
			
			while(true) {
				randomIdxSet.add((int)(Math.random()*listSize-1));
				if(randomIdxSet.size()==4) {
					break;
				}
			}
			
			Iterator<Integer> iter = randomIdxSet.iterator();
			
			while(iter.hasNext()) {
				productList.add(productList.get((int) iter.next())); 
			}
			
			productList = productList.subList(listSize, productList.size());
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
