package com.vegemil.mapper.greenbia;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.vegemil.domain.ProductDTO;
import com.vegemil.domain.greenbia.GreenbiaProductCategoryDTO;
import com.vegemil.domain.greenbia.GreenbiaProductCountDTO;
import com.vegemil.domain.greenbia.GreenbiaProductDTO;
import com.vegemil.domain.greenbia.GreenbiaProductKeywordDTO;
import com.vegemil.domain.greenbia.GreenbiaProductSearchDTO;

@Mapper
public interface GreenbiaProductMapper {

	public ProductDTO selectProductDetail(Long pIdx);

	public List<GreenbiaProductDTO> selectProductList();

	public int selectProductTotalCount();
	
	public List<GreenbiaProductCountDTO> selectProductCount();

	public List<GreenbiaProductCategoryDTO> selectCommonProduct();
	
	public List<GreenbiaProductCategoryDTO> selectProProduct();
	
	public List<GreenbiaProductCategoryDTO> selectYeonhaProduct();
	
	public List<GreenbiaProductCategoryDTO> selectHealthProduct();

	public List<GreenbiaProductSearchDTO> searchProduct(String searchKeyword);

	public List<GreenbiaProductKeywordDTO> selectKeywordProduct();

	

}
