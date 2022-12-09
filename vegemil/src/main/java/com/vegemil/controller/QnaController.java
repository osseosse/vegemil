package com.vegemil.controller;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.vegemil.constant.Method;
import com.vegemil.domain.MemberDTO;
import com.vegemil.domain.QnaDTO;
import com.vegemil.service.QnaService;
import com.vegemil.util.UiUtils;

@Controller
public class QnaController extends UiUtils {

	@Autowired
	private QnaService qnaService;
	
	@Value("${spring.servlet.multipart.location}")
    private String uploadPath;
	
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
			BindingResult bindingResult, @RequestParam("sFile") MultipartFile fileName, Model model, 
			HttpServletResponse response, HttpServletRequest request, Authentication authentication) {
		
		try {
			
			if(authentication != null) {
			
				String originalName = fileName.getOriginalFilename();
				if(!"".equals(originalName)) {
					String file = originalName.substring(originalName.lastIndexOf("\\") + 1);
					String uuid = UUID.randomUUID().toString();
					String savefileName = uuid + "_" + file;
					//테스트경로
					Path savePath = Paths.get(uploadPath + "/port/" + savefileName);
					
					//저장
					fileName.transferTo(savePath);
					params.setSFile(file);
				}
			
				MemberDTO member = (MemberDTO) authentication.getPrincipal();
				if(member != null) {
		        	params.setSId(member.getMId());
		        	params.setSHp(member.getMHp());
		        	params.setSEmail(member.getMEmail());
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

}
