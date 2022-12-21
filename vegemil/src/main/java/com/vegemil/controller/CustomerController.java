package com.vegemil.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.vegemil.domain.FaqDTO;
import com.vegemil.domain.FaqFeedbackDTO;
import com.vegemil.domain.MemberDTO;
import com.vegemil.domain.SearchDTO;
import com.vegemil.service.CustomerService;

import java.util.List;

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
        
        return "faq/list";
    }

    
    @ResponseBody
    @GetMapping(value="/faq/saveFaqFeedback")
	public boolean saveFaqFeedback(FaqFeedbackDTO params) throws Exception{
		boolean result = customerService.saveFaqFeedback(params); // 중복확인한 값을 int로 받음
		return result;
	}

}
