package com.vegemil.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.vegemil.constant.Method;
import com.vegemil.domain.MemberDTO;
import com.vegemil.domain.VisitDTO;
import com.vegemil.service.RndService;
import com.vegemil.util.UiUtils;

@Controller
public class RndController extends UiUtils {

	@Autowired
	RndService rndService;
	
	@GetMapping(value="/rnd/factory")
	public String getFactory() {
		return "rnd/factory";
	}
	
	@GetMapping(value = "/rnd/factoryTour")
    public String moveRnd(Model model, Authentication authentication)throws Exception{
		
		MemberDTO member = new MemberDTO();
		
		if(authentication != null) {
	        member = (MemberDTO) authentication.getPrincipal();  //userDetail 객체를 가져옴
	        model.addAttribute("member",member);	//유저 정보
		}
		
		return "rnd/factoryTour";
    }
	
	@GetMapping(value = "/rnd/tourApply")
    public String moveTourApply(Model model, Authentication authentication)throws Exception{
		
		MemberDTO member = new MemberDTO();
		
		if(authentication != null) {
	        member = (MemberDTO) authentication.getPrincipal();  //userDetail 객체를 가져옴
	        model.addAttribute("member",member);	//유저 정보
		}
		
		return "rnd/tourApply";
    }


	@PostMapping(value="/rnd/factoryTour")
	public String postVisitForm(VisitDTO visitDto, Model model, Authentication authentication)  {
		
		try {
		
			if(authentication == null) {
				return showMessageWithRedirect("로그인후 이용바랍니다.", "/rnd/factory", Method.GET, null, model);
			} else {
			
				int result = rndService.insertMvisit(visitDto); 
		
				if(result > 0) {
					return showMessageWithRedirect("견학 신청이 정상적으로 접수되었습니다.", "/rnd/factory", Method.GET, null, model);
				}
			}
		
		} catch(Exception e) {
			return showMessageWithRedirect("올바르지 않은 접근입니다.", "/", Method.GET, null, model);
		}
		
		return showMessageWithRedirect("견학 신청이 실패했습니다.", "/rnd/factoryTour", Method.GET, null, model);

	}
}
