package com.vegemil.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.vegemil.constant.Method;
import com.vegemil.domain.AdminCalendarModelDTO;
import com.vegemil.domain.MediaNewsDTO;
import com.vegemil.domain.MemberDTO;
import com.vegemil.domain.PopupDTO;
import com.vegemil.service.AdminService;
import com.vegemil.service.CompanyService;
import com.vegemil.service.MemberService;
import com.vegemil.service.PopupService;
import com.vegemil.util.UiUtils;

@Controller
public class MainController extends UiUtils {
	
	@Value("${spring.servlet.multipart.location}")
    private String uploadPath;
	
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private PopupService popupSerive;
	
	private static String[] globalRndList = {"introduce","haccp","fssc","halal"};
	
	@RequestMapping(value = "/")
	public String moveIndex(Model model, Authentication authentication, HttpServletResponse response,
												@CookieValue(value = "lang", required = false) String localCookie) throws Exception {
		
		if("en".equals(localCookie)) {
			return "en/index";
		}
		
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
	        model.addAttribute("isPopup", popupSerive.activePopupCount());
			
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
	public RedirectView moveSupportFlow(Model model, @RequestParam(value = "page_code", required = false) String page_code) {
		
        return new RedirectView("/communication/voc");
	}
	
	@RequestMapping(value = "/main/product/list.aspx")
	public RedirectView redirectVegemil(Model model, @RequestParam(value = "category_code", required = false) String category_code) {
		
		String redirectUrl = "";
		
		if(category_code.equals("V00")) {
			redirectUrl = "/brandStory/vegemil";
		} else if(category_code.equals("G00")) {
			redirectUrl = "/brandStory/greenbia";
		}
		
        return new RedirectView(redirectUrl);
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
	
	@RequestMapping(value = "/rnd/{viewName}")
    public String moveRnd(@PathVariable(value = "viewName", required = false) String viewName, 
    									@CookieValue(value = "lang", required = false) String localCookie)throws Exception{
		
		if("en".equals(localCookie) && PatternMatchUtils.simpleMatch(globalRndList, viewName)) {			
			return "en/" + viewName;
		}		
		return "rnd/"+viewName;
    }
	
	@RequestMapping(value = "/story/{viewName}")
    public String moveStory(@PathVariable(value = "viewName", required = false) String viewName)throws Exception{
		
		return "story/"+viewName;
    }

	
	@PostMapping("/main/mediaNews/count")
	public @ResponseBody Map<String, Object> updateMediaNewsCount(Long mIdx) {
		
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		
		try {
			boolean isUpdate = companyService.updateMediaNewsCount(mIdx);
			rtnMap.put("result", isUpdate);
		} catch (DataAccessException e) {

		} catch (Exception e) {
		}
		
		return rtnMap;
	}
	
	
	//정적 이미지 불러오기
	@GetMapping("/web/upload/MediaNews/{filename}")
	public ResponseEntity<Resource> display(@PathVariable(value = "filename", required = false) String filename) {
		Resource resource = new FileSystemResource(uploadPath + "/upload/MediaNews/" + filename);
		//Resource resource = new FileSystemResource("D:/upload/admin/" + filename);
		if(!resource.exists()) 
			return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);
		HttpHeaders header = new HttpHeaders();
		Path filePath = null;
		try {
			filePath = Paths.get(uploadPath + "/upload/MediaNews/" + filename);
			//filePath = Paths.get("D:/upload/admin/" + filename);
			header.add("Content-type", Files.probeContentType(filePath));
		} catch(IOException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Resource>(resource, header, HttpStatus.OK);
	}
	
	@PostMapping("/analysis/agentCount")
	@ResponseBody
	public JsonObject updateAgentCount(@RequestBody Map<String, Object> agent) {

		JsonObject jsonObj = new JsonObject();
		LocalDate now = LocalDate.now();
			
		if(agent != null) {
			
			String yymm = now.toString().substring(2, 7);
			agent.putIfAbsent("yymm", yymm);
	        boolean isRegistered1 = adminService.updateAgentCount(agent);
	        jsonObj.addProperty("result1", isRegistered1);
	        
	        if(agent.get("mUrl") != null) {
	        	String month = now.toString().substring(5, 7);
	        	if(month.length() == 1)
	        		month = "0" + month;
	        	
	        	boolean isRegistered2 = adminService.registerUrl(agent.get("mUrl").toString(), agent.get("mTitle").toString(), month);
	        	jsonObj.addProperty("result2", isRegistered2);
	        }
		}

		return jsonObj;
	}
	
	
	@GetMapping("/popup/info")
	@ResponseBody
	public List<PopupDTO> popupInfo(@RequestBody(required = false) Map<String, Object> agent) throws JsonProcessingException {
		return popupSerive.getPopupInfo();
	}
	
}
