package com.vegemil.controller;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.vegemil.constant.Method;
import com.vegemil.domain.MemberDTO;
import com.vegemil.domain.QnaDTO;
import com.vegemil.service.MemberService;
import com.vegemil.service.QnaService;
import com.vegemil.util.UiUtils;

@Controller
public class MypageController extends UiUtils {

	@Autowired
	private QnaService qnaService;
	
	@Autowired
	private MemberService memberService;
	
	//@Value("${spring.servlet.multipart.location}")
    //private String uploadPath;
	
	@GetMapping(value = "/mypage/qna")
	public String openQnaWrite(@ModelAttribute("params") QnaDTO params, @RequestParam(value = "sIdx", required = false) Long sIdx, Model model , Authentication authentication) {
		
		String returnPage = "";
		if(authentication != null) {
			
			//Authentication 객체를 통해 유저 정보를 가져올 수 있다.
	        MemberDTO member = (MemberDTO) authentication.getPrincipal();  //userDetail 객체를 가져옴
	        if(member != null) {
		        
		        if (sIdx == null) {
					model.addAttribute("qna", new QnaDTO());
				} else {
					params.setSId(member.getMId());
			        QnaDTO qna = qnaService.getQnaDetail(params);
					if (qna == null) {
						model.addAttribute("qna", new QnaDTO());
					} else {
						model.addAttribute("qna", qna);
					}
				}
		        
		        model.addAttribute("member", member);
		        returnPage = "member/qna";
		        
	        }
		} else {
			model.addAttribute("qna", new QnaDTO());
			returnPage = "member/qnaNonLogin";
		}
		
		return returnPage;
		
	}
	
	@GetMapping(value = "/mypage/answer")
	public String openQnaAnswer(@ModelAttribute("params") QnaDTO params, @RequestParam(value = "sIdx", required = false) Long sIdx, Model model, Authentication authentication) {
		
		if (sIdx == null) {
			return showMessageWithRedirect("올바르지 않은 접근입니다.", "/mypage/list", Method.GET, null, model);
		}
		
		//Authentication 객체를 통해 유저 정보를 가져올 수 있다.
        MemberDTO member = (MemberDTO) authentication.getPrincipal();  //userDetail 객체를 가져옴
        if(member != null) {
	        
    		params.setSIdx(sIdx);
    		params.setSId(member.getMId());
	        QnaDTO qna = qnaService.getQnaDetail(params);
			if (qna == null) {
				return showMessageWithRedirect("올바르지 않은 접근입니다.", "/mypage/list", Method.GET, null, model);
			} else {
				List<QnaDTO> qnaList = qnaService.getQnaList(qna);
	    		model.addAttribute("qnaCount", qnaList.size());
				model.addAttribute("qna", qna);
			}
	        model.addAttribute("member", member);
	        
        }
		
		return "member/answer";
	}

	@PostMapping(value = "/mypage/registerQna")
	public String registerQna(@ModelAttribute("params") final QnaDTO params,
			//BindingResult bindingResult, @RequestParam("fileName") MultipartFile fileName, Model model,
			BindingResult bindingResult, Model model,
			HttpServletResponse response, HttpServletRequest request, Authentication authentication) {
		
		try {
			
			if(authentication != null) {
			
				/*
				String originalName = fileName.getOriginalFilename();
				if(!"".equals(originalName)) {
					String file = originalName.substring(originalName.lastIndexOf("\\") + 1);
					String uuid = UUID.randomUUID().toString();
					String savefileName = uuid + "_" + file;
					//테스트경로
					//Path savePath = Paths.get(uploadPath + "/qna/" + savefileName);
					Path savePath = Paths.get("D:\\upload\\/qna/" + savefileName);
					
					//저장
					fileName.transferTo(savePath);
					params.setSFile(file);
				}
				*/
			
				MemberDTO member = (MemberDTO) authentication.getPrincipal();
				if(member != null) {
		        	params.setSId(member.getMId());
		        	params.setSHp(member.getMHp());
		        	params.setSName(member.getMName());
		        	params.setSEmail(member.getMEmail());
		        	params.setSAddr(member.getMAddr1()+member.getMAddr2());
					boolean isRegistered = qnaService.registerQna(params);
					if (isRegistered == false) {
						return showMessageWithRedirect("1:1문의 등록에 실패하였습니다.", "/mypage/list", Method.GET, null, model);
					}
				} else {
					return showMessageWithRedirect("시스템에 문제가 발생하였습니다.", "/mypage/list", Method.GET, null, model);
				}
			} else {
				//비회원일 때 저장
			}
		
		} catch (DataAccessException e) {
			return showMessageWithRedirect("데이터베이스 처리 과정에 문제가 발생하였습니다.", "/mypage/list", Method.GET, null, model);

		} catch (Exception e) {
			return showMessageWithRedirect("시스템에 문제가 발생하였습니다.", "/mypage/list", Method.GET, null, model);
		}

		return showMessageWithRedirect("1:1문의 등록이 완료되었습니다.", "/mypage/list", Method.GET, null, model);
	}

	@GetMapping(value = "/mypage/list")
	public String openQnaList( Model model, Authentication authentication) {
		
		//Authentication 객체를 통해 유저 정보를 가져올 수 있다.
        MemberDTO member = (MemberDTO) authentication.getPrincipal();  //userDetail 객체를 가져옴
        
        if(member != null) {
	        	
        	//지원서 리스트
    		model.addAttribute("member", member);
    		
    		//1:1문의 리스트
    		QnaDTO qna = new QnaDTO();
    		qna.setSId(member.getMId());
    		List<QnaDTO> qnaList = qnaService.getQnaList(qna);
    		model.addAttribute("qnaList", qnaList);
    		model.addAttribute("qnaCount", qnaList.size());
	        model.addAttribute("member", member);
        }
		

		return "member/list";
	}
	
	@GetMapping(value = "/mypage/withdrawal")
	public String moveWithdrawal(@ModelAttribute("params") QnaDTO params, @RequestParam(value = "mIdx", required = false) Long mIdx, Model model , Authentication authentication) {
		
		if(authentication != null) {
			
			//Authentication 객체를 통해 유저 정보를 가져올 수 있다.
	        MemberDTO member = (MemberDTO) authentication.getPrincipal();  //userDetail 객체를 가져옴
	        if(member != null) {
		        model.addAttribute("member", member);
	        }
		} else {
			return showMessageWithRedirect("로그인 후 이용바랍니다.", "/mypage/list", Method.GET, null, model);
		}
		
		return "member/withdrawal";
		
	}
	
	@PostMapping(value = "/qna/delete")
	public String deleteQna(@ModelAttribute("params") QnaDTO params, @RequestParam(value = "sIdx", required = false) Long sIdx, Model model) {
		if (sIdx == null) {
			return showMessageWithRedirect("올바르지 않은 접근입니다.", "/adminQna", Method.GET, null, model);
		}

		Map<String, Object> pagingParams = getPagingParams(params);
		try {
			boolean isDeleted = qnaService.deleteQna(params);
			if (isDeleted == false) {
				return showMessageWithRedirect("게시글 삭제에 실패하였습니다.", "/adminQna", Method.GET, pagingParams, model);
			}
		} catch (DataAccessException e) {
			return showMessageWithRedirect("데이터베이스 처리 과정에 문제가 발생하였습니다.", "/adminQna", Method.GET, pagingParams, model);

		} catch (Exception e) {
			return showMessageWithRedirect("시스템에 문제가 발생하였습니다.", "/adminQna", Method.GET, pagingParams, model);
		}

		return showMessageWithRedirect("게시글 삭제가 완료되었습니다.", "/adminQna", Method.GET, pagingParams, model);
	}
	
	@RequestMapping(value="/mypage/myInfo" , method = {RequestMethod.GET, RequestMethod.POST})
	public String moveJoin(Model model, HttpServletRequest request, HttpServletResponse response
			, Authentication authentication, @RequestParam(value="step", required=false, defaultValue="1") int step) throws Exception {
		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		MemberDTO member = new MemberDTO();
		
		try {
			
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
			
			member = (MemberDTO) authentication.getPrincipal();  //userDetail 객체를 가져옴
			if(step == 1) {
				diKey = memberService.getDiKey(member.getMIdx());
				model.addAttribute("mDi", diKey);
				model.addAttribute("step", 1);
			} else if(step == 2) {
				member.setMName(pccName);
				member.setMSex(pccSex);
				member.setMYear(pccBirymd);
				member.setMHp(pccCellno);
				member.setMDi(diKey);
				model.addAttribute("step", 2);
			} else {
				out.println("<script>alert('올바르지 않은 접근입니다.'); history.go(-1);</script>");
				out.flush();
				return showMessageWithRedirect("올바르지 않은 접근입니다.", "/mypage/list", Method.GET, null, model);
			}
			
			model.addAttribute("member", member);
			
		} catch (Exception e) {
			return showMessageWithRedirect("시스템에 문제가 발생하였습니다.", "/", Method.GET, null, model);
		}
		
		return "member/myInfo";
	}
	
	@PostMapping(value = "/mypage/update")
	public String updateMember(
			@ModelAttribute("member") final @Valid MemberDTO member,
			Model model, HttpServletResponse response, HttpServletRequest request) throws Exception {
		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		try {
			
			boolean isRegistered = memberService.registerMember(member);
			if (isRegistered == false) {
				out.println("<script>alert('수정에 실패했습니다.'); history.go(-1);</script>");
				out.flush();
				return showMessageWithRedirect("데이터베이스 처리 과정에 문제가 발생하였습니다.", "/home", Method.GET, null, model);
			}
			model.addAttribute("member", member);
			
		} catch (DataAccessException e) {
			out.println("<script>alert('데이터베이스 처리 과정에 문제가 발생하였습니다.'); history.back();</script>");
			out.flush();
			return showMessageWithRedirect("데이터베이스 처리 과정에 문제가 발생하였습니다.", "/mypage/list", Method.GET, null, model);

		} catch (Exception e) {
			out.println("<script>alert('시스템에 문제가 발생하였습니다.'); history.go(-1);</script>");
			out.flush();
			return showMessageWithRedirect("시스템에 문제가 발생하였습니다.", "/mypage/list", Method.GET, null, model);
		}

		return showMessageWithRedirect("정보 수정이 완료되었습니다.", "/mypage/myInfo", Method.GET, null, model);
	}
	
	@PostMapping(value = "/member/withdrawal")
	public String withdrawalMember(@ModelAttribute("params") MemberDTO member, Model model, Authentication authentication) {

		try {
			boolean isDeleted = memberService.withdrawalMember(member);
			if (isDeleted == false) {
				return showMessageWithRedirect("탈퇴 실패하였습니다.", "/mypage/list", Method.GET, null, model);
			}
		} catch (DataAccessException e) {
			return showMessageWithRedirect("데이터베이스 처리 과정에 문제가 발생하였습니다.", "/mypage/list", Method.GET, null, model);

		} catch (Exception e) {
			return showMessageWithRedirect("시스템에 문제가 발생하였습니다.", "/mypage/list", Method.GET, null, model);
		}

		//탈퇴 완료후 로그아웃처리
		return showMessageWithRedirect("탈퇴 완료되었습니다.", "/member/logout", Method.GET, null, model);
	}

}