package com.vegemil.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GreenbiaMainController {
	
	@GetMapping("/greenbia/index")
	public String moveGreenbiaindex() {
		return "index_greenbia";
	}
	
	@RequestMapping(value = "/greenbia/{viewName}")
    public String moveGreenbiaPage(@PathVariable(value = "viewName", required = false) String viewName)throws Exception{
		
		return "greenbia/"+viewName;
    }

}
