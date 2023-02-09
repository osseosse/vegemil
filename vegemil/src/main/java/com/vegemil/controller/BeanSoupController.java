package com.vegemil.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vegemil.domain.BeansoupDTO;
import com.vegemil.domain.BeansoupEventDTO;
import com.vegemil.domain.BeansoupNewsDTO;
import com.vegemil.domain.BeansoupVideoDTO;
import com.vegemil.service.BeansoupService;
import com.vegemil.util.UiUtils;

@Controller
public class BeanSoupController extends UiUtils {
	
	@Value("${spring.servlet.multipart.location}")
    private String uploadPath;
	
	@Autowired
	BeansoupService beansoupService;	

	@GetMapping("/beanSoup")
	public String beanSoupMain(Model model) {
		
		List<BeansoupDTO> beansoupList = beansoupService.selectBeansoupList();
		model.addAttribute("beansoupList",beansoupList);
	
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
	
	//간단레시피
	@GetMapping("/beanSoup/list")
	public String beanSoupRecipe(Model model, @RequestParam(required  = false) String tag) {
		
		List<BeansoupDTO> beansoupSearchList = new ArrayList<>();
		List<BeansoupDTO> beansoupList = beansoupService.selectBeansoupList();
		Map<String,Integer> countMap = beansoupService.mappingCount(beansoupList);
		
		if(tag != null && tag != "") {
			beansoupSearchList = beansoupService.selectBeanListWithKeyword(tag);
			model.addAttribute("search_html",beansoupSearchList.size()+"건");
			model.addAttribute("search_result_html", "#"+tag);
		} 
		
		model.addAttribute("countMap",countMap);
		model.addAttribute("searchList",beansoupSearchList);
		model.addAttribute("beansoupList",beansoupList);
		
		return "beansoup/list";
	}
	
	//간단 레시피 검색
	@PostMapping("/beanSoup/list")
	public String beanSoupRecipeSearch(Model model, @RequestParam("txtSearchWord") String serachKeyword) {
		
		List<BeansoupDTO> beansoupSearchList = beansoupService.selectBeanListWithKeyword(serachKeyword);
		List<BeansoupDTO> beansoupList = beansoupService.selectBeansoupList();
		Map<String,Integer> countMap = beansoupService.mappingCount(beansoupList);
	
		model.addAttribute("countMap",countMap);
		model.addAttribute("search_html",beansoupSearchList.size()+"건");
		model.addAttribute("search_result_html", "#"+serachKeyword);
		model.addAttribute("searchList",beansoupSearchList);
		model.addAttribute("beansoupList", beansoupList);
		
		return "beansoup/list";
	}
	
	//간단레시피 상세 
	@GetMapping("/beanSoup/detail")
	public String beanSoupRecipeDetail(Model model, @RequestParam("recipe") String recipe) {
		
		BeansoupDTO recipeDetail = beansoupService.selectBeansoupDetail(recipe);
		List<BeansoupDTO> beansoupList = beansoupService.selectBeansoupProposalList(recipeDetail.getMCate());
		
		model.addAttribute("recipe", recipe);
		model.addAttribute("beansoupList", beansoupList);
		
		return "beansoup/detail";
	}
	
	//[소식]영상
	@GetMapping("/beanSoup/video")
	public String beanSoupVideo(Model model, @ModelAttribute("params") BeansoupVideoDTO params) {
		
		params.setRecordsPerPage(9);
		List<BeansoupVideoDTO> beansoupVideoList = beansoupService.selectBeansoupVideoList(params);
		
		model.addAttribute("beansoupVideoList",beansoupVideoList);
		
		return "beansoup/video";
	}
	
	//[소식]뉴스
	@GetMapping("/beanSoup/news")
	public String beanSoupNews(Model model) {
		
		List<BeansoupNewsDTO> beansoupNewsList = beansoupService.selectBeansoupNewsList();
		for(BeansoupNewsDTO news : beansoupNewsList) {
			
			if(news.getMIng()==1) {
				news.setIngStr("진행중");
			} else {
				news.setIngStr("진행마감");
			}
		}
		
		model.addAttribute("beansoupNewsList",beansoupNewsList);
		
		return "beansoup/news";
	}
	
	//[소식]생생후기
	@GetMapping("/beanSoup/event")
	public String beanSoupEvent(Model model, @ModelAttribute("params") BeansoupEventDTO params) {
		
		params.setRecordsPerPage(9);
		model.addAttribute("beansoupEventList",beansoupService.selectBeansoupEventList(params));
		
		return "beansoup/event";
	}
	
	//구매처
	@GetMapping("/beanSoup/mall")
	public String beanSoupMall() {
		
		return "beansoup/mall";
	}
	
	//정적 이미지 불러오기
	@GetMapping("/web/upload/BEANSOUP/event/{filename}")
	public ResponseEntity<Resource> display(@PathVariable(value = "filename", required = false) String filename) {
		Resource resource = new FileSystemResource(uploadPath + "/upload/BEANSOUP/event/" + filename);
		//Resource resource = new FileSystemResource("D:/upload/admin/" + filename);
		if(!resource.exists()) 
			return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);
		HttpHeaders header = new HttpHeaders();
		Path filePath = null;
		try {
			filePath = Paths.get(uploadPath + "/upload/BEANSOUP/event/" + filename);
			//filePath = Paths.get("D:/upload/admin/" + filename);
			header.add("Content-type", Files.probeContentType(filePath));
		} catch(IOException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Resource>(resource, header, HttpStatus.OK);
	}
}
