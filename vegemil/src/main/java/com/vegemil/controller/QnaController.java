package com.vegemil.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vegemil.constant.Method;
import com.vegemil.domain.MemberDTO;
import com.vegemil.domain.QnaDTO;
import com.vegemil.service.QnaService;
import com.vegemil.util.UiUtils;

@Controller
public class QnaController extends UiUtils {

	@Autowired
	private QnaService qnaService;
	
	@GetMapping(value = "/mypage/qna")
	public String openQnaWrite(@ModelAttribute("params") QnaDTO params, @RequestParam(value = "qId", required = false) Long qId, Model model , Authentication authentication) {
		
		String returnPage = "";
		if(authentication != null) {
			
			//Authentication 객체를 통해 유저 정보를 가져올 수 있다.
	        MemberDTO member = (MemberDTO) authentication.getPrincipal();  //userDetail 객체를 가져옴
	        if(member != null) {
	        	if(member.getMActive() != "1") {
		        	returnPage = "member/joinConfirm";
		        }
		        
		        if (qId == null) {
					model.addAttribute("qna", new QnaDTO());
				} else {
					params.setMemNo(member.getMIdx());
			        QnaDTO qna = qnaService.getQnaDetail(params);
					if (qna == null) {
						model.addAttribute("qna", new QnaDTO());
					} else {
						model.addAttribute("qna", qna);
					}
				}
		        
		        model.addAttribute("member", member);
		        returnPage = "mypage/qna";
		        
	        }
		} else {
			model.addAttribute("qna", new QnaDTO());
			returnPage = "mypage/qnaNonLogin";
		}
		
		return returnPage;
		
	}
	
	@GetMapping(value = "/mypage/answer")
	public String openQnaAnswer(@ModelAttribute("params") QnaDTO params, @RequestParam(value = "rId", required = false) Long rId, Model model, Authentication authentication) {
		
		//Authentication 객체를 통해 유저 정보를 가져올 수 있다.
        MemberDTO member = (MemberDTO) authentication.getPrincipal();  //userDetail 객체를 가져옴
        if(member != null) {
        	if(member.getMActive() != "1") {
	        	return "member/joinConfirm";
	        }
	        
	        QnaDTO qna = qnaService.getQnaDetail(params);
			if (qna == null) {
				return showMessageWithRedirect("올바르지 않은 접근입니다.", "/mypage/list", Method.GET, null, model);
			} else {
				model.addAttribute("qna", qna);
			}
	        model.addAttribute("member", member);
	        
        }
		
		return "mypage/qna";
	}

	@PostMapping(value = "/mypage/registerQna")
	public String registerQna(@ModelAttribute("params") final QnaDTO params, Model model, Authentication authentication) {
		try {
			
			if(authentication != null) {
				MemberDTO member = (MemberDTO) authentication.getPrincipal();
				if(member != null) {
					if(member.getMActive() != "1") {
			        	return "member/joinConfirm";
			        } else {
			        	params.setMemNo(member.getMIdx());
						boolean isRegistered = qnaService.registerQna(params);
						if (isRegistered == false) {
							return showMessageWithRedirect("1:1문의 등록에 실패하였습니다.", "/mypage/list", Method.GET, null, model);
						}
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
        	if(member.getMActive() != "1") {
	        	return "member/joinConfirm";
	        } else {
	        	
	        	//지원서 리스트
	    		model.addAttribute("member", member);
	    		
	    		//1:1문의 리스트
	    		QnaDTO qna = new QnaDTO();
	    		qna.setMemNo(member.getMIdx());
	    		List<QnaDTO> qnaList = qnaService.getQnaList(qna);
	    		model.addAttribute("qnaList", qnaList);
	        	
	        }
	        model.addAttribute("member", member);
        }
		

		return "mypage/list";
	}
	
	@GetMapping(value = "/adminQna")
	public String adminQnaList(@ModelAttribute("params") QnaDTO params, Model model) {
		List<QnaDTO> qnaList = qnaService.getQnaList(params);
		model.addAttribute("qnaList", qnaList);

		return "adminQna";
	}

	@GetMapping(value = "/qna/view")
	public String openQnaDetail(@ModelAttribute("params") QnaDTO params, @RequestParam(value = "rId", required = false) Long rId, Model model) {
		if (rId == null) {
			return showMessageWithRedirect("올바르지 않은 접근입니다.", "qna", Method.GET, null, model);
		}

		QnaDTO qna = qnaService.getQnaDetail(params);
		if (qna == null || "Y".equals(qna.getRDeleteYn())) {
			return showMessageWithRedirect("없는 게시글이거나 이미 삭제된 게시글입니다.", "qna", Method.GET, null, model);
		}
		model.addAttribute("qna", qna);

		return "qna/view";
	}

	@PostMapping(value = "/qna/delete")
	public String deleteQna(@ModelAttribute("params") QnaDTO params, @RequestParam(value = "idx", required = false) Long idx, Model model) {
		if (idx == null) {
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

}
