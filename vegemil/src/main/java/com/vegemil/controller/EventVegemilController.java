package com.vegemil.controller;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vegemil.constant.Method;
import com.vegemil.domain.ThermometerLoveDTO;
import com.vegemil.service.AdminEventService;
import com.vegemil.util.UiUtils;


@Controller
public class EventVegemilController extends UiUtils {
	
	@Autowired
	private AdminEventService adminEventService;
	
	@GetMapping("/event/{eventTitle}")
	public String beanSoupBrand(@PathVariable(value = "eventTitle", required = false) String eventTitle ) {
		return "event/"+ eventTitle;
	}
	
	@GetMapping("/event/loveVegemil/{year}")
	public String getThermometerOfLove(@PathVariable("year") int year, Model model) {
		
		
		//if(year == 2024) { year = 2023; }
		 
		
		ThermometerLoveDTO dto = adminEventService.getThermometerLove(year);
		
		if(dto == null) {
			return showMessageWithRedirect("유효하지 않은 접근입니다.", "/event/list", Method.GET, null, model);
		}
		
		model.addAttribute("dto", dto);
		model.addAttribute("loveHeight", dto.getTemperature() * 0.01);

		return "event/thermometer/"+year; 
		 
	}
	
	
	@ResponseBody
	@GetMapping("/event/loveBoard/{year}")
	public  Map<String, Object> getThermometerOfLoveforBoard(@PathVariable("year") int year) throws JsonProcessingException {
				
		ThermometerLoveDTO dto = adminEventService.getThermometerLoveAdmin(year);
		Map<String, Object> res = new HashMap<>();
		
		if(dto == null) {
			dto = new ThermometerLoveDTO(year, 0.0);			
		}
		
		res.put("data", dto);		
								        									
		return res; 		 
	}
}
