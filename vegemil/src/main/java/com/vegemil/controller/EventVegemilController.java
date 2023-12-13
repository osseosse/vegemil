package com.vegemil.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.vegemil.domain.ThermometerLoveDTO;
import com.vegemil.service.AdminEventService;
import com.vegemil.util.UiUtils;

import lombok.extern.slf4j.Slf4j;

@Controller
public class EventVegemilController {
	
	@Autowired
	private AdminEventService adminEventService;
	
	@GetMapping("/event/{eventTitle}")
	public String beanSoupBrand(@PathVariable(value = "eventTitle", required = false) String eventTitle ) {
		return "event/"+ eventTitle;
	}
	
	@GetMapping("/event/loveVegemil/{year}")
	public String getThermometerOfLove(@PathVariable("year") int year, Model model) {
		
		ThermometerLoveDTO dto = adminEventService.getThermometerLove(year);
		
		model.addAttribute("dto", dto);
		model.addAttribute("loveHeight", dto.getTemperature() * 0.01);

		return "event/thermometer/"+year; 
		 
	}
	
}
