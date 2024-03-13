package com.vegemil.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.vegemil.domain.GreenbiaProductDTO;

@Mapper
public interface GreenbiaProductMapper {

	public GreenbiaProductDTO selectProductDetail(Long gIdx);
	
	public List<GreenbiaProductDTO> selectRecProduct();

	public List<GreenbiaProductDTO> selectProductList();

	public int selectProductTotalCount();
	
	public List<GreenbiaProductDTO> selectAllProduct();
	
	public List<GreenbiaProductDTO> selectProductCount();

	public List<GreenbiaProductDTO> selectCommonProduct();
	
	public List<GreenbiaProductDTO> selectProProduct();
	
	public List<GreenbiaProductDTO> selectYeonhaProduct();
	
	public List<GreenbiaProductDTO> selectHealthProduct();

	public List<GreenbiaProductDTO> searchProduct(String searchKeyword);
	
	public List<GreenbiaProductDTO> selectProductListTube(String searchKeyword);

	public List<GreenbiaProductDTO> selectKeywordProduct();

	

}
