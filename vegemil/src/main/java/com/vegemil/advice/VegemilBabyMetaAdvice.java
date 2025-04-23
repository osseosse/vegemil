package com.vegemil.advice;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.vegemil.controller.VegemilBabyController;
import com.vegemil.service.vegemilBaby.VegemilBabyCommunityService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;


@ControllerAdvice(assignableTypes  = VegemilBabyController.class)
@RequiredArgsConstructor
@Log4j2
public class VegemilBabyMetaAdvice {
	
	private final VegemilBabyCommunityService vegemilBabyCommunityService;
	
	@ModelAttribute
	public void addVegemilBabyMetaModel(Model model, HttpServletRequest request) {
		
		log.info("[VegemilBabyMetaAdvice.Advice]request vegemil baby Community path = {}", request.getRequestURI().toString());	
		model.addAttribute("tags", vegemilBabyCommunityService.getBabyMetaGuideData(request.getRequestURI().toString()));	
	}

}
