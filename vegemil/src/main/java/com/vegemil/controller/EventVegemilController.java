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
@Slf4j
public class EventVegemilController extends UiUtils {
	
	@Autowired
	private AdminEventService adminEventService;
	
	@Value("${spring.servlet.multipart.location}")
    private String uploadPath;
	
	@GetMapping("/event/{eventTitle}")
	public String beanSoupBrand(@PathVariable(value = "eventTitle", required = false) String eventTitle ) {
		return "event/"+ eventTitle;
	}
	
	@GetMapping("/event/loveVegemil/{year}")
	public String getThermometerOfLove(@PathVariable("year") String year, Model model) {
		
		ThermometerLoveDTO dto = adminEventService.getThermometerLove(Integer.parseInt(year));
		
		int temperature = Integer.parseInt(dto.getTemperature());
		int loveHeight = (int) ((temperature <= 99) ? 1: (temperature * 0.01));
		
		model.addAttribute("dto", dto);
		model.addAttribute("loveHeight", loveHeight);

		return "event/thermometer/"+year; 
		 
	}
	
	
	
}
