package com.vegemil.controller;

import lombok.RequiredArgsConstructor;
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

    private final CompanyService companyService;

    @GetMapping("/pr/mediaNews")
    public String openFaqList(@ModelAttribute("params") final SearchDTO params, Model model) {
    	
    	List<MediaNewsDTO> mediaNewsList = companyService.findAllMediaNews(params);
        model.addAttribute("mediaNewsList", mediaNewsList);
        
        return "pr/mediaNews";
    }

    
    @ResponseBody
    @GetMapping(value="/pr/saveMediaNews")
	public boolean saveMediaNews(MediaNewsDTO params) throws Exception{
		boolean result = companyService.saveMediaNews(params);
		return result;
	}

}
