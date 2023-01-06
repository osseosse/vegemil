package com.vegemil.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.vegemil.adapter.GsonLocalDateTimeAdapter;
import com.vegemil.constant.Method;
import com.vegemil.domain.MemberDTO;
import com.vegemil.domain.ScheduleDTO;
import com.vegemil.domain.VisitDTO;
import com.vegemil.service.RndService;
import com.vegemil.util.UiUtils;

@Controller
public class RndController extends UiUtils {

	@Autowired
	RndService rndService;
	
	@GetMapping(value="/rnd/factory")
	public String getFactory() {
		return "rnd/factory";
	}
	
	
	@GetMapping(value = "/rnd/getTourSchedule")
	 public @ResponseBody JsonObject getTourSchedule() {

		JsonObject jsonObj = new JsonObject();
		List<ScheduleDTO> volunteerList = rndService.getTourScheduleList();
		if (CollectionUtils.isEmpty(volunteerList) == false) {
			Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new GsonLocalDateTimeAdapter()).create();
			JsonArray jsonArr = gson.toJsonTree(volunteerList).getAsJsonArray();
			jsonObj.add("data", jsonArr);
			
		}

		return jsonObj;
	 }
	
	@GetMapping(value = "/rnd/tourApply")
    public String moveTourApply(Model model, Authentication authentication, @RequestParam(value = "date", required = false) String date)throws Exception{
		
		MemberDTO member = new MemberDTO();
		
		if(!date.equals("")) {
			model.addAttribute("date",date);
		} else {
			return showMessageWithRedirect("올바르지 않은 접근입니다.", "/rnd/factory", Method.GET, null, model);
		}
		if(authentication != null) {
	        member = (MemberDTO) authentication.getPrincipal();  //userDetail 객체를 가져옴
	        model.addAttribute("member",member);	//유저 정보
		}
		
		return "rnd/tourApply";
    }


	@GetMapping(value="/rnd/factoryTour")
	public String postVisitForm(VisitDTO visitDto, Model model) throws Exception {
		
		try {
			
			// 현재 날짜 구하기 (시스템 시계, 시스템 타임존)
	        LocalDate now = LocalDate.now();
	        int year = now.getYear();
	        int monthValue = now.getMonthValue();
	        
			List<VisitDTO> visitList = rndService.getVisitList();
			if(visitList != null) {
				model.addAttribute("visitList", visitList);
				model.addAttribute("yyyymm", year+"년 "+monthValue+"월");
			}
			
		} catch(Exception e) {
			return showMessageWithRedirect("올바르지 않은 접근입니다.", "/", Method.GET, null, model);
		}
		return "rnd/factoryTour";
	}
	
}
