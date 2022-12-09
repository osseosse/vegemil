package com.vegemil.controller.vegemilBaby;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VegemilBabyMainController {
	
	@GetMapping("/vegemilBaby/index")
	public String index() {
		return "vegemilBaby/index";
	}
	
	@GetMapping("/vegemilBaby/brand")
	public String brand() {
		return "vegemilBaby/brand";
	}
	
	@GetMapping("/vegemilBaby/nutrition")
	public String nutrition() {
		return "vegemilBaby/nutrition";
	}
	
	@GetMapping("/vegemilBaby/safe")
	public String safe() {
		return "vegemilBaby/safe";
	}
	
	@GetMapping("/vegemilBaby/tv")
	public String tv() {
		return "vegemilBaby/tv";
	}
	
	
	@GetMapping("/vegemilBaby/premium")
	public String premium() {
		return "vegemilBaby/premium";
	}
	
	@GetMapping("/vegemilBaby/belief")
	public String belief() {
		return "vegemilBaby/belief";
	}
	
		
	@GetMapping("/vegemilBaby/faq")
	public String faq() {
		return "vegemilBaby/faq";
	}
	
	@GetMapping("/vegemilBaby/lactation")
	public String lactation() {
		return "vegemilBaby/lactation";
	}
	
	

}
