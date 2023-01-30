package com.vegemil.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vegemil.domain.AdminEventDTO;
import com.vegemil.domain.DataTableDTO;
import com.vegemil.service.AdminEventService;

@Controller
@RequestMapping("/admin/manage")
public class AdminEventController {
	
	@Autowired
	private AdminEventService adminEventService;
	
	
	//이벤트 등록 페이지
	@GetMapping("/event/eventAdd")
	public String moveEventAddPage() throws Exception {
		return "admin/event/eventAdd";
	}
	
	//이벤트 등록 - 베지밀
	@PostMapping("/event/eventAdd")
	@ResponseBody
	public Map<String, Object> saveMediaNews(@ModelAttribute("params") final AdminEventDTO params, Model model,
			HttpServletResponse response, HttpServletRequest request) throws Exception {

		Map<String, Object> rtnMap = new HashMap<String, Object>();
		
		try {			
			boolean isRegistered = adminEventService.registerEvent(params);			
			rtnMap.put("result", isRegistered);
		} catch (DataAccessException e) {
		} catch (Exception e) {
		}
		return rtnMap;
	}
	
	
	//이벤트 조회페이지 - 베지밀
	@GetMapping("/event/eventVegemil")
	public String moveEventListPage() {
		return "admin/event/eventVegemil";
	}
	
	//이벤트 조회 - 베지밀
	@GetMapping("/event/eventVegemilList")
	public @ResponseBody DataTableDTO getEventVegemilList(
						@ModelAttribute("params") AdminEventDTO params, Model model, 
						@RequestParam Map<String, Object> commandMap) {
		
		DataTableDTO dataTableDto = adminEventService.getEventVegemilList(commandMap);
		
		return dataTableDto;
	}
	
	
	

}
	
	

