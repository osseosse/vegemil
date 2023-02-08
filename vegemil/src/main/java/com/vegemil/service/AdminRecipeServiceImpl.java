package com.vegemil.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vegemil.domain.AdminRecipeDTO;
import com.vegemil.mapper.AdminRecipeMapper;

@Service
@Transactional
public class AdminRecipeServiceImpl implements AdminRecipeService{

	@Autowired
	private AdminRecipeMapper mapper;
	
	@Override
	public List<AdminRecipeDTO> getVegemilRecipeLsit(AdminRecipeDTO params) {
		return mapper.selectVegemilRecipeList(params);
	}

	@Override
	public boolean postVegemilRecipe(AdminRecipeDTO params) {
		
		int queryResult = 0;

		if ("U".equals(params.getAction())) {
			queryResult = mapper.updateRecipeData(params);
		} else if("D".equals(params.getAction())) {
			queryResult = mapper.deleteRecipeData(params.getCIdx());
		}else {
			queryResult = mapper.insertVegemilRecipe(params);
		}

		return (queryResult == 1) ? true : false;
	}

	@Override
	public boolean changeRecipeStatus(AdminRecipeDTO params) {
		
		int queryResult = 0;
		
		if(params.getCIdx() != null) {
			queryResult = mapper.updatecVactiveStatus(params);
		}
		
		return (queryResult == 1) ? true : false;
	}
	

}
