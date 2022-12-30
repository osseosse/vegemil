package com.vegemil.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.vegemil.domain.MediaNewsDTO;
import com.vegemil.domain.SearchDTO;
import com.vegemil.service.CompanyService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CompanyController {

	@Autowired
    private final CompanyService companyService;

    @GetMapping("/company/mediaNews")
    public String openFaqList(@ModelAttribute("params") final SearchDTO params, Model model) {
    	
    	List<MediaNewsDTO> mediaNewsList = companyService.findAllMediaNews(params);
        model.addAttribute("mediaNewsList", mediaNewsList);
        model.addAttribute("newsCount", params.getPaginationInfo().getTotalRecordCount());
        return "company/mediaNews";
    }
    
    @ResponseBody
    @GetMapping(value="/company/saveMediaNews")
	public boolean saveMediaNews(MediaNewsDTO params) throws Exception{
		boolean result = companyService.saveMediaNews(params);
		return result;
	}
    
    @GetMapping("/company/notice")
    public String openNotice(Model model) {
        
        return "company/notice";
    }

}
