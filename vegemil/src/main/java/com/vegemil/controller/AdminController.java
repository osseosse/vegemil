package com.vegemil.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.vegemil.constant.Method;
import com.vegemil.domain.MemberDTO;
import com.vegemil.service.AdminService;
import com.vegemil.service.MailService;
import com.vegemil.util.UiUtils;

@Controller
public class AdminController extends UiUtils {
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private MailService mailService;

	@RequestMapping(value = "/admin/index")
	public String adminIndex(HttpServletRequest req, Model model) {
		
		// View attribute
	    return "admin/index";
	}
	
	@RequestMapping(value = "/admin/auth/{viewName}")
    public String adminAuth(@PathVariable(value = "viewName", required = false) String viewName, HttpServletRequest req, Model model)throws Exception{
		
		
		return "admin/auth/"+viewName;
    }
	
	@RequestMapping(value = "/admin/account/{viewName}")
    public String adminAccount(@PathVariable(value = "viewName", required = false) String viewName, HttpServletRequest req, Model model)throws Exception{
		
		HttpSession session = req.getSession();
		if(viewName.equals("settings")) {
			MemberDTO admin = (MemberDTO) session.getAttribute("admin");
			admin = adminService.getAdmin(admin.getMId());
			if(admin != null) {
				model.addAttribute("admin", admin);
			}
		}
		
		return "admin/account/"+viewName;
    }
	
	@GetMapping("/admin/auth/login")
	public String adminLogin(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "exception", required = false) String exception,
			Model model) {
		model.addAttribute("error", error);
		model.addAttribute("exception", exception);
		return "admin/auth/login";
    }
	
    // 회원가입 처리
    @PostMapping("/admin/auth/signUp")
    public String adminSignup(MemberDTO params, Model model) {
    	
    	try {
	    	
	    	int result = adminService.joinAdmin(params);
			if (result == 0) {
				return showMessageWithRedirect("이미 등록된 아이디입니다.", "/admin/auth/register", Method.GET, null, model);
			}else if(result == 2) {
				return showMessageWithRedirect("회원 가입 실패하였습니다.", "/admin/auth/register", Method.GET, null, model);
			}
			
			mailService.mailSend(params);
			
    	} catch (DataAccessException e) {
			return showMessageWithRedirect("데이터베이스 처리 과정에 문제가 발생하였습니다.", "/admin/auth/register", Method.GET, null, model);

		} catch (Exception e) {
			return showMessageWithRedirect("시스템에 문제가 발생하였습니다.", "/admin/auth/register", Method.GET, null, model);
		}
    	
    	return showMessageWithRedirect("가입이 완료되었습니다.\n정보전산실 승인 이후 사용이 가능합니다.", "/admin/auth/login", Method.GET, null, model);

    }
    
    @PostMapping("/admin/auth/searchPwdProc")
    public String searchPwdProc(MemberDTO params, Model model, Authentication authentication) {
    	
    	boolean result;
    	
    	try {
    		result = adminService.initPwd(params);
    		if(!result) {
    			return showMessageWithRedirect("다시 확인해주세요.", "admin/auth/resetPassword", Method.GET, null, model);
    		}
    	}catch(Exception e) {
    		return showMessageWithRedirect("시스템에 문제가 발생하였습니다.", "/admin/auth/register", Method.GET, null, model);
    	}
        return showMessageWithRedirect("초기화되었습니다. - abcd1234 \n" , "/admin/auth/login", Method.GET, null, model);
    }

    // 로그아웃 결과 페이지
    @GetMapping("/admin/logout")
    public String adminLogout(HttpServletRequest req, Model model) {
    	
    	HttpSession session = req.getSession();
    	session.setAttribute("admin", null);
    	return showMessageWithRedirect("로그아웃 되었습니다.\n로그인 페이지로 이동 합니다.", "/admin/auth/login", Method.GET, null, model);
    	
    }
    
    @PostMapping("/admin/passChange" )
	public String adminPassChange(MemberDTO params, Model model , HttpServletRequest req, RedirectAttributes rttr 
			,Authentication authentication) {
		
		try {
			MemberDTO loginUser = (MemberDTO) authentication.getPrincipal();  //userDetail 객체를 가져옴
			params.setMId(loginUser.getMId());
			
			boolean isUpdated = adminService.changePwd(params);
			
			if(!isUpdated) {
				return showMessageWithRedirect("비밀번호가 일치하지 않습니다.", "/admin/auth/resetPassword", Method.GET, null, model);
			}
			
		} catch (DataAccessException e) {
			return showMessageWithRedirect("데이터베이스 처리 과정에 문제가 발생하였습니다.", "/admin/auth/login", Method.GET, null, model);

		} catch (Exception e) {
			return showMessageWithRedirect("시스템에 문제가 발생하였습니다.", "/admin/auth/login", Method.GET, null, model);
		}
		
		return showMessageWithRedirect("변경되었습니다.", "/admin/manage/customer/faq", Method.GET, null, model);
    }
    
    //회원 활성화
  	@GetMapping(value = "/admin/email/active")
  	public void changeActive(@RequestParam(value="mId", required=true) String mId, 
  			@RequestParam(value="mName", required=true) String mName, Model model, HttpServletResponse response) throws Exception {
  		response.setContentType("text/html; charset=UTF-8");
  		PrintWriter out = response.getWriter();
  		MemberDTO params = new MemberDTO();
  		if (mId == null || mName == null) {
  			out.println("<script>alert('올바르지 않은 접근입니다.'); history.go(-1);</script>");
  			out.flush();
  		}
  		params.setMId(mId);
  		params.setMName(mName);
  		
  		boolean isActived = mailService.verifyEmail(params);
  		if (!isActived) {
  			out.println("<script>alert('올바르지 않은 접근입니다.'); history.go(-1);</script>");
  			out.flush();
  		}
  		boolean isRegistered = adminService.activeMember(params);
  		if (isRegistered == false) {
  			out.println("<script>alert('이메일인증에 실패하였습니다.'); window.location='/';</script>");
  			out.flush();
  		}
  		
  		out.println("<script>alert('이메일 인증이 완료되었습니다.'); window.close();</script>");
		out.flush();
  	}
  	

}
