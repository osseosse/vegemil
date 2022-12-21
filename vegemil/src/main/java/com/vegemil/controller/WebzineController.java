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
import org.springframework.web.bind.annotation.RequestParam;

import com.vegemil.constant.Method;
import com.vegemil.domain.MemberDTO;
import com.vegemil.domain.SearchDTO;
import com.vegemil.domain.SubscribeDTO;
import com.vegemil.domain.WebzineDTO;
import com.vegemil.service.WebzineService;
import com.vegemil.util.UiUtils;

@Controller
public class WebzineController extends UiUtils {

	@Autowired
	private WebzineService webzineService;
	
	@GetMapping(value = "/main/webzine/list")
	public String openWebzineList( Model model, @RequestParam(value = "searchKeyword", required = false) String searchKeyword) {
	        	
		//List<ProductDTO> productList = productService.getProductList(searchKeyword);
		//model.addAttribute("productList", productList);
		//model.addAttribute("productCount", productList.size());

		return "webzine/list";
	}
	
	@GetMapping(value = "/main/webzine")
	public String openWebzineIndex(Model model, @RequestParam(required = false) SearchDTO params) {
		
		List<WebzineDTO> webzineList = webzineService.findAllWebzine(params);
		model.addAttribute("webzineList", webzineList);
		
		return "webzine/default";
	}
	
	@GetMapping(value = "/webzine/subscribe")
	public String openSubscribe(Model model, Authentication authentication) {
		
		SubscribeDTO subscribe = new SubscribeDTO();
		
		if(authentication != null) {
			MemberDTO member = (MemberDTO) authentication.getPrincipal();
			if(member != null) {
				model.addAttribute("member", member);
			}
		}
		
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
				return showMessageWithRedirect("데이터베이스 처리 과정에 문제가 발생하였습니다.", "/main/webzine", Method.GET, null, model);
			}
			model.addAttribute("subscribe", subscribe);
			
		} catch (DataAccessException e) {
			out.println("<script>alert('데이터베이스 처리 과정에 문제가 발생하였습니다.'); history.back();</script>");
			out.flush();
			return showMessageWithRedirect("데이터베이스 처리 과정에 문제가 발생하였습니다.", "/main/webzine", Method.GET, null, model);

		} catch (Exception e) {
			out.println("<script>alert('시스템에 문제가 발생하였습니다.'); history.go(-1);</script>");
			out.flush();
			return showMessageWithRedirect("시스템에 문제가 발생하였습니다.", "/main/webzine", Method.GET, null, model);
		}

		return "webzine/result";
	}
	
	@GetMapping(value = "/main/webzine/special/sub{fileNo}.aspx")
	public String openWebzineSpecial(Model model
								, @PathVariable(value = "fileNo", required = false) String fileNo) {
		
		model.addAttribute("fileNo", fileNo);
		
		return "webzine/special";
	}
	
	@GetMapping(value = "/main/webzine/{qrtYear}/sub{fileNo}.aspx")
	public String openWebzineSubQY(@PathVariable(value = "qrtYear", required = false) String qrtYear
									, @PathVariable(value = "fileNo", required = false) String fileNo
									, @RequestParam(required = false) SearchDTO params, Model model, HttpServletRequest request) {
		
		String wYear = "";
		String qrt = "";
		String sortYear = "";
		String returnHtml = "";
		
		if(!qrtYear.equals("")) {
			wYear = qrtYear.substring(3, 7);
			sortYear = qrtYear.substring(5, 7);
			qrt  = qrtYear.substring(0, 2);
		}
		
		List<WebzineDTO> webzineYear = webzineService.getWebzineYear();
		List<WebzineDTO> webzineQrt = webzineService.getWebzineQrt();
		List<WebzineDTO> webzineLink = webzineService.getWebzineLink();
		
		List<WebzineDTO> webzineListQY = webzineService.getWebzineQrtYear(qrtYear);
		List<WebzineDTO> recommandWebzineList = webzineService.getRecommandWebzine();
		WebzineDTO webzine = new WebzineDTO();
		webzine.setQrtYear(qrtYear);
		webzine.setFileNo("sub"+fileNo);
		webzine = webzineService.getWebzine(webzine);
		
		if(webzine != null) {
			model.addAttribute("title", webzine.getTitle());
			model.addAttribute("snsTitle", webzine.getSnsTitle());
		}
		
		if(wYear.equals("2017") || wYear.equals("2018") || qrtYear.equals("Q1_2019")) {
			returnHtml = "webzine/oldSub";
		} else {
			returnHtml = "webzine/sub";
		}
		
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
	public String openWebzineIndexQY(@PathVariable(value = "qrtYear", required = false) String qrtYear
									, @RequestParam(required = false) SearchDTO params, Model model, HttpServletRequest request) {
		
		String wYear = "";
		String returnHtml = "";
		
		if(!qrtYear.equals("")) {
			wYear = qrtYear.substring(3, 7);
		}
		
		List<WebzineDTO> webzineYear = webzineService.getWebzineYear();
		List<WebzineDTO> webzineQrt = webzineService.getWebzineQrt();
		List<WebzineDTO> webzineLink = webzineService.getWebzineLink();
		
		if(wYear.equals("2017") || wYear.equals("2018") || qrtYear.equals("Q1_2019")) {
			returnHtml = "webzine/oldIndex";
		} else {
			returnHtml = "webzine/index";
		}
		
		model.addAttribute("webzineYear", webzineYear);
		model.addAttribute("webzineQrt", webzineQrt);
		model.addAttribute("webzineLink", webzineLink);
		
		model.addAttribute("qrtYear", qrtYear);
		model.addAttribute("wYear", wYear);

		return returnHtml;
	}
	
	@GetMapping(value = "/main/webzine/lastlistNew.aspx")
	public String openLastlistNew(@RequestParam(value = "year", required = false, defaultValue = "2022") String year
									, @RequestParam(required = false) SearchDTO params, Model model, HttpServletRequest request) {
		
		String returnHtml = "";
		String q1 = "0";
		String q2 = "0";
		String q3 = "0";
		String q4 = "0";
		
		List<WebzineDTO> webzineYear = webzineService.getWebzineYear();
		List<WebzineDTO> webzineQrt = webzineService.getWebzineQrt();
		List<WebzineDTO> webzineLink = webzineService.getWebzineLink();
		
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
		
		model.addAttribute("webzineYear", webzineYear);
		model.addAttribute("webzineQrt", webzineQrt);
		model.addAttribute("webzineLink", webzineLink);
		model.addAttribute("year", year);
		model.addAttribute("q1", q1);
		model.addAttribute("q2", q2);
		model.addAttribute("q3", q3);
		model.addAttribute("q4", q4);

		return "webzine/lastlistNew";
	}

	@GetMapping(value = "/main/webzine/theme.aspx")
	public String openLastlistNew(@RequestParam(required = false) SearchDTO params, Model model, HttpServletRequest request) {
		
		String returnHtml = "";
		
		List<WebzineDTO> webzineYear = webzineService.getWebzineYear();
		List<WebzineDTO> webzineQrt = webzineService.getWebzineQrt();
		List<WebzineDTO> webzineLink = webzineService.getWebzineLink();
		
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
		
		model.addAttribute("webzineYear", webzineYear);
		model.addAttribute("webzineQrt", webzineQrt);
		model.addAttribute("webzineLink", webzineLink);

		return "webzine/theme";
	}
	
}
