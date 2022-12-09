package com.vegemil.controller.greenbia;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GreenbiaMainController {
	
	@GetMapping("/greenbia/index")
	public String index() {
		return "greenbia/index";
	}
	
	@GetMapping("/greenbia/newInfo")
	public String newInfo() {
		return "greenbia/newInfo";
	}
	
	@GetMapping("/greenbia/features")
	public String features() {
		return "greenbia/features";
	}
	
	@GetMapping("/greenbia/history01")
	public String history1() {
		return "greenbia/history01";
	}
	
	@GetMapping("/greenbia/history02")
	public String history2() {
		return "greenbia/history02";
	}
	
	@GetMapping("/greenbia/history03")
	public String history3() {
		return "greenbia/history03";
	}
	
	@GetMapping("/greenbia/history04")
	public String history4() {
		return "greenbia/history04";
	}
	
	@GetMapping("/greenbia/thesis")
	public String thesis() {
		return "greenbia/thesis";
	}

}
