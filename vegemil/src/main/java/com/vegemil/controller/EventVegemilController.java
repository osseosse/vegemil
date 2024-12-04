package com.vegemil.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
		
		
		ThermometerLoveDTO dto = adminEventService.getThermometerLove(year);
		
		if(dto == null) {
			return showMessageWithRedirect("유효하지 않은 접근입니다.", "/event/list", Method.GET, null, model);
		}
		
		long loveHeight = (long) (dto.getTemperature() == 0.0 ? 1 : dto.getTemperature() * 0.01);
				
		model.addAttribute("dto", dto);
		model.addAttribute("loveHeight", loveHeight);

		return "event/thermometer/"+year; 
		 
	}
	
}
