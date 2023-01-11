package com.vegemil.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vegemil.domain.AdminMediaNewsDTO;
import com.vegemil.domain.AdminSaboDTO;
import com.vegemil.domain.DataTableDTO;
import com.vegemil.service.AdminPublicCenterService;
import com.vegemil.util.UiUtils;

@Controller
@RequestMapping("/admin/manage")
public class AdminpublicCenterController extends UiUtils{
	
	@Autowired
	private AdminPublicCenterService adminPublicCenterService;
	
	//보도자료 등록 페이지
	@GetMapping("/publicCenter/mediaNewsAdd")
	public String moveMediaNewsAddPage() {
		return "admin/publicCenter/mediaNewsAdd";
	}
	
	/*
	 * @PostMapping("/publicCenter/mediaNewsAdd")
	 * 
	 * @R public String moveMediaNewsAddPage() { return
	 * "admin/publicCenter/mediaNewsAdd"; }
	 */
	
	
	//보도자료 조회
	@GetMapping("/publicCenter/mediaNews")
	public String moveMediaNewsPage() {
		return "admin/publicCenter/mediaNews";
	}
	
	@RequestMapping(value = "/publicCenter/mediaNewsList")
	public @ResponseBody DataTableDTO getSaboSubscribeList(
			 			@ModelAttribute("params") AdminMediaNewsDTO params, Model model, 
			 			@RequestParam Map<String, Object> commandMap) {

		DataTableDTO dataTableDto = adminPublicCenterService.getMediaNewsList(commandMap);
		return dataTableDto;
	 }
	
	@RequestMapping(value = "/publicCenter/deleteMediaNews", method = {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody boolean deleteMediaNews(@ModelAttribute("params") AdminMediaNewsDTO params, Model model, 
															HttpServletResponse response, HttpServletRequest request) {
		
		try {
			String checkList[] = request.getParameterValues("checkList");
			ArrayList<String> list = new ArrayList<>();
			for(int i=0; i<checkList.length; i++) {
				list.add(checkList[i]);
			}
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("list", list);
		
			boolean isDeleted = adminPublicCenterService.deleteMediaNews(paramMap);
			
			System.out.println("isDeleted: "+isDeleted);
			if(!isDeleted) {
				return false;
			}	
		
		}catch (DataAccessException e) {
	   		return false;
		} catch (Exception e) {
			return false;
		}	
	
		return true;
	
	}
}
