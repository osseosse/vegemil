package com.vegemil.controller.greenbia;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GreenbiaEnteralController {
	
	@GetMapping("/greenbia/enteral")
	public String enteral() {
		return "greenbia/enteral";
	}
	
	@GetMapping("/greenbia/ingestion")
	public String ingestion() {
		return "greenbia/ingestion";
	}
	
	@GetMapping("/greenbia/safety")
	public String safety() {
		return "greenbia/safety";
	}
	
	

}
