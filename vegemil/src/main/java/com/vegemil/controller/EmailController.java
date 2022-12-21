package com.vegemil.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
 import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonObject;
import com.vegemil.constant.Method;
import com.vegemil.domain.MemberDTO;
import com.vegemil.service.MemberService;
import com.vegemil.service.MailService;
import com.vegemil.util.UiUtils;

@Controller
public class EmailController extends UiUtils {

	@Autowired
	private MemberService memberService;
	
	@Autowired
	private MailService mailService;
	
	//회원 활성화
	@GetMapping(value = "/email/active")
	public String changeActive(@ModelAttribute("authToken") final String authToken, Model model, HttpServletResponse response) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		MemberDTO member = null;
		if (authToken == null) {
			out.println("<script>alert('올바르지 않은 접근입니다.'); history.go(-1);</script>");
			out.flush();
		}
		String mIdx = mailService.verifyMemberIdx(authToken);
		if (mIdx == null) {
			out.println("<script>alert('올바르지 않은 접근입니다.'); history.go(-1);</script>");
			out.flush();
		}
		member = new MemberDTO();
		member.setMIdx(Long.parseLong(mIdx));
		boolean isRegistered = memberService.activeMember(member);
		if (isRegistered == false) {
			out.println("<script>alert('이메일인증에 실패하였습니다.'); window.location='/';</script>");
			out.flush();
		}
		
		return showMessageWithRedirect("이메일 인증이 완료되었습니다.", "/member/login", Method.GET, null, model);
	}
	
	@RequestMapping(value = { "/email/sendPwResetEmailJson" }, method = { RequestMethod.POST })
	public @ResponseBody JsonObject sendPwResetMail(MemberDTO member,
														HttpServletResponse response, HttpServletRequest request) {

		JsonObject jsonObj = new JsonObject();

		try {
			
			if(member != null) {
		        
		        int mamberCount = memberService.getMemberCount(member);
		        if(mamberCount == 1) {
		        	//비밀번호 재설정 메일 발송 
		        	mailService.sendPwResetEmail(member);
		        	jsonObj.addProperty("result", true);
		        } else {
		        	jsonObj.addProperty("result", false);
		        }
				
			}
			
		} catch (DataAccessException e) {
			jsonObj.addProperty("message", "데이터베이스 처리 과정에 문제가 발생하였습니다.");

		} catch (Exception e) {
			jsonObj.addProperty("message", "시스템에 문제가 발생하였습니다.");
		}

		return jsonObj;
	}
	
	@GetMapping(value = "/email/pwReset")
	public String pwReset(@ModelAttribute("authToken") final String authToken, Model model, HttpServletResponse response) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		MemberDTO member = null;
		if (authToken == null) {
			out.println("<script>alert('올바르지 않은 접근입니다.'); history.go(-1);</script>");
			out.flush();
		}
		String mIdx = mailService.verifyMemberIdx(authToken);
		if (mIdx == null) {
			out.println("<script>alert('존재하지 않는 토큰입니다.'); history.go(-1);</script>");
			out.flush();
		} else {
			member = new MemberDTO();
			member.setMIdx(Long.parseLong(mIdx));
			model.addAttribute("member", member);
		}
		
		return "member/passwordReset";
	}
	
}
