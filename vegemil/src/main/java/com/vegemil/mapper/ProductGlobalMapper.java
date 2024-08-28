package com.vegemil.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.vegemil.domain.global.ProductEnDTO;
import com.vegemil.domain.global.ProductVnDTO;



@Mapper
public interface ProductGlobalMapper {

	// 영문
	public List<ProductEnDTO> selectProductList(String searchKeyword);
	public List<ProductEnDTO> selectRecProduct(ProductEnDTO ProductEnDTO);
	public ProductEnDTO selectProductDetail(Long pIdx);
	
	// 베트남
	public List<ProductVnDTO> selectProductVnList(String searchKeyword);	
	public List<ProductVnDTO> selectRecVnProduct(ProductVnDTO ProductVnDTO);
	public ProductVnDTO selectVnProductDetail(Long pIdx);
	
	public int selectProductTotalCount();

	public int selectBrandStroyCount();
	
	public int updateAddCount(Long pIdx);
	
}
