package com.vegemil.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class VegemilBabyController {
	
	@RequestMapping(value = "/vegemilBaby/{viewName}")
    public String moveVegemilBabyPage(@PathVariable(value = "viewName", required = false) String viewName)throws Exception{
		
		return "vegemilBaby/"+viewName;
    }

}
