package com.vegemil.controller;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonObject;
import com.vegemil.domain.FaqDTO;
import com.vegemil.domain.FaqFeedbackDTO;
import com.vegemil.domain.MemberDTO;
import com.vegemil.domain.SearchDTO;
import com.vegemil.service.CustomerService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/faq/list")
    public String openFaqList(@ModelAttribute("params") final SearchDTO params, Model model) {
    
    	params.setCategory("11");
    	List<FaqDTO> faqList11 = customerService.findAllFaq(params);
    	params.setCategory("22");
    	List<FaqDTO> faqList22 = customerService.findAllFaq(params);
    	params.setCategory("33");
    	List<FaqDTO> faqList33 = customerService.findAllFaq(params);
    	params.setCategory("44");
    	List<FaqDTO> faqList44 = customerService.findAllFaq(params);
    	params.setCategory("55");
    	List<FaqDTO> faqList55 = customerService.findAllFaq(params);
        model.addAttribute("faqList11", faqList11);
        model.addAttribute("faqList22", faqList22);
        model.addAttribute("faqList33", faqList33);
        model.addAttribute("faqList44", faqList44);
        model.addAttribute("faqList55", faqList55);
        model.addAttribute("searchKeyword", params.getSearchKeyword());
        
        model.addAttribute("params", params);
        model.addAttribute("dataCount",faqList11.size()+faqList22.size()+faqList33.size()+faqList44.size()+faqList55.size());
        
        return "faq/list";
    }

    @GetMapping(value="/faq/saveFaqFeedback")
	public @ResponseBody JsonObject saveFaqFeedback(FaqFeedbackDTO params, Authentication authentication) throws Exception{

    	JsonObject jsonObj = new JsonObject();
    	Boolean result = false;
    	
    	try {
    		if(authentication != null) {
				MemberDTO member = new MemberDTO();
				member = (MemberDTO) authentication.getPrincipal();  //userDetail 객체를 가져옴
				
				params.setFName(member.getMName());
				params.setFId(member.getMId());
	    		result = customerService.saveFaqFeedback(params);
    		}
    		jsonObj.addProperty("result", result);
		
		} catch (DataAccessException e) {
			jsonObj.addProperty("message", "데이터베이스 처리 과정에 문제가 발생하였습니다.");

		} catch (Exception e) {
			jsonObj.addProperty("message", "시스템에 문제가 발생하였습니다.");
		}

		return jsonObj;
    	
    	
	}

}
