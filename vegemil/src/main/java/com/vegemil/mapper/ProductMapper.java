package com.vegemil.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.vegemil.domain.ProductDTO;

@Mapper
public interface ProductMapper {

	public ProductDTO selectProductDetail(Long pIdx);

	public List<ProductDTO> selectProductList(String searchKeyword);
	
	public List<ProductDTO> selectRecProduct(String categoryCode);
	
	public List<ProductDTO> selectBrandStroyList(String categoryCode);

	public int selectProductTotalCount();

	public int selectBrandStroyCount();
	
}
