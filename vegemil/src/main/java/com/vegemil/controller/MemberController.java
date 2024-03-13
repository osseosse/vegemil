package com.vegemil.controller;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vegemil.constant.Method;
import com.vegemil.domain.MemberDTO;
import com.vegemil.service.MailService;
import com.vegemil.service.MemberService;
import com.vegemil.util.UiUtils;

@Controller
public class MemberController extends UiUtils {

	@Autowired
	private MemberService memberService;
	
	@Autowired
	private MailService mailService;
	
	@GetMapping(value = "/member/{viewName}")
    public String moveMemberPages(@PathVariable(value = "viewName", required = false) String viewName)throws Exception{
		
		return "member/"+viewName;
    }
	
	@GetMapping(value = "/member/login")
	public String moveMemLogin(Model model, Authentication authentication) {
		
		if(authentication != null)
    	return showMessageWithRedirect("이미 로그인 상태입니다.", "/", Method.GET, null, model);
		
		return "member/login";
	}
	
	@GetMapping(value = "/member/idSearch")
	public String moveidSearch(Model model, Authentication authentication) {
		
		if(authentication != null)
    	return showMessageWithRedirect("이미 로그인 상태입니다.", "/", Method.GET, null, model);
		
		MemberDTO member = new MemberDTO();
		model.addAttribute("member", member);
		
		return "member/idSearch";
	}
	
	@GetMapping(value = "/member/pwSearch")
	public String movePwSearch(Model model, Authentication authentication) {
		
		if(authentication != null)
    	return showMessageWithRedirect("이미 로그인 상태입니다.", "/", Method.GET, null, model);
		
		MemberDTO member = new MemberDTO();
		model.addAttribute("member", member);
		
		return "member/pwSearch";
	}
	
	@GetMapping(value = "/member/payLogin")
	public String movePayLogin(Model model, Authentication authentication,
			@PathVariable(value = "viewName", required = false) String viewName) {
		
		if(authentication != null)
    	return showMessageWithRedirect("이미 로그인 상태입니다.", "/", Method.GET, null, model);
		
		return "member/payLogin";
	}
	
	@GetMapping(value = "/member/recruitList")
	public String openMemberList(@ModelAttribute("params") MemberDTO params, Model model) {
		
		List<MemberDTO> memberList = memberService.getMemberList(params);
		int memberCount = memberService.getMemberCount(params);
		
		model.addAttribute("memberList", memberList);
		model.addAttribute("memberCount", memberCount);

		return "member/list";
	}
	
	@ResponseBody
	@GetMapping(value="/member/idCheck")
	public int overlappedID(MemberDTO member) throws Exception{
		int result = memberService.overlappedID(member); // 중복확인한 값을 int로 받음
		return result;
	}
	
	@PostMapping(value = "/member/searchMemId")
	public String searchMemId(
			@ModelAttribute("member") MemberDTO member,
			Model model, HttpServletResponse response, HttpServletRequest request) throws Exception {
		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		String mId = "";
		
		try {
			
			mId = memberService.searchMemId(member);
			model.addAttribute("mId", mId);
			
		} catch (Exception e) {
			out.println("<script>alert('시스템에 문제가 발생하였습니다.'); history.go(-1);</script>");
			out.flush();
			return showMessageWithRedirect("시스템에 문제가 발생하였습니다.", "/home", Method.GET, null, model);
		}

		return "member/idSearchResult";
	}
	
	@PostMapping(value = "/member/sendPwResetEmail")
	public String sendPwResetEmail(
			@ModelAttribute("member") MemberDTO member,
			Model model, HttpServletResponse response, HttpServletRequest request) throws Exception {
		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		long mIdx;
		long mCnt;
		String mEmail = "";
		
		try {
			
			//idx 조회해와서 없으면 띵
			mCnt = memberService.getMemberCountByEmail(member);
			//해당 회원 없음
			if(mCnt == 0) {
				return showMessageWithRedirect("해당하는 회원이 존재하지 않습니다.\n아이디 또는 이메일을 다시 확인 바랍니다.", "/member/pwSearch", Method.GET, null, model);
			} else {
				//재설정메일 발송
				mIdx = memberService.getMemberIdx(member);
				member.setMIdx(mIdx);
				mEmail = mailService.sendPwResetEmail(member);
				member.setMEmail(mEmail);
			}
			model.addAttribute("member", member);
			
		} catch (DataAccessException e) {
			out.println("<script>alert('데이터베이스 처리 과정에 문제가 발생하였습니다.'); history.back();</script>");
			out.flush();
			return showMessageWithRedirect("데이터베이스 처리 과정에 문제가 발생하였습니다.", "/member/pwSearch", Method.GET, null, model);

		} catch (Exception e) {
			out.println("<script>alert('시스템에 문제가 발생하였습니다.'); history.go(-1);</script>");
			out.flush();
			return showMessageWithRedirect("시스템에 문제가 발생하였습니다.", "/member/pwSearch", Method.GET, null, model);
		}

		return "member/pwSearchResult";
	}
	
	@PostMapping(value = "/member/resetPassword")
	public String resetPassword(
			@ModelAttribute("member") MemberDTO member,
			Model model, HttpServletResponse response, HttpServletRequest request) throws Exception {
		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		try {
			
			boolean isRegistered = memberService.resetPassword(member);
			if (isRegistered == false) {
				out.println("<script>alert('비밀번호 재설정에 실패하였습니다.'); history.go(-1);</script>");
				out.flush();
			}
			
		} catch (DataAccessException e) {
			out.println("<script>alert('데이터베이스 처리 과정에 문제가 발생하였습니다.'); history.back();</script>");
			out.flush();
			return showMessageWithRedirect("데이터베이스 처리 과정에 문제가 발생하였습니다.", "/member/pwSearch", Method.GET, null, model);

		} catch (Exception e) {
			out.println("<script>alert('시스템에 문제가 발생하였습니다.'); history.go(-1);</script>");
			out.flush();
			return showMessageWithRedirect("시스템에 문제가 발생하였습니다.", "/member/pwSearch", Method.GET, null, model);
		}

		return "member/pwResetResult";
	}
	
	@RequestMapping(value="/member/join" , method = {RequestMethod.GET, RequestMethod.POST})
	public String moveJoin(Model model, HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value="step", required=false, defaultValue="1") int step) throws Exception {
		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		MemberDTO member = new MemberDTO();
		String returnHtml = "";
		
		
		//-----------------------pcc 결과값--------------------------
		String pccResult 			=	"";
		String pccNameOrg			=	"";
		String pccName 				=   "";
		String pccSex 				=	"";
		String pccBirymd 			=	"";
		String pccDi 				=	"";
		String pccCellno            =   "";
		String diKey 				=	"";
		pccResult 			=	request.getParameter("PCC_RESULT");
		
		if(pccResult != null && pccResult.equals("Y"))
		{
			step	=	2;
			pccNameOrg			=	request.getParameter("PCC_NAME");
			pccName 				=   URLDecoder.decode(pccNameOrg, "utf-8");
			pccSex 				=	request.getParameter("PCC_SEX");
			pccBirymd 			=	request.getParameter("PCC_BIRYMD");
			pccDi 				=	request.getParameter("PCC_DI");
			pccCellno            =   request.getParameter("PCC_CELLNO");
			diKey 				=	request.getParameter("diKey");
			diKey = pccDi;
		}
		//-----------------------pcc 결과값--------------------------
		
		if(step == 1) {
			//model.addAttribute("member", member);
			returnHtml = "member/agreement";
		} else if(step == 2) {
			member.setMName(pccName);
			member.setMSex(pccSex);
			member.setMYear(pccBirymd);
			member.setMHp(pccCellno);
			member.setMDi(diKey);
			model.addAttribute("member", member);
			returnHtml = "member/join";
		} else {
			out.println("<script>alert('올바르지 않은 접근입니다.'); history.go(-1);</script>");
			out.flush();
			return showMessageWithRedirect("올바르지 않은 접근입니다.", "member/agreement", Method.GET, null, model);
		}
		
		return returnHtml;
	}
	
	@RequestMapping(value="/member/joinWithEdaymall" , method = {RequestMethod.GET, RequestMethod.POST})
	public String joinWithEdaymal(Model model, HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value="step", required=false, defaultValue="1") int step) throws Exception {
		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		MemberDTO member = new MemberDTO();
		String returnHtml = "";
		
		//-----------------------pcc 결과값--------------------------
		String pccResult 			=	"";
		String pccNameOrg			=	"";
		String pccName 				=   "";
		String pccSex 				=	"";
		String pccBirymd 			=	"";
		String pccDi 				=	"";
		String pccCellno            =   "";
		String diKey 				=	"";
		pccResult 			=	request.getParameter("PCC_RESULT");
		
		if(pccResult != null && pccResult.equals("Y"))
		{
			step	=	2;
			pccNameOrg			=	request.getParameter("PCC_NAME");
			pccName 				=   URLDecoder.decode(pccNameOrg, "utf-8");
			pccSex 				=	request.getParameter("PCC_SEX");
			pccBirymd 			=	request.getParameter("PCC_BIRYMD");
			pccDi 				=	request.getParameter("PCC_DI");
			pccCellno            =   request.getParameter("PCC_CELLNO");
			diKey 				=	request.getParameter("diKey");
			diKey = pccDi;
		}
		//-----------------------pcc 결과값--------------------------
		
		if(step == 1) {
			//model.addAttribute("member", member);
			returnHtml = "member/agreement";
		} else if(step == 2) {
			member.setMName(pccName);
			member.setMSex(pccSex);
			member.setMYear(pccBirymd);
			member.setMHp(pccCellno);
			member.setMDi(diKey);
			// 동시가입 
			member.setMDualYn("1");
			
			model.addAttribute("member", member);
			returnHtml = "member/join";
		} else {
			out.println("<script>alert('올바르지 않은 접근입니다.'); history.go(-1);</script>");
			out.flush();
			return showMessageWithRedirect("올바르지 않은 접근입니다.", "member/agreement", Method.GET, null, model);
		}
		
		return returnHtml;
	}
	
	
	@PostMapping(value = "/member/register")
	public String registerMember(
			@ModelAttribute("member") final @Valid MemberDTO member,
			Model model, HttpServletResponse response, HttpServletRequest request) throws Exception {
		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		System.out.println("member. MdualYn ========> " + member.getMDualYn());
		
		try {
			
			boolean isRegistered = memberService.registerMember(member);
			if (isRegistered == false) {
				out.println("<script>alert('이미 가입된 회원입니다.'); history.go(-1);</script>");
				out.flush();
				return showMessageWithRedirect("데이터베이스 처리 과정에 문제가 발생하였습니다.", "/home", Method.GET, null, model);
			}
			model.addAttribute("member", member);
			
		} catch (DataAccessException e) {
			out.println("<script>alert('데이터베이스 처리 과정에 문제가 발생하였습니다.'); history.back();</script>");
			out.flush();
			return showMessageWithRedirect("데이터베이스 처리 과정에 문제가 발생하였습니다.", "/member/agreement", Method.GET, null, model);

		} catch (Exception e) {
			out.println("<script>alert('시스템에 문제가 발생하였습니다.'); history.go(-1);</script>");
			out.flush();
			return showMessageWithRedirect("시스템에 문제가 발생하였습니다.", "/member/agreement", Method.GET, null, model);
		}

		return "member/joinOk";
	}
	
	@GetMapping(value = "/member/detail")
	public String openMemberDetail(@ModelAttribute("params") MemberDTO params, @RequestParam(value = "mId", required = false) String mId, Model model, HttpServletResponse response) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		if (mId == null) {
			out.println("<script>alert('올바르지 않은 접근입니다.'); history.go(-1);</script>");
			out.flush();
			return showMessageWithRedirect("올바르지 않은 접근입니다.", "member/memberList", Method.GET, null, model);
		}
		MemberDTO member = memberService.loadUserByUsername(mId);
		if (member == null) {
			out.println("<script>alert('이미 종료된 채용입니다.'); history.go(-1);</script>");
			out.flush();
			return showMessageWithRedirect("이미 종료된 채용입니다.", "member/memberList", Method.GET, null, model);
		}
		model.addAttribute("member", member);

		return "member/detail";
	}
	
	@PostMapping(value = "/member/infoUpdate")
	public String updateMemberInfo(@ModelAttribute("params") final MemberDTO params, Model model, HttpServletResponse response, HttpServletRequest request) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		try {
			boolean isRegistered = memberService.registerMember(params);
			if (isRegistered == false) {
				out.println("<script>alert('게시글 수정에 실패하였습니다.'); history.go(-1);</script>");
				out.flush();
			}
		} catch (DataAccessException e) {
			out.println("<script>alert('데이터베이스 처리 과정에 문제가 발생하였습니다.'); history.go(-1);</script>");
			out.flush();
			return showMessageWithRedirect("데이터베이스 처리 과정에 문제가 발생하였습니다.", "/admin/baby/babyInfoList", Method.GET, null, model);

		} catch (Exception e) {
			out.println("<script>alert('시스템에 문제가 발생하였습니다.'); history.go(-1);</script>");
			out.flush();
			return showMessageWithRedirect("시스템에 문제가 발생하였습니다.", "/admin/baby/babyInfoList", Method.GET, null, model);
		}
		
		out.println("<script>alert('게시글 수정이 완료되었습니다.'); window.location='/admin/baby/babyInfoList';</script>");
		out.flush();

		return showMessageWithRedirect("게시글 수정이 완료되었습니다.", "/admin/baby/babyInfoList", Method.GET, null, model);
	}
	
	@GetMapping(value = "/member/infoCheck")
	public void changeCheck(@RequestParam(value = "memId", required = false) String emailAddr, @RequestParam(value = "active", required = false) String active, HttpServletResponse response) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		if (emailAddr == null) {
			out.println("<script>alert('올바르지 않은 접근입니다.'); history.go(-1);</script>");
			out.flush();
		}
		MemberDTO member = memberService.loadUserByUsername(emailAddr);
		member.setMEmail(emailAddr);
		member.setMActive(active);
		boolean isRegistered = memberService.registerMember(member);
		if (isRegistered == false) {
			out.println("<script>alert('메인화면 진열 변경에 실패하였습니다.'); window.location='/admin/baby/babyInfoList';</script>");
			out.flush();
		}
	}
	
	//휴면해제
	@GetMapping(value="/member/di")
	public String checkDi(Model model, Authentication authentication) throws Exception{
		
		MemberDTO member = new MemberDTO();
		//Authentication 객체를 통해 유저 정보를 가져올 수 있다.
		
		if(authentication != null) {
			
	        member = (MemberDTO) authentication.getPrincipal();  //userDetail 객체를 가져옴
	        
	        if("1".equals(member.getMIsIdle()) == false) {
	        	return showMessageWithRedirect("잘못된 접근입니다", "/", Method.GET, null, model);
	        }
	        
		} else {
			return showMessageWithRedirect("잘못된 접근입니다", "/", Method.GET, null, model);
		}
		
		try {
		        
	        if(memberService.wakeUpSleepMember(member.getMId())) {
	        	
	        	member = memberService.loadUserByUsername(member.getUsername()); 
	        	
	        	if(member != null) {
	        		SecurityContextHolder.getContext().setAuthentication(createNewAuthentication(authentication,member));
	        	} else {
	        		return showMessageWithRedirect("회원 조회에 실패했습니다 다시 로그인 해주세요.", "/member/logout", Method.GET, null, model);
	        	}
	        	
	        	if(member.getMDi() == null || "".equals(member.getMDi()) || member.getMDi().length() < 1) {
	        		return showMessageWithRedirect("휴면이 해제되었습니다.", "/mypage/myInfo", Method.GET, null, model);
	        	} else {
	        		return showMessageWithRedirect("휴면이 해제되었습니다.", "/", Method.GET, null, model);
	        	}
	        }
			
		} catch (Exception e) {
			e.printStackTrace();
			return showMessageWithRedirect("휴면 해제에 실패하였습니다.", "/", Method.GET, null, model);		
		}
		
		return showMessageWithRedirect("휴면 해제에 실패하였습니다.", "/", Method.GET, null, model);		
	}
	
	protected Authentication createNewAuthentication(Authentication currentAuth, MemberDTO member) {
	    UsernamePasswordAuthenticationToken newAuth = new UsernamePasswordAuthenticationToken(member, currentAuth.getCredentials(), member.getAuthorities());
	    newAuth.setDetails(currentAuth.getDetails());
	    return newAuth;
	}

}
