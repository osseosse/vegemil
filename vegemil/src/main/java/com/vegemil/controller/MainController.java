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
		MemberDTO member = null;
		if(authentication != null)
        member = (MemberDTO) authentication.getPrincipal();  //userDetail 객체를 가져옴
		
        if(member != null) {
	        if(member.getMActive() != "1") {
	        	return "member/joinConfirm";
	        }
	        model.addAttribute("member",member);	//유저 정보
        }
        
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
	
	@RequestMapping(value = "/information/{viewName}")
    public String moveInformation(@PathVariable(value = "viewName", required = false) String viewName)throws Exception{
		
		return "information/"+viewName;
    }
	
	@RequestMapping(value = "/member/{viewName}")
    public String moveMember(@PathVariable(value = "viewName", required = false) String viewName)throws Exception{
		
		return "member/"+viewName;
    }
	
	@RequestMapping(value = "/application/{viewName}")
    public String moveAapplication(@PathVariable(value = "viewName", required = false) String viewName)throws Exception{
		
		return "application/"+viewName;
    }
	
	@GetMapping("/mail")
    public String dispMail() {
        return "utils/mail";
    }
	
}
