package com.vegemil.service;

import java.util.List;
import com.vegemil.domain.AdminRecipeDTO;

public interface AdminRecipeService {
	
	public List<AdminRecipeDTO> getVegemilRecipeLsit(AdminRecipeDTO params);
	public boolean postVegemilRecipe(AdminRecipeDTO params);
	public boolean changeRecipeStatus(AdminRecipeDTO params);
	
	
}
