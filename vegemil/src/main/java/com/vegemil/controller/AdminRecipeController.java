package com.vegemil.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.vegemil.adapter.GsonLocalDateTimeAdapter;
import com.vegemil.domain.AdminBeanSoupVideoDTO;
import com.vegemil.domain.AdminRecipeDTO;
import com.vegemil.service.AdminRecipeService;

import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/admin/manage")
public class AdminRecipeController {
	
	@Autowired
	private AdminRecipeService adminRecipeService;
	
	@GetMapping("/recipe/vegemil")
	public String getAdminRecipeMain(Model model) {
		return "admin/recipe/vegemil";
	}
	
	@RequestMapping(value = "/recipe/recipeList")
    public @ResponseBody JsonObject getBeanSoupVideo(@ModelAttribute("params") final AdminRecipeDTO params, HttpServletRequest req, 
    		Map<String, Object> commandMap)throws Exception{
		List<AdminRecipeDTO> vegemilRecipeList = adminRecipeService.getVegemilRecipeLsit(params);
		JsonObject jsonObj = new JsonObject();
		if (CollectionUtils.isEmpty(vegemilRecipeList) == false) {
			Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new GsonLocalDateTimeAdapter()).create();
			JsonArray jsonArr = gson.toJsonTree(vegemilRecipeList).getAsJsonArray();
			jsonObj.add("data", jsonArr);
		}
		
		return jsonObj;
    }
	
	
	@RequestMapping(value="/recipe/postRecipe")
    public @ResponseBody Map<String, Object> saveVegemilRecipe(@ModelAttribute("params") final AdminRecipeDTO params)throws Exception{
		Map<String, Object> rtnMsg = new HashMap<String, Object>();
		
		try {
			boolean isResulted = adminRecipeService.postVegemilRecipe(params);
			rtnMsg.put("result", isResulted);
		}catch (DataAccessException e) {
    		e.printStackTrace();
    		throw new Exception("저장에 실패하였습니다");
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("저장에 실패하였습니다");
		}
		
		return rtnMsg;
    }
	
	@RequestMapping(value="/recipe/changeVactiveStatus")
    public @ResponseBody Map<String, Object> changeRecipeActiveStatus(@ModelAttribute("params") final AdminRecipeDTO params)throws Exception{
		Map<String, Object> rtnMsg = new HashMap<String, Object>();
		
		try {
			boolean isResulted = adminRecipeService.changeRecipeStatus(params);
			rtnMsg.put("result", isResulted);
		}catch (DataAccessException e) {
    		e.printStackTrace();
    		throw new Exception("저장에 실패하였습니다");
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("저장에 실패하였습니다");
		}
		
		return rtnMsg;
    }
	
}
