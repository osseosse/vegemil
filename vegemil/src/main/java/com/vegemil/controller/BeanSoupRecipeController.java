package com.vegemil.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class BeanSoupRecipeController {

	@GetMapping("/Main/beanSoupRecipe/{beansoupRecipe}")
	public String getBeanSoupRecipePages(@PathVariable("beansoupRecipe") String beansoupRecipe) {
		if(beansoupRecipe.contains(".")) {
			beansoupRecipe = beansoupRecipe.substring(0, beansoupRecipe.lastIndexOf("."));
		}
		return "beanSoupRecipe/" + beansoupRecipe;
	}
	
	@GetMapping("/beanSoupRecipe/{viewName}")
	public String moveBeanSoupRecipe(@PathVariable(value = "viewName", required = false) String viewName) {
		
		return "beanSoupRecipe/" + viewName;
	}
	
}
