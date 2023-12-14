package com.vegemil.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.vegemil.domain.global.ProductEnDTO;



@Mapper
public interface ProductGlobalMapper {

	public ProductEnDTO selectProductDetail(Long pIdx);

	public List<ProductEnDTO> selectProductList(String searchKeyword);
	
	public List<ProductEnDTO> selectRecProduct(ProductEnDTO ProductEnDTO);	

	public int selectProductTotalCount();

	public int selectBrandStroyCount();
	
	public int updateAddCount(Long pIdx);
	
}
