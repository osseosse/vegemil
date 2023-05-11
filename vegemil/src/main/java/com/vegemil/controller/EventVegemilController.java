package com.vegemil.controller;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.vegemil.util.UiUtils;

@Controller
public class EventVegemilController extends UiUtils {
	
	@Value("${spring.servlet.multipart.location}")
    private String uploadPath;
	
	@GetMapping("/event/{eventTitle}")
	public String beanSoupBrand(@PathVariable(value = "eventTitle", required = false) String eventTitle ) {
		return "event/"+ eventTitle;
	}
	
}
