package com.vegemil.controller;

import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.vegemil.constant.Method;
import com.vegemil.domain.MemberDTO;
import com.vegemil.domain.vegemilBaby.VegemilBabyCalendarModelDTO;
import com.vegemil.domain.vegemilBaby.VegemilBabySampleDTO;
import com.vegemil.service.vegemilBaby.VegemilBabyCommunityService;
import com.vegemil.util.UiUtils;


@CrossOrigin(origins="*", allowedHeaders = "*")
@Controller
@RequestMapping("/vegemilBaby")
public class VegemilBabyController extends UiUtils {

	@Autowired
	private VegemilBabyCommunityService vegemilBabyCommunityService;

	@RequestMapping(value = "/{viewName}")
	public String moveVegemilBabyPage(@PathVariable(value = "viewName", required = false) String viewName)
			throws Exception {
		return "vegemilBaby/" + viewName;
	}
	
	@GetMapping("/index")
	public String index(Model model) {
		model.addAttribute("magazineList", vegemilBabyCommunityService.selectMagazineIndex());
		model.addAttribute("qnaList", vegemilBabyCommunityService.selectQnAIndex());
		return "vegemilBaby/index";
	}

	/* Community */
	// 육아정보
	@GetMapping("/magazine")
	public String moveMagazineList(@RequestParam(required = false, defaultValue = "all") String category, Model model) {
		model.addAttribute("magazineList", vegemilBabyCommunityService.selectAllMagazine(category));
		model.addAttribute("categoryCount", vegemilBabyCommunityService.selectCategoryCount());
		return "vegemilBaby/magazine";
	}

	// 육아정보 상세
	@GetMapping(value = { "/magazine/detail/{idx}" })
	public String moveMagazineDetail(@PathVariable("idx") Long idx, Model model) {
		model.addAttribute("magazineDetail", vegemilBabyCommunityService.selectMagazineDetail(idx));
		model.addAttribute("categoryCount", vegemilBabyCommunityService.selectCategoryCount());
		return "vegemilBaby/magazineDetail";
	}

	// 영유아식 레시피
	@GetMapping("/recipe")
	public String moveRecipeList(Model model) {
		model.addAttribute("recipeList", vegemilBabyCommunityService.selectRecipeList());
		return "vegemilBaby/recipe";
	}

	// 영유아식 레시피 상세
	@GetMapping("/recipe/detail/{idx}")
	public String moveRecipeDetail(@PathVariable("idx") Long idx, Model model) {
		model.addAttribute("recipeList", vegemilBabyCommunityService.selectRecipeList());
		model.addAttribute("recipe", vegemilBabyCommunityService.selectRecipeDetail(idx));
		return "vegemilBaby/recipeDetail";
	}
	
	/* Event */
	@GetMapping("/sample/form")
	public String moveSampleForm(Authentication authentication, Model model,
							@RequestParam(value = "sItem", required = false) String sItem) {
		
		MemberDTO member = new MemberDTO();
		if(authentication == null) {
			return showMessageWithRedirect("로그인후 이용가능합니다.", "/vegemilBaby/sample", Method.GET, null, model);
		} else {
			member = (MemberDTO) authentication.getPrincipal();
		}
        model.addAttribute("member", member);
        model.addAttribute("sItem", sItem);
        
		return "vegemilBaby/sampleForm";
	}
	
	@GetMapping("/model/form")
	public String moveModelForm(Authentication authentication, Model model) {
		
		MemberDTO member = new MemberDTO();
		VegemilBabyCalendarModelDTO calModel = new VegemilBabyCalendarModelDTO();
		
		if(authentication == null) {
			member.setMName("");
			member.setMHp("");
			member.setMEmail("");
		} else {
			member = (MemberDTO) authentication.getPrincipal();
		}
        model.addAttribute("member", member);
        model.addAttribute("model", calModel);
        
		return "vegemilBaby/event_model_form";
	}
	
	@PostMapping("/model/apply")
	public String submitModelForm(@ModelAttribute("model") VegemilBabyCalendarModelDTO calModel,
							BindingResult bindingResult,
							HttpServletRequest request, Model model, HttpServletResponse response) throws Exception {
		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		try {
			/*
			String originalName = fileName.getOriginalFilename();
			if(!"".equals(originalName)) {
				String file = originalName.substring(originalName.lastIndexOf("\\") + 1);
				String uuid = UUID.randomUUID().toString();
				String savefileName = uuid + "_" + file;
				//테스트경로
				Path savePath = Paths.get("D:\\upload" + savefileName);
				
				//저장
				fileName.transferTo(savePath);
				//포트폴리오
				calModel.setCImage(file);
			}
			*/
			
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
			out.println("<script>alert('시스템에 문제가 발생하였습니다.'); history.go(-1);</script>");
			out.flush();
			return showMessageWithRedirect("시스템에 문제가 발생하였습니다.", "/vegemilBaby/event_model", Method.GET, null, model);
		}
		
		return showMessageWithRedirect("아기달력모델 신청 완료되었습니다.", "/vegemilBaby/event_model", Method.GET, null, model);
	}
	
	//샘플 신청 등록
	@PostMapping("/sample/apply")
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

	// 육아 정보
	/*
	 * @GetMapping("/e_magazine") public String eMagazine(@ModelAttribute("params")
	 * final SearchDTO params, Model model) {
	 * 
	 * model.addAttribute("categoryCount",
	 * vegemilBabyCommunityService.categoryCount()); String category =
	 * params.getCategory(); model.addAttribute("category", category);
	 * 
	 * List<VegemilBabyMagazineDTO> pbMagazineList =
	 * vegemilBabyCommunityService.findPbMagazine(params);
	 * model.addAttribute("pbMagazineList", pbMagazineList);
	 * List<VegemilBabyMagazineDTO> ghMagazineList =
	 * vegemilBabyCommunityService.findGhMagazine(params);
	 * model.addAttribute("ghMagazineList", ghMagazineList);
	 * List<VegemilBabyMagazineDTO> peMagazineList =
	 * vegemilBabyCommunityService.findPeMagazine(params);
	 * model.addAttribute("peMagazineList", peMagazineList); return
	 * "vegemilBaby/e_magazine";
	 * 
	 * }
	 */

	/* Event */
	@GetMapping("/bv_event")
	public String bv_event(Model model) {
		model.addAttribute("eventList", vegemilBabyCommunityService.eventList());
		return "vegemilBaby/bv_event";
	}

}
