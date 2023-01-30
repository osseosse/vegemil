package com.vegemil.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.vegemil.constant.Method;
import com.vegemil.domain.MediaNewsDTO;
import com.vegemil.domain.MemberDTO;
import com.vegemil.service.CompanyService;
import com.vegemil.service.MemberService;
import com.vegemil.util.UiUtils;

@Controller
public class MainController extends UiUtils {
	
	@Autowired
	private CompanyService companyService;
	
	@RequestMapping(value = "/")
	public String moveIndex(Model model, Authentication authentication) throws Exception {
		
		MemberDTO member = new MemberDTO();
		
		try {
//			String ipv4 = Inet4Address.getLocalHost().getHostAddress();
//			if(ipv4.equals("211.204.41.41") || ipv4.equals("115.88.198.133") || ipv4.equals("192.168.26.40")) {
//			} else {
//				return "member/server";
//			}
			
			//Authentication 객체를 통해 유저 정보를 가져올 수 있다.
			if(authentication != null) {
		        member = (MemberDTO) authentication.getPrincipal();  //userDetail 객체를 가져옴
		        
		        if("1".equals(member.getMIsIdle())){
		        	return showMessageWithRedirect("고객님은 휴면 회원입니다. 휴면 해제 페이지로 이동합니다.", "/member/wakeUp", Method.GET, null, model);
		        }
		        
		        model.addAttribute("member",member);	//유저 정보
			}
			List<MediaNewsDTO> mediaNewsList = companyService.getMediaNewsTop3();
	        model.addAttribute("mediaNewsList", mediaNewsList);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        return "index";
	}

	@RequestMapping(value = "/main/default.aspx")
	public String moveOldIndex1(Model model, Authentication authentication) throws Exception {
		
		MemberDTO member = new MemberDTO();
		
		try {
			
			//Authentication 객체를 통해 유저 정보를 가져올 수 있다.
			if(authentication != null) {
		        member = (MemberDTO) authentication.getPrincipal();  //userDetail 객체를 가져옴
		        
		        if("1".equals(member.getMIsIdle())){
		        	return showMessageWithRedirect("고객님은 휴면 회원입니다. 휴면 해제 페이지로 이동합니다.", "/member/wakeUp", Method.GET, null, model);
		        }
		        
		        model.addAttribute("member",member);	//유저 정보
			}
			List<MediaNewsDTO> mediaNewsList = companyService.getMediaNewsTop3();
	        model.addAttribute("mediaNewsList", mediaNewsList);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        return "index";
	}
	
	@RequestMapping(value = "/Main/default.aspx")
	public String moveOldIndex2(Model model, Authentication authentication) throws Exception {
		
		MemberDTO member = new MemberDTO();
		
		try {
			
			//Authentication 객체를 통해 유저 정보를 가져올 수 있다.
			if(authentication != null) {
		        member = (MemberDTO) authentication.getPrincipal();  //userDetail 객체를 가져옴
		        
		        if("1".equals(member.getMIsIdle())){
		        	return showMessageWithRedirect("고객님은 휴면 회원입니다. 휴면 해제 페이지로 이동합니다.", "/member/wakeUp", Method.GET, null, model);
		        }
		        
		        model.addAttribute("member",member);	//유저 정보
			}
			List<MediaNewsDTO> mediaNewsList = companyService.getMediaNewsTop3();
	        model.addAttribute("mediaNewsList", mediaNewsList);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        return "index";
	}
	
	@RequestMapping(value = "/main/customer/supportFlow.aspx")
	public String moveSupportFlow(Model model, @RequestParam(value = "page_code", required = false) String page_code) {
		
        return "redirect:/communication/voc";
	}
	
	@RequestMapping(value = "/main/product/list.aspx")
	public String redirectVegemil(Model model, @RequestParam(value = "category_code", required = false) String category_code) {
		
		String redirectUrl = "";
		
		if(category_code.equals("V00")) {
			redirectUrl = "redirect:/brandStory/vegemil";
		} else if(category_code.equals("G00")) {
			redirectUrl = "redirect:/brandStory/greenbia";
		}
		
        return redirectUrl;
	}
	
	@RequestMapping(value = "/requestAuth")
    public ModelAndView jspTest()throws Exception{
		ModelAndView mav = new ModelAndView("input_seed");
		return mav;
    }
	
	@RequestMapping(value = "/home")
    public String moveMain(Model model, Authentication authentication)throws Exception{
		
		//Authentication 객체를 통해 유저 정보를 가져올 수 있다.
		MemberDTO member = new MemberDTO();
		if(authentication != null) {
	        member = (MemberDTO) authentication.getPrincipal();  //userDetail 객체를 가져옴
	        
	        if("1".equals(member.getMIsIdle())){
	        	return showMessageWithRedirect("고객님은 휴면 회원입니다. 휴면 해제 페이지로 이동합니다.", "/member/wakeUp", Method.GET, null, model);
	        }
	        
	        model.addAttribute("member",member);	//유저 정보
		}
		List<MediaNewsDTO> mediaNewsList = companyService.getMediaNewsTop3();
        model.addAttribute("mediaNewsList", mediaNewsList);
		
		return "index";
    }
	
	@RequestMapping(value = "/fragments/{viewName}")
    public String openFragments(@PathVariable(value = "viewName", required = false) String viewName)throws Exception{
		
		return "fragments/"+viewName;
    }
	
	@RequestMapping(value = "/company/{viewName}")
    public String moveCompany(@PathVariable(value = "viewName", required = false) String viewName)throws Exception{
		
		return "company/"+viewName;
    }
	
	@RequestMapping(value = "/rnd/{viewName}")
    public String moveRnd(@PathVariable(value = "viewName", required = false) String viewName)throws Exception{
		
		return "rnd/"+viewName;
    }
	
	@RequestMapping(value = "/story/{viewName}")
    public String moveStory(@PathVariable(value = "viewName", required = false) String viewName)throws Exception{
		
		return "story/"+viewName;
    }
	
	@GetMapping("/mail")
    public String dispMail() {
        return "utils/mail";
    }
	
}
