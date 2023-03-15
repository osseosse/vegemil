package com.vegemil.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vegemil.domain.AdminAdEctDTO;
import com.vegemil.domain.AdminAviCFDTO;
import com.vegemil.domain.AgencyDTO;
import com.vegemil.domain.MediaNewsDTO;
import com.vegemil.domain.SearchDTO;
import com.vegemil.service.AdminAdEtcService;
import com.vegemil.service.AdminAviCFService;
import com.vegemil.service.AdminAviRadioCMService;
import com.vegemil.service.AdminVideoContestService;
import com.vegemil.service.CompanyService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class CompanyController {

	@Autowired
    private final CompanyService companyService;
	
	@Autowired
	private AdminAviCFService adminAviCFService;
	
	@Autowired
	private AdminAviRadioCMService adminRadioCMSerive;
	
	@Autowired 
	private AdminVideoContestService adminVideoContestService;
	
	@Autowired
	private AdminAdEtcService adminAdEtcService;
	

	@RequestMapping(value = "/company/{viewName}")
    public String moveCompany(@PathVariable(value = "viewName", required = false) String viewName, Model model)throws Exception{
		
		if(viewName.equals("media")) {
			AdminAviCFDTO cfDto = new AdminAviCFDTO(); cfDto.setTOnair("1");
			AdminAdEctDTO etcDto = new AdminAdEctDTO(); etcDto.setTOnair("1"); etcDto.setSearchKeyword("youtube");
			model.addAttribute("cfList", adminAviCFService.getAdminAviCFList(cfDto));
			model.addAttribute("cmList", adminRadioCMSerive.getRadioCMList(null));
			model.addAttribute("contestList", adminVideoContestService.getAdminVideoContestList(null));
			model.addAttribute("etcList", adminAdEtcService.getAdminAdEtcList(etcDto));
		}
		
		return "company/"+viewName;
    }
	
	@GetMapping("/company/agency") public String getAgencyInfo(Model model, AgencyDTO params){
		return "company/agency"; 
	}
	
	@RequestMapping(value = "/company/agencyDev")
    public @ResponseBody List<AgencyDTO> getAgencyListByArea(@ModelAttribute("params") final AgencyDTO params, HttpServletRequest req, 
    			Map<String, Object> commandMap)throws Exception{
		
		List<AgencyDTO> agencyList = companyService.getAgencyList(params);
		return agencyList;
    }
	
	@RequestMapping(value = "/company/agencyDevSearch")
	public @ResponseBody List<AgencyDTO> getAgencyListBykeyword(@RequestParam("searchKeyword") String searchKeyword, 
						@RequestParam("searchType") String searchType, HttpServletRequest req, Map<String, Object> commandMap)throws Exception{
		
		System.out.println(searchType);
		System.out.println(searchKeyword);
		
		AgencyDTO params = new AgencyDTO();
		params.setSearchKeyword(searchKeyword);
		params.setSearchType(searchType);
		List<AgencyDTO> agencyList = companyService.getAgencyList(params);
		return agencyList;
	}
	
	
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
