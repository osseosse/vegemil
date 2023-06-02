package com.vegemil.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vegemil.domainEday.EdayVempDTO;
import com.vegemil.service.EdayVempService;
import com.vegemil.util.UiUtils;

@Controller
public class TempDevController extends UiUtils {
	
	@Autowired
	private EdayVempService  edayVempService;
	


    @GetMapping("/devTestVemp")
    @ResponseBody
    public String devTestVemp() {
    	
    	
    	EdayVempDTO vemp = new EdayVempDTO();
    	
    	System.out.println("왜..?");
    	try {
    		vemp = edayVempService.getVempInfo("hypark023@osse.co.kr");
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return vemp.toString();
    	
    	
    }
    

  

}
