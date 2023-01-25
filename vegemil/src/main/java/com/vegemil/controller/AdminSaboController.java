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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vegemil.domain.AdminSaboDTO;
import com.vegemil.domain.DataTableDTO;
import com.vegemil.service.AdminSaboService;
import com.vegemil.util.UiUtils;

@Controller
@RequestMapping("/admin/manage/sabo")
public class AdminSaboController extends UiUtils{
	
	@Autowired
	private AdminSaboService adminSaboService;
	
	@RequestMapping(value = "/{viewName}")
    public String adminMoveCustomer(@PathVariable(value = "viewName", required = false) String viewName)throws Exception{
		return "admin/sabo/"+viewName;
    }
	
	@GetMapping("/subscriber")
	public String moveSubscriberList() {
		return "admin/sabo/sabo";
	}
	
	@RequestMapping(value = "/subscribeList")
	public @ResponseBody DataTableDTO getSaboSubscribeList(
			 			@ModelAttribute("params") AdminSaboDTO params, Model model, 
			 			@RequestParam Map<String, Object> commandMap) {

		DataTableDTO dataTableDto = adminSaboService.getSaboSubscribeList(commandMap);
		return dataTableDto;
	 }
	
	
	@RequestMapping(value = "/deleteSaboSubscribe", method = {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody boolean deleteSaboSubscribeList(@ModelAttribute("params") AdminSaboDTO params, Model model, 
															HttpServletResponse response, HttpServletRequest request) {
		
		try {
			String checkList[] = request.getParameterValues("checkList");
			ArrayList<String> list = new ArrayList<>();
			for(int i=0; i<checkList.length; i++) {
				list.add(checkList[i]);
			}
			System.out.println(checkList);
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("list", list);
			
			boolean isDeleted = adminSaboService.deleteSaboSubscribe(paramMap);
			
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
	
	/**
	 * 웹진이벤트관리
	 * @param params
	 * @param model
	 * @param commandMap
	 * @return
	 */
	@RequestMapping(value = "/eventList")
	public @ResponseBody DataTableDTO getWebzineEventList(
			 			@ModelAttribute("params") AdminSaboDTO params, Model model, 
			 			@RequestParam Map<String, Object> commandMap) {

		DataTableDTO dataTableDto = adminSaboService.getWebzineEventList(commandMap);
		return dataTableDto;
	 }
}
