package com.vegemil.controller;

import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import com.mysql.cj.log.Log;
import com.vegemil.constant.Method;
import com.vegemil.domain.CMRespDto;
import com.vegemil.domain.MemberDTO;
import com.vegemil.domain.vegemilBaby.VegemilBabyBestReviewDTO;
import com.vegemil.domain.vegemilBaby.VegemilBabyCalendarModelDTO;
import com.vegemil.domain.vegemilBaby.VegemilBabySampleDTO;
import com.vegemil.domain.vegemilBaby.VegemilBabySearchDTO;
import com.vegemil.paging.BoardListSearchDTO;
import com.vegemil.service.vegemilBaby.VegemilBabyCommunityService;
import com.vegemil.util.UiUtils;


@CrossOrigin(origins="*", allowedHeaders = "*")
@Controller
public class VegemilBabyController extends UiUtils {

	@Autowired
	private VegemilBabyCommunityService vegemilBabyCommunityService;
	
	@Value("${spring.servlet.multipart.location}")
    private String uploadPath;

	@RequestMapping(value = "/vegemilBaby/{viewName}")
	public String moveVegemilBabyPage(@PathVariable(value = "viewName", required = false) String viewName)
			throws Exception {
		return "vegemilBaby/" + viewName;
	}
	
	@GetMapping("/Main/brandVegemilBaby/{vegemilBabyAspx}")
	public String vegemilBabyRedirect(@PathVariable("vegemilBabyAspx") String vegemilBabyAspx) {
		if(vegemilBabyAspx.contains(".")) {
			vegemilBabyAspx = vegemilBabyAspx.substring(0, vegemilBabyAspx.lastIndexOf("."));
		}
		return "vegemilBaby/" + vegemilBabyAspx;
	}
	
	@GetMapping("/vegemilBaby/index")
	public String index(Model model) {
		model.addAttribute("magazineList", vegemilBabyCommunityService.selectMagazineIndex());
		model.addAttribute("qnaList", vegemilBabyCommunityService.selectQnAIndex());
		return "vegemilBaby/index";
	}	
	/* ======== Brand ======== */
	@GetMapping(value = "/vegemilBaby/tv")
	public String moveTvPage(Model model) {		
		model.addAttribute("tvcfList",vegemilBabyCommunityService.selectTvCf());		 
		return "vegemilBaby/tv";
	}	
	
	/* ======== Community ======== */
	@RequestMapping(value="/vegemilBaby/magazine")
	public String moveMagazineList(@ModelAttribute("params") final VegemilBabySearchDTO params, Model model) {
		
		  model.addAttribute("magazineList",vegemilBabyCommunityService.selectMagazine(params));
		  model.addAttribute("categoryCount", vegemilBabyCommunityService.selectCategoryCount());
		return "vegemilBaby/magazine";
	}	
	
	@RequestMapping(value = "/main/brandVegemilBaby/e_magazine.aspx")
    public String moveOldGreenbiaPage(@PathVariable(value = "viewName", required = false) String viewName)throws Exception{
		
		return "redirect:/vegemilBaby/magazine?category=all";
    }
	
	@GetMapping("/Main/brandVegemilBaby/e_magazine.aspx")
	public String moveNewMagazineList(Model model) {
		
		VegemilBabySearchDTO params = new VegemilBabySearchDTO();
		params.setCategory("all");
		model.addAttribute("magazineList",vegemilBabyCommunityService.selectMagazine(params));
		model.addAttribute("categoryCount", vegemilBabyCommunityService.selectCategoryCount());
		 
		return "vegemilBaby/magazine";
	}	
	// 육아정보 상세
	@GetMapping(value = { "/vegemilBaby/magazine/detail/{idx}" })
	public String moveMagazineDetail(@PathVariable("idx") Long idx, Model model) {
		model.addAttribute("magazineDetail", vegemilBabyCommunityService.selectMagazineDetail(idx));
		model.addAttribute("categoryCount", vegemilBabyCommunityService.selectCategoryCount());
		return "vegemilBaby/magazineDetail";
	}
	//육아상담 QnA
	@GetMapping("/vegemilBaby/qna")
	public String moveQnaList(@ModelAttribute("params") final VegemilBabySearchDTO params, Model model) {
		model.addAttribute("qnaList",vegemilBabyCommunityService.selectQna(params));			
		return "vegemilBaby/qna";
	}	
	//육아상담  QnA상세
	@GetMapping(value = { "/vegemilBaby/qna/detail/{idx}"})
	public String moveQnaDetail(@PathVariable("idx") Long idx, Model model) {
		model.addAttribute("qnaDetail", vegemilBabyCommunityService.selectQnaDetail(idx));		
		model.addAttribute("qnaList",vegemilBabyCommunityService.selectQnaList());				 
		return "vegemilBaby/qnaDetail";				
	}	

	// 영유아식 레시피
	@GetMapping("/vegemilBaby/recipe")
	public String moveRecipeList(Model model) {
		model.addAttribute("recipeList", vegemilBabyCommunityService.selectRecipeList());
		return "vegemilBaby/recipe";
	}
	// 영유아식 레시피 상세
	@GetMapping("/vegemilBaby/recipe/detail/{idx}")
	public String moveRecipeDetail(@PathVariable("idx") Long idx, Model model) {
		model.addAttribute("recipeList", vegemilBabyCommunityService.selectRecipeList());
		model.addAttribute("recipe", vegemilBabyCommunityService.selectRecipeDetail(idx));
		return "vegemilBaby/recipeDetail";
	}
	
	/* ======== Event ======== */
	//진행 중 이벤트
	@RequestMapping(value={"/vegemilBaby/bv_event","/main/brandVegemilBaby/reviewInfo.aspx"})
	public String moveBvEvent(Model model) {
		model.addAttribute("eventList", vegemilBabyCommunityService.selectEventList());
		return "vegemilBaby/bv_event";
	}	
	//사랑의 온도계 상세페이지 이동
	@RequestMapping(value = "/vegemilBaby/event/loveVegemil2022") 
	public String moveVegemilBabyEventPage(@PathVariable(value = "viewName", required = false) String viewName, Model model
			) throws Exception { 
		
		model.addAttribute("temperature", vegemilBabyCommunityService.selectTemperature());
		return "vegemilBaby/event/loveVegemil2022";	
	}

	//후기 이벤트  신청 페이지
	@GetMapping("/vegemilBaby/review/form")
	public String moveReviewForm(Principal principal, Model model) {
		int beginIdx = principal.toString().indexOf("mId=");
		int EndIdx = principal.toString().indexOf(", mName");
		String loggedId = principal.toString().substring(beginIdx+4, EndIdx);
		
		model.addAttribute("reviewList", vegemilBabyCommunityService.selectReviewList(loggedId));
		model.addAttribute("loggedId", principal.toString().substring(beginIdx+4, EndIdx));		
		
		return "vegemilBaby/reviewForm";
	}
	
	@PostMapping("/vegemil/review/apply")
	public String submitReviewForm(@ModelAttribute("review") @Valid VegemilBabyBestReviewDTO review,
									BindingResult bindingResult,
									HttpServletRequest request, Model model, HttpServletResponse response) throws Exception {
		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();		
		try {
			vegemilBabyCommunityService.insertReviewEvent(review, response);
			
		}catch (DataAccessException e) {
			out.println("<script>alert('데이터베이스 처리 과정에 문제가 발생하였습니다.'); history.back();</script>");
			out.flush();			
			return showMessageWithRedirect("데이터베이스 처리 과정에 문제가 발생하였습니다.", "/vegemilBaby/event_review_best", Method.GET, null, model);			
		} catch (Exception e) {			
			System.out.println(e);
			System.out.println(e.getMessage());
			out.println("<script>alert('시스템에 문제가 발생하였습니다.'); history.go(-1);</script>");
			out.flush();
			return showMessageWithRedirect("시스템에 문제가 발생하였습니다.", "/vegemilBaby/event_review_best", Method.GET, null, model);
		}		
		
		return showMessageWithRedirect("후기 이벤트 참여가 완료되었습니다.", "/vegemilBaby/event_review_best", Method.GET, null, model);
	}
	
	//베스트후기 페이지
	@GetMapping("/vegemilBaby/event_best_review")
	public String moveBestReviewPage(Model model) {
		model.addAttribute("bestReviewList", vegemilBabyCommunityService.selectBestReviewList());
		return "vegemilBaby/event_best_review";
	}
		
	
	//달력아기모델 
	@GetMapping("/vegemilBaby/event/model")
	public String moveEventModelPage(Model model) {
//		model.addAttribute("modelList", vegemilBabyCommunityService.selectModelList());		
		model.addAttribute("titleList", vegemilBabyCommunityService.selectCalenderModelTitle());		
		return "vegemilBaby/event_model";
	}
	
	@GetMapping("/api2/vegemilBaby/event/model")
	public  ResponseEntity moveEventModelPage2(BoardListSearchDTO boardListSearchDTO) {
			
		List<VegemilBabyCalendarModelDTO> modelList = vegemilBabyCommunityService.selectModelList();
        
        return new ResponseEntity<>(new CMRespDto<>(1, "성공", modelList), HttpStatus.OK);
	}
	
	//달력아기모델 신청 페이지
	@GetMapping("/vegemilBaby/model/form")
	public String moveModelForm(Authentication authentication, Model model) {
		
		MemberDTO member = new MemberDTO();
		VegemilBabyCalendarModelDTO calModel = new VegemilBabyCalendarModelDTO();
		
		if(authentication == null) {
			member.setMName("");
			member.setMHp("");
			member.setMEmail("");
		} else {
			member = (MemberDTO) authentication.getPrincipal();
			if("1".equals(member.getMIsIdle())){
	        	return showMessageWithRedirect("고객님은 휴면 회원입니다. 휴면 해제 페이지로 이동합니다.", "/member/wakeUp", Method.GET, null, model);
	        }
		}
        model.addAttribute("member", member);
        model.addAttribute("model", calModel);
        
		return "vegemilBaby/modelForm";		
	}
	
	//달력아기모델 등록	
	@PostMapping("/vegemilBaby/model/apply")
	public String submitModelForm(@ModelAttribute("model") VegemilBabyCalendarModelDTO calModel,
							BindingResult bindingResult,
							HttpServletRequest request, Model model, HttpServletResponse response) throws Exception {
		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		if(vegemilBabyCommunityService.isDuple(calModel)) {
			return showMessageWithRedirect( calModel.getCName()+"("+ calModel.getCEmail()+")님의 자녀 "					   
					+ calModel.getCBabyName() +" 님은 최근 접수한 이력이 있습니다.", "/vegemilBaby/event_model", Method.GET, null, model);
		}
		
		// valid extensions
		String extensionList = "jpg, jpeg, png, gif";
		
		try {
			String uuid = UUID.randomUUID().toString();
			
			String originalName1 = calModel.getFileName1().getOriginalFilename();
			String originalName2 = calModel.getFileName2().getOriginalFilename();
			String fileExtension2 = "";
			
			
			//확장자 분리 - for check		
			String fileExtension1 = StringUtils.getFilenameExtension(originalName1);
			
			// 옵션데이터인 두번째 이미지 부터 체크 
			if(StringUtils.hasText(originalName2)) {
				 fileExtension2 = StringUtils.getFilenameExtension(originalName2);
				 if(extensionList.contains(fileExtension2.toLowerCase()) == false) {
					 return showMessageWithRedirect("이미지 형식만 업로드 가능합니다", "/vegemilBaby/model/apply", Method.GET, null, model);
				 }
			}

			// extension check
			if(extensionList.contains(fileExtension1.toLowerCase()) == false) {					
				return showMessageWithRedirect("이미지 형식만 업로드 가능합니다", "/vegemilBaby/model/apply", Method.GET, null, model);
			}
						
			if(!"".equals(originalName1)) {
				String file1 = originalName1.substring(originalName1.lastIndexOf("\\") + 1);
				
				String savefileName1 = uuid + "_" + file1.replaceAll("\\s", "");
				
				//저장 - 실제 경로
				Path savePath = Paths.get(uploadPath + "/upload/vegemilBaby/" + savefileName1);
				//저장 - 테스트경로
				//Path savePath = Paths.get("D:/upload/admin/vegemilbaby/"+savefileName1);

				//저장
				calModel.getFileName1().transferTo(savePath);
				//포트폴리오
				calModel.setCImage(savefileName1);
			}
			if(!"".equals(originalName2)) {
				String file2 = originalName2.substring(originalName2.lastIndexOf("\\") + 1);
				
				String savefileName2 = uuid + "_" + file2.replaceAll("\\s", "");;
				//저장 - 실제 경로
				Path savePath2 = Paths.get(uploadPath + "/upload/vegemilBaby/" + savefileName2);
				//저장 - 테스트경로
				//Path savePath2 = Paths.get("D:/upload/admin/vegemilbaby/"+savefileName2);
				
				//저장
				calModel.getFileName2().transferTo(savePath2);
				//포트폴리오
				calModel.setCImage2(savefileName2);
			}
			
			boolean isRegistered = vegemilBabyCommunityService.insertModelForm(calModel);
			if (isRegistered == false) {
				out.println("<script>alert('샘플 신청이 실패했습니다.'); history.go(-1);</script>");
				out.flush();
				return showMessageWithRedirect("데이터베이스 처리 과정에 문제가 발생하였습니다.", "/vegemilBaby/event_model", Method.GET, null, model);
			}
		
		} catch (DataAccessException e) {
			out.println("<script>alert('데이터베이스 처리 과정에 문제가 발생하였습니다.'); history.back();</script>");
			out.flush();
			return showMessageWithRedirect("데이터베이스 처리 과정에 문제가 발생하였습니다.", "/vegemilBaby/event_model", Method.GET, null, model);
			
		} catch (Exception e) {
			System.out.println(e);

			out.println("<script>alert('시스템에 문제가 발생하였습니다.'); history.go(-1);</script>");
			out.flush();
			return showMessageWithRedirect("시스템에 문제가 발생하였습니다.", "/vegemilBaby/event_model", Method.GET, null, model);
		}
		
		return showMessageWithRedirect("아기달력모델 신청 완료되었습니다.", "/vegemilBaby/event_model", Method.GET, null, model);
	}
	
	//샘플 신청 페이지
	@GetMapping("/vegemilBaby/sample/form")
	public String moveSampleForm(Authentication authentication, Model model,
							@RequestParam(value = "sItem", required = false) String sItem) {		
		MemberDTO member = new MemberDTO();
		if(authentication == null) {
			return showMessageWithRedirect("로그인후 이용가능합니다.", "/vegemilBaby/sample", Method.GET, null, model);
		} else {
			member = (MemberDTO) authentication.getPrincipal();
			if("1".equals(member.getMIsIdle())){
	        	return showMessageWithRedirect("고객님은 휴면 회원입니다. 휴면 해제 페이지로 이동합니다.", "/member/wakeUp", Method.GET, null, model);
	        }
		}

        model.addAttribute("member", member);		
        model.addAttribute("sItem", sItem);
		return "vegemilBaby/sampleForm";
	}
	//샘플 신청 등록
	@PostMapping("/vegemilBaby/sample/apply")
	public String submitSampleForm(@ModelAttribute("sample") VegemilBabySampleDTO sample, 
								HttpServletRequest request, Model model, HttpServletResponse response) throws Exception {
		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		try {
			
			boolean isRegistered = vegemilBabyCommunityService.isSampleForm(sample);
			if (isRegistered == true) {
				out.println("<script>alert('고객님은 이미 샘플신청을 했습니다.'); history.go(-1);</script>");
				out.flush();
				return showMessageWithRedirect("고객님은 이미 샘플신청을 했습니다.", "/vegemilBaby/sample", Method.GET, null, model);
			}
			isRegistered = vegemilBabyCommunityService.insertSampleForm(sample);
			if (isRegistered == false) {
				out.println("<script>alert('샘플 신청이 실패했습니다.'); history.go(-1);</script>");
				out.flush();
				return showMessageWithRedirect("데이터베이스 처리 과정에 문제가 발생하였습니다.", "/webzine/event", Method.GET, null, model);
			}
		
		} catch (DataAccessException e) {
			out.println("<script>alert('데이터베이스 처리 과정에 문제가 발생하였습니다.'); history.back();</script>");
			out.flush();
			return showMessageWithRedirect("데이터베이스 처리 과정에 문제가 발생하였습니다.", "/vegemilBaby/sample", Method.GET, null, model);
			
		} catch (Exception e) {
			out.println("<script>alert('시스템에 문제가 발생하였습니다.'); history.go(-1);</script>");
			out.flush();
			return showMessageWithRedirect("시스템에 문제가 발생하였습니다.", "/vegemilBaby/sample", Method.GET, null, model);
		}
		
		return showMessageWithRedirect("샘플신청 완료되었습니다.", "/vegemilBaby/sample", Method.GET, null, model);
	}
}
