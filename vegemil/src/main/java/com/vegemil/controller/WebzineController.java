package com.vegemil.controller;
import java.io.PrintWriter;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vegemil.constant.Method;
import com.vegemil.domain.BannerDTO;
import com.vegemil.domain.MemberDTO;
import com.vegemil.domain.SearchDTO;
import com.vegemil.domain.SubscribeDTO;
import com.vegemil.domain.WebzineDTO;
import com.vegemil.domain.WebzineEventDTO;
import com.vegemil.mapper.WebzineMapper;
import com.vegemil.service.WebzineService;
import com.vegemil.util.UiUtils;

@Controller
public class WebzineController extends UiUtils {

	@Autowired
	private WebzineService webzineService;	
	
	@GetMapping(value = "/main/webzine/list")
	public String openWebzineList( Model model, @RequestParam(value = "searchKeyword", required = false) String searchKeyword) {
	
		return "webzine/list";
	}
	
	
	@RequestMapping(value = {"/webzine","/Main/webzine/default.aspx","/main/webzine"})
	public String moveWebzine(Model model, @RequestParam(required = false) SearchDTO params) {
		
		List<WebzineDTO> webzineList = webzineService.findAllWebzine(params);
		model.addAttribute("webzineList", webzineList);
		
		return "webzine/default";
	}

	
	@GetMapping(value = "/webzine/emaildeny")
	public String moveEmaildeny(@RequestParam(required = false) String emailaddr, Model model) {

		model.addAttribute("emailaddr", emailaddr);
		return "webzine/emaildeny";
	}
	
	@GetMapping(value = "/webzine/reject")
	public String saveReject(
			@RequestParam(required = false) String emailaddr,
			Model model, HttpServletResponse response, HttpServletRequest request) throws Exception {
		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		try {
			
			boolean isRegistered = webzineService.saveSendYn(emailaddr);
			if (isRegistered == false) {
				return showMessageWithRedirect("데이터베이스 처리 과정에 문제가 발생하였습니다.", "/webzine", Method.GET, null, model);
			}

		} catch (Exception e) {
			out.println("<script>alert('시스템에 문제가 발생하였습니다.'); history.go(-1);</script>");
			out.flush();
			return showMessageWithRedirect("시스템에 문제가 발생하였습니다.", "/webzine", Method.GET, null, model);
		}

		return showMessageWithRedirect("수신거부 완료되었습니다.", "/", Method.GET, null, model);
	}
	
	@RequestMapping(value = {"/webzine/subscribe", "/main/publicCenter/webzine.aspx"})
	public String openSubscribe(Model model, Authentication authentication) {
		
		SubscribeDTO subscribe = new SubscribeDTO();
		String mName = "";
		String mHp = "";
		String txtEmail = "";
		String selEmail = "";
		String mEmail = "";
		
		if(authentication != null) {
			MemberDTO member = (MemberDTO) authentication.getPrincipal();
			
			if("1".equals(member.getMIsIdle())){
	        	return showMessageWithRedirect("고객님은 휴면 회원입니다. 휴면 해제 페이지로 이동합니다.", "/member/wakeUp", Method.GET, null, model);
	        }
			
			if(member != null) {
				mName = member.getMName();
				mHp = member.getMHp();
				txtEmail = member.getTxtEmail();
				selEmail = member.getSelEmail();
				mEmail = member.getMEmail();
			}
		}
		
		model.addAttribute("mName", mName);
		model.addAttribute("mHp", mHp);
		model.addAttribute("txtEmail", txtEmail);
		model.addAttribute("selEmail", selEmail);
		model.addAttribute("mEmail", mEmail);
		model.addAttribute("subscribe", subscribe);
		
		return "webzine/subscribe";
	}
	
	@PostMapping(value = "/webzine/saveSabo")
	public String saveSabo(
			@ModelAttribute("subscribe") final @Valid SubscribeDTO subscribe,
			Model model, HttpServletResponse response, HttpServletRequest request) throws Exception {
		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		try {
			
			boolean isRegistered = webzineService.saveSubscribe(subscribe);
			if (isRegistered == false) {
				out.println("<script>alert('이미 신청된 이메일입니다.'); history.go(-1);</script>");
				out.flush();
				return showMessageWithRedirect("데이터베이스 처리 과정에 문제가 발생하였습니다.", "/webzine/subscribe", Method.GET, null, model);
			}
			model.addAttribute("subscribe", subscribe);
			
		} catch (DataAccessException e) {
			out.println("<script>alert('데이터베이스 처리 과정에 문제가 발생하였습니다.'); history.back();</script>");
			out.flush();
			return showMessageWithRedirect("데이터베이스 처리 과정에 문제가 발생하였습니다.", "/webzine/subscribe", Method.GET, null, model);

		} catch (Exception e) {
			out.println("<script>alert('시스템에 문제가 발생하였습니다.'); history.go(-1);</script>");
			out.flush();
			return showMessageWithRedirect("시스템에 문제가 발생하였습니다.", "/webzine/subscribe", Method.GET, null, model);
		}

		return showMessageWithRedirect("웹진 신청이 완료되었습니다.", "/webzine", Method.GET, null, model);
	}
	
	@PostMapping(value = "/webzine/saveEvent")
	public String saveWebzineEvent(
			@ModelAttribute("webzineEvent") final @Valid WebzineEventDTO webzineEvent,
			Model model, HttpServletResponse response, HttpServletRequest request) throws Exception {
		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		try {
			
			boolean isRegistered = webzineService.isWebzineEvent(webzineEvent);
			if (isRegistered == true) {
				out.println("<script>alert('고객님은 이미 이벤트 신청을 완료했습니다.'); history.go(-1);</script>");
				out.flush();
				return showMessageWithRedirect("데이터베이스 처리 과정에 문제가 발생하였습니다.", "/webzine/event", Method.GET, null, model);
			}
			isRegistered = webzineService.saveWebzineEvent(webzineEvent);
			if (isRegistered == false) {
				out.println("<script>alert('이벤트 신청이 실패했습니다.'); history.go(-1);</script>");
				out.flush();
				return showMessageWithRedirect("데이터베이스 처리 과정에 문제가 발생하였습니다.", "/webzine/event", Method.GET, null, model);
			}
			
		} catch (DataAccessException e) {
			out.println("<script>alert('데이터베이스 처리 과정에 문제가 발생하였습니다.'); history.back();</script>");
			out.flush();
			return showMessageWithRedirect("데이터베이스 처리 과정에 문제가 발생하였습니다.", "/webzine/event", Method.GET, null, model);

		} catch (Exception e) {
			out.println("<script>alert('시스템에 문제가 발생하였습니다.'); history.go(-1);</script>");
			out.flush();
			return showMessageWithRedirect("시스템에 문제가 발생하였습니다.", "/webzine/event", Method.GET, null, model);
		}

		return showMessageWithRedirect("이벤트 응모가 완료되었습니다.", "/main/webzine", Method.GET, null, model);
	}
	
	@GetMapping(value = "/main/webzine/special/sub{fileNo}.aspx")
	public String openWebzineSpecial(Model model
								, @PathVariable(value = "fileNo", required = false) String fileNo) {
		
		model.addAttribute("fileNo", fileNo);
		
		return "webzine/special";
	}
	
	@GetMapping(value = "/webzine/special/sub{fileNo}")
	public String moveWebzineSpecial(Model model
								, @PathVariable(value = "fileNo", required = false) String fileNo) {
		
		model.addAttribute("fileNo", fileNo);
		
		return "webzine/special";
	}
	
	//상세진입 교체완
	@GetMapping(value = "/main/webzine/{qrtYear}/sub{fileNo}.aspx")
	public String openWebzineSubQY(@PathVariable(value = "qrtYear", required = false) String qrtYear
									, @PathVariable(value = "fileNo", required = false) String fileNo
									, @RequestParam(required = false) SearchDTO params, Model model, HttpServletRequest request) {
		
		String wYear = "";
		String qrt = "";
		String sortYear = "";
		String returnHtml = "";
		
		if(!qrtYear.equals("")) {
			wYear = qrtYear.substring(3, 7); // 2023
			sortYear = qrtYear.substring(5, 7); //23
			qrt  = qrtYear.substring(0, 2); //Q1
		}
		
		List<WebzineDTO> webzineYear = webzineService.getWebzineYear();
		List<WebzineDTO> webzineQrt = webzineService.getWebzineQrt();
		List<WebzineDTO> webzineLink = webzineService.getWebzineLink();
		
		List<WebzineDTO> webzineListQY = webzineService.getWebzineQrtYear(qrtYear);
		WebzineDTO webzine = new WebzineDTO();
		webzine.setQrtYear(qrtYear);
		webzine.setFileNo("sub"+fileNo); 
		
		webzine = webzineService.getWebzine(webzine);
		List<WebzineDTO> recommandWebzineList = webzineService.getRecommandWebzine(webzine);
		
		if(webzine != null) {
			model.addAttribute("title", webzine.getTitle());
			model.addAttribute("snsTitle", webzine.getSnsTitle());
			model.addAttribute("sumLine", webzine.getSumLine());
			
		}
		
		if(wYear.equals("2017") || wYear.equals("2018") || qrtYear.equals("Q1_2019")) {
			returnHtml = "webzine/oldSub";
		} else if((Integer.parseInt(wYear)==2023 && Integer.parseInt(qrt.substring(1)) >= 2) || (Integer.parseInt(wYear)>=2024)) {
			model.addAttribute("banner",webzineService.getBanner(new BannerDTO(qrtYear, "sub"+fileNo)));
			returnHtml = "webzine/newSub";
		} else {
			returnHtml = "webzine/sub";
		}
		
		
		model.addAttribute("countArticles", webzineListQY.size());
		model.addAttribute("webzineYear", webzineYear);
		model.addAttribute("webzineQrt", webzineQrt);
		model.addAttribute("webzineLink", webzineLink);
		model.addAttribute("webzineListQY", webzineListQY);
		model.addAttribute("recommandWebzineList", recommandWebzineList);
		model.addAttribute("qrtYear", qrtYear);
		model.addAttribute("sortYear", sortYear);
		model.addAttribute("qrt", qrt);
		model.addAttribute("wYear", wYear);
		model.addAttribute("num", fileNo);
		model.addAttribute("fileNo", "sub"+fileNo);
		
		return returnHtml;
	}
	
	//상세진입 교체완
	@GetMapping(value = "/webzine/{qrtYear}/sub{fileNo}.aspx")
	public String moveWebzineSubAspx(@PathVariable(value = "qrtYear", required = false) String qrtYear
									, @PathVariable(value = "fileNo", required = false) String fileNo
									, @RequestParam(required = false) SearchDTO params, Model model, HttpServletRequest request) {
		
		String wYear = "";
		String qrt = "";
		String sortYear = "";
		String returnHtml = "";
		
		if(!qrtYear.equals("")) {
			wYear = qrtYear.substring(3, 7); // 2023
			sortYear = qrtYear.substring(5, 7); //23
			qrt  = qrtYear.substring(0, 2); //Q1
		}
		
		List<WebzineDTO> webzineYear = webzineService.getWebzineYear();
		List<WebzineDTO> webzineQrt = webzineService.getWebzineQrt();
		List<WebzineDTO> webzineLink = webzineService.getWebzineLink();
		
		List<WebzineDTO> webzineListQY = webzineService.getWebzineQrtYear(qrtYear);
		WebzineDTO webzine = new WebzineDTO();
		webzine.setQrtYear(qrtYear);
		webzine.setFileNo("sub"+fileNo); 
		//webzine.setFileNo("sub05"); 개발용 하드코드
		webzine = webzineService.getWebzine(webzine);
		List<WebzineDTO> recommandWebzineList = webzineService.getRecommandWebzine(webzine);
		
		if(webzine != null) {
			model.addAttribute("title", webzine.getTitle());
			model.addAttribute("snsTitle", webzine.getSnsTitle());
			model.addAttribute("sumLine", webzine.getSumLine());
			
		}
		
		if(wYear.equals("2017") || wYear.equals("2018") || qrtYear.equals("Q1_2019")) {
			returnHtml = "webzine/oldSub";
		} else if((Integer.parseInt(wYear)==2023 && Integer.parseInt(qrt.substring(1)) >= 2) || (Integer.parseInt(wYear)>=2024)) {
			model.addAttribute("banner",webzineService.getBanner(new BannerDTO(qrtYear, "sub"+fileNo)));
			returnHtml = "webzine/newSub";
		} else {
			returnHtml = "webzine/sub";
		}
		
		
		model.addAttribute("countArticles", webzineListQY.size());
		model.addAttribute("webzineYear", webzineYear);
		model.addAttribute("webzineQrt", webzineQrt);
		model.addAttribute("webzineLink", webzineLink);
		model.addAttribute("webzineListQY", webzineListQY);
		model.addAttribute("recommandWebzineList", recommandWebzineList);
		model.addAttribute("qrtYear", qrtYear);
		model.addAttribute("sortYear", sortYear);
		model.addAttribute("qrt", qrt);
		model.addAttribute("wYear", wYear);
		model.addAttribute("num", fileNo);
		model.addAttribute("fileNo", "sub"+fileNo);
		
		return returnHtml;
	}
	
	//상세진입 교체완
	@GetMapping(value = "/Main/webzine/{qrtYear}/sub{fileNo}.aspx")
	public String moveWebzineSubQY(@PathVariable(value = "qrtYear", required = false) String qrtYear
									, @PathVariable(value = "fileNo", required = false) String fileNo
									, @RequestParam(required = false) SearchDTO params, Model model, HttpServletRequest request) {
		
		String wYear = "";
		String qrt = "";
		String sortYear = "";
		String returnHtml = "";
		
		if(!qrtYear.equals("")) {
			wYear = qrtYear.substring(3, 7); // 2023
			sortYear = qrtYear.substring(5, 7); //23
			qrt  = qrtYear.substring(0, 2); //Q1
		}
		
		List<WebzineDTO> webzineYear = webzineService.getWebzineYear();
		List<WebzineDTO> webzineQrt = webzineService.getWebzineQrt();
		List<WebzineDTO> webzineLink = webzineService.getWebzineLink();
		
		List<WebzineDTO> webzineListQY = webzineService.getWebzineQrtYear(qrtYear);
		WebzineDTO webzine = new WebzineDTO();
		webzine.setQrtYear(qrtYear);
		webzine.setFileNo("sub"+fileNo); 
		//webzine.setFileNo("sub05"); 개발용 하드코드
		webzine = webzineService.getWebzine(webzine);
		List<WebzineDTO> recommandWebzineList = webzineService.getRecommandWebzine(webzine);
		
		if(webzine != null) {
			model.addAttribute("title", webzine.getTitle());
			model.addAttribute("snsTitle", webzine.getSnsTitle());
			model.addAttribute("sumLine", webzine.getSumLine());
			
		}
		
		if(wYear.equals("2017") || wYear.equals("2018") || qrtYear.equals("Q1_2019")) {
			returnHtml = "webzine/oldSub";
		} else if((Integer.parseInt(wYear)==2023 && Integer.parseInt(qrt.substring(1)) >= 2) || (Integer.parseInt(wYear)>=2024)) {
			model.addAttribute("banner",webzineService.getBanner(new BannerDTO(qrtYear, "sub"+fileNo)));
			returnHtml = "webzine/newSub";
		} else {
			returnHtml = "webzine/sub";
		}
		
		
		model.addAttribute("countArticles", webzineListQY.size());
		model.addAttribute("webzineYear", webzineYear);
		model.addAttribute("webzineQrt", webzineQrt);
		model.addAttribute("webzineLink", webzineLink);
		model.addAttribute("webzineListQY", webzineListQY);
		model.addAttribute("recommandWebzineList", recommandWebzineList);
		model.addAttribute("qrtYear", qrtYear);
		model.addAttribute("sortYear", sortYear);
		model.addAttribute("qrt", qrt);
		model.addAttribute("wYear", wYear);
		model.addAttribute("num", fileNo);
		model.addAttribute("fileNo", "sub"+fileNo);
		
		return returnHtml;
	}
	
	// simple path version 상세진입 교체완
	@GetMapping(value = "/webzine/{qrtYear}/sub{fileNo}")
	public String moveWebzineSubQY2(@PathVariable(value = "qrtYear", required = false) String qrtYear
			, @PathVariable(value = "fileNo", required = false) String fileNo
			, @RequestParam(required = false) SearchDTO params, Model model, HttpServletRequest request) {
		
		String wYear = "";
		String qrt = "";
		String sortYear = "";
		String returnHtml = "";
		
		if(!qrtYear.equals("")) {
			wYear = qrtYear.substring(3, 7); // 2023
			sortYear = qrtYear.substring(5, 7); //23
			qrt  = qrtYear.substring(0, 2); //Q1
		}
		
		List<WebzineDTO> webzineYear = webzineService.getWebzineYear();
		List<WebzineDTO> webzineQrt = webzineService.getWebzineQrt();
		List<WebzineDTO> webzineLink = webzineService.getWebzineLink();
		
		List<WebzineDTO> webzineListQY = webzineService.getWebzineQrtYear(qrtYear);
		WebzineDTO webzine = new WebzineDTO();
		webzine.setQrtYear(qrtYear);
		webzine.setFileNo("sub"+fileNo); 
		
		webzine = webzineService.getWebzine(webzine);
		List<WebzineDTO> recommandWebzineList = webzineService.getRecommandWebzine(webzine);
		
		if(webzine != null) {
			model.addAttribute("title", webzine.getTitle());
			model.addAttribute("snsTitle", webzine.getSnsTitle());
			model.addAttribute("sumLine", webzine.getSumLine());
			
		}
		
		if(wYear.equals("2017") || wYear.equals("2018") || qrtYear.equals("Q1_2019")) {
			returnHtml = "webzine/oldSub";
		} else if((Integer.parseInt(wYear)==2023 && Integer.parseInt(qrt.substring(1)) >= 2) || (Integer.parseInt(wYear)>=2024)) {
			model.addAttribute("banner",webzineService.getBanner(new BannerDTO(qrtYear, "sub"+fileNo)));
			returnHtml = "webzine/newSub";
		} else {
			returnHtml = "webzine/sub";
		}
		
		
		model.addAttribute("countArticles", webzineListQY.size());
		model.addAttribute("webzineYear", webzineYear);
		model.addAttribute("webzineQrt", webzineQrt);
		model.addAttribute("webzineLink", webzineLink);
		model.addAttribute("webzineListQY", webzineListQY);
		model.addAttribute("recommandWebzineList", recommandWebzineList);
		model.addAttribute("qrtYear", qrtYear);
		model.addAttribute("sortYear", sortYear);
		model.addAttribute("qrt", qrt);
		model.addAttribute("wYear", wYear);
		model.addAttribute("num", fileNo);
		model.addAttribute("fileNo", "sub"+fileNo);
		
		return returnHtml;
	}
	
	@GetMapping(value = "/main/webzine/{qrtYear}/index.aspx")
	public String openMainWebzineIndex(@PathVariable(value = "qrtYear", required = false) String qrtYear
									, @RequestParam(required = false) SearchDTO params, Model model, HttpServletRequest request) {
		
		String wYear = "";
		String returnHtml = "";
		
		if(!qrtYear.equals("")) {
			wYear = qrtYear.substring(3, 7);
		}
		
		List<WebzineDTO> webzineYear = webzineService.getWebzineYear();
		List<WebzineDTO> webzineQrt = webzineService.getWebzineQrt();
		List<WebzineDTO> webzineLink = webzineService.getWebzineLink();
		List<WebzineDTO> webzineListQY = webzineService.getWebzineQrtYear(qrtYear);
		
		if(wYear.equals("2016") || wYear.equals("2017") || wYear.equals("2018") || qrtYear.equals("Q1_2019")) {
			returnHtml = "webzine/oldIndex";
		} else {
			returnHtml = "webzine/index";
		}
		
		model.addAttribute("webzineYear", webzineYear);
		model.addAttribute("webzineQrt", webzineQrt);
		model.addAttribute("webzineLink", webzineLink);
		model.addAttribute("webzineListQY", webzineListQY);
		model.addAttribute("qrtYear", qrtYear);
		model.addAttribute("wYear", wYear);
		model.addAttribute("fileNo", "index");

		return returnHtml;
	}
	
	@GetMapping(value = "/webzine/{qrtYear}/index.aspx")
	public String moveWebzineIndex(@PathVariable(value = "qrtYear", required = false) String qrtYear
									, @RequestParam(required = false) SearchDTO params, Model model, HttpServletRequest request) {
		
		String wYear = "";
		String returnHtml = "";
		
		if(!qrtYear.equals("")) {
			wYear = qrtYear.substring(3, 7);
		}
		
		List<WebzineDTO> webzineYear = webzineService.getWebzineYear();
		List<WebzineDTO> webzineQrt = webzineService.getWebzineQrt();
		List<WebzineDTO> webzineLink = webzineService.getWebzineLink();
		List<WebzineDTO> webzineListQY = webzineService.getWebzineQrtYear(qrtYear);
		
		if(wYear.equals("2016") || wYear.equals("2017") || wYear.equals("2018") || qrtYear.equals("Q1_2019")) {
			returnHtml = "webzine/oldIndex";
		} else {
			returnHtml = "webzine/index";
		}
		
		model.addAttribute("webzineYear", webzineYear);
		model.addAttribute("webzineQrt", webzineQrt);
		model.addAttribute("webzineLink", webzineLink);
		model.addAttribute("webzineListQY", webzineListQY);
		model.addAttribute("qrtYear", qrtYear);
		model.addAttribute("wYear", wYear);
		model.addAttribute("fileNo", "index");

		return returnHtml;
	}
	
	@GetMapping(value = "/Main/webzine/{qrtYear}/index.aspx")
	public String moveMainWebzineIndexQY(@PathVariable(value = "qrtYear", required = false) String qrtYear
									, @RequestParam(required = false) SearchDTO params, Model model, HttpServletRequest request) {
		
		String wYear = "";
		String returnHtml = "";
		
		if(!qrtYear.equals("")) {
			wYear = qrtYear.substring(3, 7);
		}
		
		List<WebzineDTO> webzineYear = webzineService.getWebzineYear();
		List<WebzineDTO> webzineQrt = webzineService.getWebzineQrt();
		List<WebzineDTO> webzineLink = webzineService.getWebzineLink();
		List<WebzineDTO> webzineListQY = webzineService.getWebzineQrtYear(qrtYear);
		
		if(wYear.equals("2016") || wYear.equals("2017") || wYear.equals("2018") || qrtYear.equals("Q1_2019")) {
			returnHtml = "webzine/oldIndex";
		} else {
			returnHtml = "webzine/index";
		}
		
		model.addAttribute("webzineYear", webzineYear);
		model.addAttribute("webzineQrt", webzineQrt);
		model.addAttribute("webzineLink", webzineLink);
		model.addAttribute("webzineListQY", webzineListQY);
		model.addAttribute("qrtYear", qrtYear);
		model.addAttribute("wYear", wYear);
		model.addAttribute("fileNo", "index");

		return returnHtml;
	}
	
	// simple path version 
	@GetMapping(value = "/webzine/{qrtYear}/index")
	public String openWebzineIndexQY2(@PathVariable(value = "qrtYear", required = false) String qrtYear
			, @RequestParam(required = false) SearchDTO params, Model model, HttpServletRequest request) {
		
		String wYear = "";
		String returnHtml = "";
		
		if(!qrtYear.equals("")) {
			wYear = qrtYear.substring(3, 7);
		}
		
		List<WebzineDTO> webzineYear = webzineService.getWebzineYear();
		List<WebzineDTO> webzineQrt = webzineService.getWebzineQrt();
		List<WebzineDTO> webzineLink = webzineService.getWebzineLink();
		List<WebzineDTO> webzineListQY = webzineService.getWebzineQrtYear(qrtYear);
		
		if(wYear.equals("2016") || wYear.equals("2017") || wYear.equals("2018") || qrtYear.equals("Q1_2019")) {
			returnHtml = "webzine/oldIndex";
		} else {
			returnHtml = "webzine/index";
		}
		
		model.addAttribute("webzineYear", webzineYear);
		model.addAttribute("webzineQrt", webzineQrt);
		model.addAttribute("webzineLink", webzineLink);
		model.addAttribute("webzineListQY", webzineListQY);
		model.addAttribute("qrtYear", qrtYear);
		model.addAttribute("wYear", wYear);
		model.addAttribute("fileNo", "index");
		
		return returnHtml;
	}
	
	@GetMapping(value = "/Main/webzine/events/event_{qrtYear}.aspx")
	public String moveMainOldWebzineEvent(@PathVariable(value = "qrtYear", required = false) String qrtYear
									, Model model, HttpServletRequest request) {
		
		String wYear = "";
		String returnHtml = "";
		
		if(!qrtYear.equals("")) {
			wYear = qrtYear.substring(3, 7);
		}
		
		List<WebzineDTO> webzineYear = webzineService.getWebzineYear();
		List<WebzineDTO> webzineQrt = webzineService.getWebzineQrt();
		List<WebzineDTO> webzineLink = webzineService.getWebzineLink();
		List<WebzineDTO> webzineListQY = webzineService.getWebzineQrtYear("Q1_2023");
		
		/*
		 * if(qrtYear.equals("Q2_2023")){ returnHtml = "webzine/event_Q2_2023"; }else {
		 * returnHtml = "webzine/oldEvent"; }
		 */
		returnHtml = "webzine/oldEvent";
		
		model.addAttribute("webzineListQY", webzineListQY);
		model.addAttribute("webzineYear", webzineYear);
		model.addAttribute("webzineQrt", webzineQrt);
		model.addAttribute("webzineLink", webzineLink);
		
		model.addAttribute("qrtYear", qrtYear);
		model.addAttribute("wYear", wYear);
		model.addAttribute("fileNo", "event");

		return returnHtml;
	}
	
	@GetMapping(value = "/webzine/events/event_{qrtYear}.aspx")
	public String moveOldWebzineEvent(@PathVariable(value = "qrtYear", required = false) String qrtYear
									, Model model, HttpServletRequest request) {
		
		String wYear = "";
		String returnHtml = "";
		
		if(!qrtYear.equals("")) {
			wYear = qrtYear.substring(3, 7);
		}
		
		List<WebzineDTO> webzineYear = webzineService.getWebzineYear();
		List<WebzineDTO> webzineQrt = webzineService.getWebzineQrt();
		List<WebzineDTO> webzineLink = webzineService.getWebzineLink();
		List<WebzineDTO> webzineListQY = webzineService.getWebzineQrtYear("Q1_2023");
		
		if(qrtYear.equals("Q2_2023")){
			returnHtml = "webzine/event_Q2_2023";
		}else {
			returnHtml = "webzine/oldEvent";
		}
		
		model.addAttribute("webzineListQY", webzineListQY);
		model.addAttribute("webzineYear", webzineYear);
		model.addAttribute("webzineQrt", webzineQrt);
		model.addAttribute("webzineLink", webzineLink);
		
		model.addAttribute("qrtYear", qrtYear);
		model.addAttribute("wYear", wYear);
		model.addAttribute("fileNo", "event");

		return returnHtml;
	}

	@GetMapping(value = "/webzine/event/{qrtYear}")
	public String openWebzineEvent(@PathVariable(value = "qrtYear", required = false) String qrtYear
									, Model model, HttpServletRequest request) {
		
		String wYear = "";
		String returnHtml = "";
		
		if(!qrtYear.equals("")) {
			wYear = qrtYear.substring(3, 7);
		}
		
		List<WebzineDTO> webzineYear = webzineService.getWebzineYear();
		List<WebzineDTO> webzineQrt = webzineService.getWebzineQrt();
		List<WebzineDTO> webzineLink = webzineService.getWebzineLink();
		List<WebzineDTO> webzineListQY = webzineService.getWebzineQrtYear("Q1_2023");
		
		if(qrtYear.equals("Q2_2023")){
			returnHtml = "webzine/event_Q2_2023";
		}else {
			returnHtml = "webzine/oldEvent";
		}
		
		model.addAttribute("webzineListQY", webzineListQY);
		model.addAttribute("webzineYear", webzineYear);
		model.addAttribute("webzineQrt", webzineQrt);
		model.addAttribute("webzineLink", webzineLink);
		
		model.addAttribute("qrtYear", qrtYear);
		model.addAttribute("wYear", wYear);
		model.addAttribute("fileNo", "event");

		return returnHtml;
	}
	
	@GetMapping(value = "/webzine/{qrtYear}/event")
	public String moveWebzineEvent(@PathVariable(value = "qrtYear", required = false) String qrtYear
									, Model model, HttpServletRequest request) {
		
		String wYear = "";
		String returnHtml = "";
		
		if(!qrtYear.equals("")) {
			wYear = qrtYear.substring(3, 7);
		}
		
		List<WebzineDTO> webzineYear = webzineService.getWebzineYear();
		List<WebzineDTO> webzineQrt = webzineService.getWebzineQrt();
		List<WebzineDTO> webzineLink = webzineService.getWebzineLink();
		List<WebzineDTO> webzineListQY = webzineService.getWebzineQrtYear("Q1_2023");
		
		if(qrtYear.equals("Q2_2023")){
			returnHtml = "webzine/event_Q2_2023";
		}else {
			returnHtml = "webzine/oldEvent";
		}
		
		model.addAttribute("webzineListQY", webzineListQY);
		model.addAttribute("webzineYear", webzineYear);
		model.addAttribute("webzineQrt", webzineQrt);
		model.addAttribute("webzineLink", webzineLink);
		
		model.addAttribute("qrtYear", qrtYear);
		model.addAttribute("wYear", wYear);
		model.addAttribute("fileNo", "event");

		return returnHtml;
	}
	
	@GetMapping(value = "/main/webzine/events/event_{qrtYear}.aspx")
	public String openWebzineEventQY(@PathVariable(value = "qrtYear", required = false) String qrtYear
									, Model model, HttpServletRequest request) {
		
		String wYear = "";
		String returnHtml = "";
		
		if(!qrtYear.equals("")) {
			wYear = qrtYear.substring(3, 7);
		}
		
		List<WebzineDTO> webzineYear = webzineService.getWebzineYear();
		List<WebzineDTO> webzineQrt = webzineService.getWebzineQrt();
		List<WebzineDTO> webzineLink = webzineService.getWebzineLink();
		List<WebzineDTO> webzineListQY = webzineService.getWebzineQrtYear("Q1_2023");
		
		if(qrtYear.equals("Q2_2023")){
			returnHtml = "webzine/event_Q2_2023";
		}else {
			returnHtml = "webzine/oldEvent";
		}
		
		model.addAttribute("webzineListQY", webzineListQY);
		model.addAttribute("webzineYear", webzineYear);
		model.addAttribute("webzineQrt", webzineQrt);
		model.addAttribute("webzineLink", webzineLink);
		
		model.addAttribute("qrtYear", qrtYear);
		model.addAttribute("wYear", wYear);
		model.addAttribute("fileNo", "event");

		return returnHtml;
	}
	
	@GetMapping(value = "/main/webzine/lastlistNew.aspx")
	public String openLastlistNew(@RequestParam(value = "year", required = false, defaultValue = "2023") String year
									, @RequestParam(required = false) SearchDTO params, Model model, HttpServletRequest request) {
		
		String returnHtml = "";
		String q1 = "0";
		String q2 = "0";
		String q3 = "0";
		String q4 = "0";
		
		List<WebzineDTO> webzineYear = webzineService.getWebzineYear();
		List<WebzineDTO> webzineQrt = webzineService.getWebzineQrt();
		List<WebzineDTO> webzineLink = webzineService.getWebzineLink();
		List<WebzineDTO> webzineListQY = webzineService.getWebzineQrtYear("Q1_2023");
		
		List<WebzineDTO> webzineQ1 = webzineService.getWebzineQ("Q1_"+year);
		if(webzineQ1.size() > 0) {
			model.addAttribute("webzineListQ1", webzineQ1);
			q1 = "1";
		}
		List<WebzineDTO> webzineQ2 = webzineService.getWebzineQ("Q2_"+year);
		if(webzineQ2.size() > 0) {
			model.addAttribute("webzineListQ2", webzineQ2);
			q2 = "1";
		}
		List<WebzineDTO> webzineQ3 = webzineService.getWebzineQ("Q3_"+year);
		if(webzineQ3.size() > 0) {
			model.addAttribute("webzineListQ3", webzineQ3);
			q3 = "1";
		}
		List<WebzineDTO> webzineQ4 = webzineService.getWebzineQ("Q4_"+year);
		if(webzineQ4.size() > 0) {
			model.addAttribute("webzineListQ4", webzineQ4);
			q4 = "1";
		}
		
		model.addAttribute("qrtYear", "Q1_2023");
		model.addAttribute("webzineListQY", webzineListQY);
		model.addAttribute("webzineYear", webzineYear);
		model.addAttribute("webzineQrt", webzineQrt);
		model.addAttribute("webzineLink", webzineLink);
		model.addAttribute("year", year);
		model.addAttribute("q1", q1);
		model.addAttribute("q2", q2);
		model.addAttribute("q3", q3);
		model.addAttribute("q4", q4);
		model.addAttribute("fileNo", "lastlistNew");

		return "webzine/lastlistNew";
	}
	
	@GetMapping(value = "/webzine/lastlistNew")
	public String moveLastlistNew(@RequestParam(value = "year", required = false, defaultValue = "2023") String year
									, @RequestParam(required = false) SearchDTO params, Model model, HttpServletRequest request) {
		
		String q1 = "0";
		String q2 = "0";
		String q3 = "0";
		String q4 = "0";
		
		List<WebzineDTO> webzineYear = webzineService.getWebzineYear();
		List<WebzineDTO> webzineQrt = webzineService.getWebzineQrt();
		List<WebzineDTO> webzineLink = webzineService.getWebzineLink();
		List<WebzineDTO> webzineListQY = webzineService.getWebzineQrtYear("Q1_2023");
		
		List<WebzineDTO> webzineQ1 = webzineService.getWebzineQ("Q1_"+year);
		if(webzineQ1.size() > 0) {
			model.addAttribute("webzineListQ1", webzineQ1);
			q1 = "1";
		}
		List<WebzineDTO> webzineQ2 = webzineService.getWebzineQ("Q2_"+year);
		if(webzineQ2.size() > 0) {
			model.addAttribute("webzineListQ2", webzineQ2);
			q2 = "1";
		}
		List<WebzineDTO> webzineQ3 = webzineService.getWebzineQ("Q3_"+year);
		if(webzineQ3.size() > 0) {
			model.addAttribute("webzineListQ3", webzineQ3);
			q3 = "1";
		}
		List<WebzineDTO> webzineQ4 = webzineService.getWebzineQ("Q4_"+year);
		if(webzineQ4.size() > 0) {
			model.addAttribute("webzineListQ4", webzineQ4);
			q4 = "1";
		}
		
		model.addAttribute("qrtYear", "Q1_2023");
		model.addAttribute("webzineListQY", webzineListQY);
		model.addAttribute("webzineYear", webzineYear);
		model.addAttribute("webzineQrt", webzineQrt);
		model.addAttribute("webzineLink", webzineLink);
		model.addAttribute("year", year);
		model.addAttribute("q1", q1);
		model.addAttribute("q2", q2);
		model.addAttribute("q3", q3);
		model.addAttribute("q4", q4);
		model.addAttribute("fileNo", "lastlistNew");

		return "webzine/lastlistNew";
	}

	@GetMapping(value = "/main/webzine/theme.aspx")
	public String moveMainTheme(@RequestParam(required = false) SearchDTO params, Model model, HttpServletRequest request) {
		
		List<WebzineDTO> webzineYear = webzineService.getWebzineYear();
		List<WebzineDTO> webzineQrt = webzineService.getWebzineQrt();
		List<WebzineDTO> webzineLink = webzineService.getWebzineLink();
		List<WebzineDTO> webzineListQY = webzineService.getWebzineQrtYear("Q1_2023");
		
		List<WebzineDTO> webzine02 = webzineService.getWebzine02();
		if(webzine02.size() > 0) {
			model.addAttribute("webzineList02", webzine02);
		}
		List<WebzineDTO> webzine03 = webzineService.getWebzine03();
		if(webzine03.size() > 0) {
			model.addAttribute("webzineList03", webzine03);
		}
		List<WebzineDTO> webzine04 = webzineService.getWebzine04();
		if(webzine04.size() > 0) {
			model.addAttribute("webzineList04", webzine04);
		}
		List<WebzineDTO> webzine05 = webzineService.getWebzine05();
		if(webzine05.size() > 0) {
			model.addAttribute("webzineList05", webzine05);
		}
		List<WebzineDTO> webzine06 = webzineService.getWebzine06();
		if(webzine06.size() > 0) {
			model.addAttribute("webzineList06", webzine06);
		}
		
		model.addAttribute("qrtYear", "Q1_2023");
		model.addAttribute("webzineYear", webzineYear);
		model.addAttribute("webzineListQY", webzineListQY);
		model.addAttribute("webzineQrt", webzineQrt);
		model.addAttribute("webzineLink", webzineLink);

		return "webzine/theme";
	}
	
	
	@RequestMapping(value = {"/webzine/theme","/webzine/theme.aspx", "/Main/webzine/theme.aspx"})
	public String moveTheme(@RequestParam(required = false) SearchDTO params, Model model, HttpServletRequest request) {
		
		List<WebzineDTO> webzineYear = webzineService.getWebzineYear();
		List<WebzineDTO> webzineQrt = webzineService.getWebzineQrt();
		List<WebzineDTO> webzineLink = webzineService.getWebzineLink();
		List<WebzineDTO> webzineListQY = webzineService.getWebzineQrtYear("Q1_2023");
		
		List<WebzineDTO> webzine02 = webzineService.getWebzine02();
		if(webzine02.size() > 0) {
			model.addAttribute("webzineList02", webzine02);
		}
		List<WebzineDTO> webzine03 = webzineService.getWebzine03();
		if(webzine03.size() > 0) {
			model.addAttribute("webzineList03", webzine03);
		}
		List<WebzineDTO> webzine04 = webzineService.getWebzine04();
		if(webzine04.size() > 0) {
			model.addAttribute("webzineList04", webzine04);
		}
		List<WebzineDTO> webzine05 = webzineService.getWebzine05();
		if(webzine05.size() > 0) {
			model.addAttribute("webzineList05", webzine05);
		}
		List<WebzineDTO> webzine06 = webzineService.getWebzine06();
		if(webzine06.size() > 0) {
			model.addAttribute("webzineList06", webzine06);
		}
		
		model.addAttribute("qrtYear", "Q1_2023");
		model.addAttribute("webzineListQY", webzineListQY);
		model.addAttribute("webzineYear", webzineYear);
		model.addAttribute("webzineQrt", webzineQrt);
		model.addAttribute("webzineLink", webzineLink);

		return "webzine/theme";
	}
	
	@GetMapping("/webzine/searchNew")
	public String getSearchNewPage(Model model) {
		
		List<WebzineDTO> webzineYear = webzineService.getWebzineYear();
		List<WebzineDTO> webzineQrt = webzineService.getWebzineQrt();
		List<WebzineDTO> webzineLink = webzineService.getWebzineLink();
		List<WebzineDTO> webzineListQY = webzineService.getWebzineQrtYear("Q1_2023");
		
		model.addAttribute("qrtYear", "Q1_2023");
		model.addAttribute("webzineListQY", webzineListQY);
		model.addAttribute("webzineQrt", webzineQrt);
		model.addAttribute("webzineLink", webzineLink);
		model.addAttribute("webzineYear", webzineYear);
		
		return "webzine/searchNew";
	}
	
	@PostMapping("/webzine/searchNew")
	public String getSearchNewList(Model model, SearchDTO params) {
		
		List<WebzineDTO> webzineYear = webzineService.getWebzineYear();
		List<WebzineDTO> webzineQrt = webzineService.getWebzineQrt();
		List<WebzineDTO> webzineLink = webzineService.getWebzineLink();
		List<WebzineDTO> webzineListQY = webzineService.getWebzineQrtYear("Q1_2023");
		
		model.addAttribute("qrtYear", "Q1_2023");
		model.addAttribute("webzineListQY", webzineListQY);
		model.addAttribute("webzineQrt", webzineQrt);
		model.addAttribute("webzineLink", webzineLink);
		model.addAttribute("webzineYear", webzineYear);
		
		List<WebzineDTO> webzineListSearch = webzineService.getWebzineSearchList(params);
		model.addAttribute("searchKeyword", params.getSearchKeyword());
		model.addAttribute("webzineListSearch", webzineListSearch);
		
		return "webzine/searchNew";
	}

	
}
