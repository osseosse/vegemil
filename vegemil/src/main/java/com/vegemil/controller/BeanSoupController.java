package com.vegemil.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.vegemil.adapter.GsonLocalDateTimeAdapter;
import com.vegemil.constant.Method;
import com.vegemil.domain.AdminBeanSoupNewsDTO;
import com.vegemil.domain.BeansoupDTO;
import com.vegemil.domain.BeansoupEventDTO;
import com.vegemil.domain.BeansoupNewsDTO;
import com.vegemil.domain.BeansoupVideoDTO;
import com.vegemil.domain.SearchDTO;
import com.vegemil.domain.contest.PaintingContestDTO;
import com.vegemil.service.BeansoupService;
import com.vegemil.util.UiUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class BeanSoupController extends UiUtils {

	@Value("${spring.servlet.multipart.location}")
	private String uploadPath;

	@Autowired
	BeansoupService beansoupService;

	@GetMapping("/beanSoup")
	public String beanSoupMain(Model model) {

		List<BeansoupDTO> beansoupList = beansoupService.selectBeansoupList();
		List<BeansoupEventDTO> mainBeansoupEventList = beansoupService.selectMainBeansoupEventList();
		model.addAttribute("beansoupList", beansoupList);
		model.addAttribute("mainBeansoupEventList", mainBeansoupEventList);
		return "beansoup/index";
	}

	@GetMapping("/beanSoup/brand")
	public String beanSoupBrand(Model model) {

		return "beansoup/brand";
	}

	@GetMapping("/Main/beanSoup/intro.aspx")
	public String beanSoupIntro(Model model) {
		return "redirect:/beansoup/intro";
	}

	@GetMapping("/Main/beanSoup/index.aspx")
	public String beanSoupIndex(Model model) {
		return "redirect:/beanSoup";
	}

	@GetMapping("/Main/event/recipeLanding.aspx")
	public String beanSoupQR(Model model) {

		return "redirect:/beansoup/intro";
	}

	@GetMapping("/main/event/recipeLanding.aspx")
	public String beanSoupQR2(Model model) {

		return "redirect:/beansoup/intro";
	}

	@GetMapping("/beansoup/intro")
	public String moveBeanSoupIntro(Model model) {

		return "beansoup/intro";
	}

	// 간단레시피
	@GetMapping("/beanSoup/list")
	public String beanSoupRecipe(Model model, @RequestParam(required = false) String tag) {

		List<BeansoupDTO> beansoupSearchList = new ArrayList<>();
		List<BeansoupDTO> beansoupList = beansoupService.selectBeansoupList();
		Map<String, Integer> countMap = beansoupService.mappingCount(beansoupList);

		if (tag != null && tag != "") {
			beansoupSearchList = beansoupService.selectBeanListWithKeyword(tag);
			model.addAttribute("search_html", beansoupSearchList.size() + "건");
			model.addAttribute("search_result_html", "#" + tag);
		}

		model.addAttribute("countMap", countMap);
		model.addAttribute("searchList", beansoupSearchList);
		model.addAttribute("beansoupList", beansoupList);

		return "beansoup/list";
	}

	// 간단 레시피 검색
	@PostMapping("/beanSoup/list")
	public String beanSoupRecipeSearch(Model model, @RequestParam("txtSearchWord") String serachKeyword) {

		List<BeansoupDTO> beansoupSearchList = beansoupService.selectBeanListWithKeyword(serachKeyword);
		List<BeansoupDTO> beansoupList = beansoupService.selectBeansoupList();
		Map<String, Integer> countMap = beansoupService.mappingCount(beansoupList);

		model.addAttribute("countMap", countMap);
		model.addAttribute("search_html", beansoupSearchList.size() + "건");
		model.addAttribute("search_result_html", "#" + serachKeyword);
		model.addAttribute("searchList", beansoupSearchList);
		model.addAttribute("beansoupList", beansoupList);

		return "beansoup/list";
	}

	// 간단레시피 상세
	@GetMapping("/beanSoup/detail")
	public String beanSoupRecipeDetail(Model model, @RequestParam("recipe") String recipe) {

		BeansoupDTO recipeDetail = beansoupService.selectBeansoupDetail(recipe);
		List<BeansoupDTO> beansoupList = beansoupService.selectBeansoupProposalList(recipeDetail.getMCate());

		model.addAttribute("recipe", recipe);
		model.addAttribute("beansoupList", beansoupList);

		return "beansoup/detail";
	}

	// [소식]영상
	@GetMapping("/beanSoup/video")
	public String beanSoupVideo(Model model, @ModelAttribute("params") BeansoupVideoDTO params) {

		params.setRecordsPerPage(9);
		List<BeansoupVideoDTO> beansoupVideoList = beansoupService.selectBeansoupVideoList(params);

		model.addAttribute("beansoupVideoList", beansoupVideoList);

		return "beansoup/video";
	}

	// [소식]뉴스
	@GetMapping("/beanSoup/news")
	public String beanSoupNews(Model model) {

		List<BeansoupNewsDTO> beansoupNewsList = beansoupService.selectBeansoupNewsList();
		for (BeansoupNewsDTO news : beansoupNewsList) {

			if (news.getMIng() == 1) {
				news.setIngStr("진행중");
			} else {
				news.setIngStr("진행마감");
			}
		}

		model.addAttribute("beansoupNewsList", beansoupNewsList);

		return "beansoup/news";
	}

	// [소식]생생후기
	@GetMapping("/beanSoup/event")
	public String beanSoupEvent(Model model, @ModelAttribute("params") BeansoupEventDTO params) {

		params.setRecordsPerPage(9);
		model.addAttribute("beansoupEventList", beansoupService.selectBeansoupEventList(params));

		return "beansoup/event";
	}

	// 구매처
	@GetMapping("/beanSoup/mall")
	public String beanSoupMall() {

		return "beansoup/mall";
	}

	// 정적 이미지 불러오기
	@GetMapping("/web/upload/BEANSOUP/event/{filename}")
	public ResponseEntity<Resource> display(@PathVariable(value = "filename", required = false) String filename) {
		Resource resource = new FileSystemResource(uploadPath + "/upload/BEANSOUP/event/" + filename);
		// Resource resource = new FileSystemResource("D:/upload/admin/" + filename);
		if (!resource.exists())
			return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);
		HttpHeaders header = new HttpHeaders();
		Path filePath = null;
		try {
			filePath = Paths.get(uploadPath + "/upload/BEANSOUP/event/" + filename);
			// filePath = Paths.get("D:/upload/admin/" + filename);
			header.add("Content-type", Files.probeContentType(filePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Resource>(resource, header, HttpStatus.OK);
	}

	// 그림동시대회코드

	@RequestMapping(value = { "/beanSoup/Season", "/beanSoup/season", "beansoup/Season", "/beansoup/season" })
	public String getSeasonContest() {
		return "beansoup/season";
	}

	// test 그림대회인증
	@RequestMapping(value = "/beanSoup/agreementDev123", method = { RequestMethod.GET, RequestMethod.POST })
	public String moveJoin(Model model, HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "step", required = false, defaultValue = "1") int step) throws Exception {

		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		PaintingContestDTO paintingContest = new PaintingContestDTO();
		String returnHtml = "";

		// -----------------------pcc 결과값--------------------------
		String pccResult = "";
		String pccNameOrg = "";
		String pccName = "";
		String pccSex = "";
		String pccBirymd = "";
		String pccDi = "";
		String pccCellno = "";
		String diKey = "";
		pccResult = request.getParameter("PCC_RESULT");

		if (pccResult != null && pccResult.equals("Y")) {
			step = 2;
			pccNameOrg = request.getParameter("PCC_NAME");
			pccName = URLDecoder.decode(pccNameOrg, "utf-8");
			pccSex = request.getParameter("PCC_SEX");
			pccBirymd = request.getParameter("PCC_BIRYMD");
			pccDi = request.getParameter("PCC_DI");
			pccCellno = request.getParameter("PCC_CELLNO");
			diKey = request.getParameter("diKey");
			diKey = pccDi;
		}
		// -----------------------pcc 결과값--------------------------

		if (step == 1) {
			// model.addAttribute("member", member);
			returnHtml = "beansoup/agreement";
		} else if (step == 2) {
			paintingContest.setGuardianName(pccName);
			paintingContest.setGuardianPh(pccCellno);
			paintingContest.setGuardianDI(diKey);
			// 다음 그림 등록 페이지에서 렌더링 될 것들
			model.addAttribute("paintingContest", paintingContest);
			returnHtml = "beansoup/submitWork";
		} else {
			out.println("<script>alert('올바르지 않은 접근입니다.'); history.go(-1);</script>");
			out.flush();
			return showMessageWithRedirect("올바르지 않은 접근입니다.", "beanSoup/agreement", Method.GET, null, model);
		}

		return returnHtml;
	}
	
	//그림 제출
	@PostMapping("/beansoup/submitWork")
	public String getSubmitWorkForm(@Validated @ModelAttribute("paintingContest") PaintingContestDTO paintingContestDto,
			BindingResult bindingResult,@RequestParam("fileName") MultipartFile fileName, HttpServletRequest request, Model model, HttpServletResponse response) throws IOException {

		if (bindingResult.hasErrors()) {
			log.info("검증 에러 발생");			
			
		}
		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		try {
			String originalName = fileName.getOriginalFilename();
			if(!"".equals(originalName)) {
				String file = originalName.substring(originalName.lastIndexOf("\\") + 1);
				String uuid = UUID.randomUUID().toString();
				String savefileName = uuid + "_" + file;
				Path savePath = Paths.get("D:/test/"+savefileName);
				
				fileName.transferTo(savePath);
				paintingContestDto.setPaintingFilename(originalName);
				paintingContestDto.setPaintingSavedFilename(savefileName);
				
			}
			
			beansoupService.submitPaintingPoetWork(paintingContestDto);
			
		} catch (DataAccessException e) {
			out.println("<script>alert('데이터베이스 처리 과정에 문제가 발생하였습니다.'); history.back();</script>");
			out.flush();
			return showMessageWithRedirect("데이터베이스 처리 과정에 문제가 발생하였습니다.", "/beanSoup", Method.GET, null, model);
			
		} catch (Exception e) {
			System.out.println(e);
			System.out.println(e.getMessage());
			out.println("<script>alert('시스템에 문제가 발생하였습니다.'); history.go(-1);</script>");
			out.flush();
			return showMessageWithRedirect("시스템에 문제가 발생하였습니다.", "/beanSoup", Method.GET, null, model);
		}
		
		return showMessageWithRedirect("그림동시대회 접수가 완료되었습니다.", "/beanSoup", Method.GET, null, model);
			
	}
	
	// 콘테스트 어드민
	@GetMapping("/admin/manage/beanSoup/PaintingPoetCon")
	public String getPaintingPoetContAdmin(Model model, @RequestParam(required = false) SearchDTO searchDTO) {
		
		return "admin/beanSoup/PaintingPoetCon";
	}
	
	@GetMapping("/admin/manage/beanSoup/paintingPoetConList")
	public @ResponseBody JsonObject paintingPoetConList(@ModelAttribute("params") PaintingContestDTO params) {
		
		List<PaintingContestDTO> pcList = beansoupService.findAllSubmitList(params);
		JsonObject jsonObj = new JsonObject();
		if (CollectionUtils.isEmpty(pcList) == false) {
			Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new GsonLocalDateTimeAdapter()).create();
			JsonArray jsonArr = gson.toJsonTree(pcList).getAsJsonArray();
			jsonObj.add("data", jsonArr);
		}
		return jsonObj;
	}
}
