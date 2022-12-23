package com.vegemil.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vegemil.domain.SearchDTO;
import com.vegemil.domain.vegemilBaby.VegemilBabyMagazineDTO;
import com.vegemil.service.vegemilBaby.VegemilBabyCommunityService;

@Controller
@RequestMapping("/vegemilBaby")
public class VegemilBabyController {

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
		model.addAttribute("magazineDetail", vegemilBabyCommunityService.magazineDetail(idx));
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
	public String recipe(@PathVariable("idx") Long idx, Model model) {
		model.addAttribute("recipe", vegemilBabyCommunityService.selectRecipe(idx));
		return "vegemilBaby/recipeDetail";
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
