package com.vegemil.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.vegemil.domain.AdminRecipeDTO;

@Mapper
public interface AdminRecipeMapper {
	public List<AdminRecipeDTO> selectVegemilRecipeList(AdminRecipeDTO params);
	
	public int insertVegemilRecipe(AdminRecipeDTO params);
	public int deleteRecipeData(String cIdx);
	public int updateRecipeData(AdminRecipeDTO params);
	public int updatecVactiveStatus(AdminRecipeDTO params);
}
