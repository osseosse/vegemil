package com.vegemil.util;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import com.vegemil.constant.Method;
import com.vegemil.domain.MemberDTO;
import com.vegemil.paging.Criteria;
import com.vegemil.service.MemberService;

@Controller
public class UiUtils {
	
	@Autowired
	private MemberService memberService;
	
	public String showMessageWithRedirect(@RequestParam(value = "message", required = false) String message,
										  @RequestParam(value = "redirectUri", required = false) String redirectUri,
										  @RequestParam(value = "method", required = false) Method method,
										  @RequestParam(value = "params", required = false) Map<String, Object> params, Model model) {

		model.addAttribute("message", message);
		model.addAttribute("redirectUri", redirectUri);
		model.addAttribute("method", method);
		model.addAttribute("params", params);

		return "utils/message-redirect";
	}

	public Map<String, Object> getPagingParams(Criteria criteria) {

		Map<String, Object> params = new LinkedHashMap<>();
		params.put("currentPageNo", criteria.getCurrentPageNo());
		params.put("recordsPerPage", criteria.getRecordsPerPage());
		params.put("pageSize", criteria.getPageSize());
		params.put("searchType", criteria.getSearchType());
		params.put("searchKeyword", criteria.getSearchKeyword());

		return params;
	}
	
	//클라이언트 ip주소 가져오기
	public String getClientIp(HttpServletRequest req) {
	    String ip = "";
	    try {
			byte[] bytes = Inet4Address.getLocalHost().getAddress();
			ip = new String(bytes);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	    return ip;
	}
	

}
