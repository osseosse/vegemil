package com.vegemil.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.vegemil.domain.MemberDTO;

@Controller
public class MainController {
	
	@RequestMapping(value = "/")
	public String index(Model model, Authentication authentication) {
		//Authentication 객체를 통해 유저 정보를 가져올 수 있다.
		MemberDTO member = new MemberDTO();
		if(authentication != null)
        member = (MemberDTO) authentication.getPrincipal();  //userDetail 객체를 가져옴
		
        model.addAttribute("member",member);	//유저 정보
        
        return "index";
	}
	
	@RequestMapping(value = "/requestAuth")
    public ModelAndView jspTest()throws Exception{
		ModelAndView mav = new ModelAndView("input_seed");
		return mav;
    }
	
	@RequestMapping(value = "/home")
    public String main()throws Exception{
		return "index";
    }
	
	@RequestMapping(value = "/fragments/{viewName}")
    public String openFragments(@PathVariable(value = "viewName", required = false) String viewName)throws Exception{
		
		return "fragments/"+viewName;
    }
	
	@RequestMapping(value = "/communication/{viewName}")
    public String moveCommunication(@PathVariable(value = "viewName", required = false) String viewName)throws Exception{
		
		return "communication/"+viewName;
    }
	
	@RequestMapping(value = "/company/{viewName}")
    public String moveCompany(@PathVariable(value = "viewName", required = false) String viewName)throws Exception{
		
		return "company/"+viewName;
    }
	
	@RequestMapping(value = "/rnd/{viewName}")
    public String moveRnd(@PathVariable(value = "viewName", required = false) String viewName)throws Exception{
		
		return "rnd/"+viewName;
    }
	
	@GetMapping("/mail")
    public String dispMail() {
        return "utils/mail";
    }
	
}
