package com.vegemil.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class GolobalizeDevController {
	
	
	@GetMapping("/en/index")
	public String GlobalController(Model model) {
		
		return "";
		
		
		
	}
	
	

}
