package com.vegemil.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vegemil.domain.AdminEventDTO;
import com.vegemil.domain.AdminMediaNewsDTO;
import com.vegemil.domain.DataTableDTO;
import com.vegemil.service.AdminEventService;

import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
@RequestMapping("/admin/manage")
public class AdminEventController {
	
	@Autowired
	private AdminEventService adminEventService;
	
	//이벤트 등록 페이지
	@GetMapping("/event/eventAdd")
	public String moveEventAddPage(@RequestParam(value = "eIdx", required = false) Long eIdx , Model model)throws Exception {
		
		AdminEventDTO eventDto = adminEventService.getEventDetail(eIdx);
		model.addAttribute("eventInfo", eventDto);
		
		return "admin/event/eventAdd";
	}
	
	//이벤트 수정 페이지
	@GetMapping("/event/eventUpdate")
	public String moveEventUpdatePage(@RequestParam(value = "eIdx", required = false) Long eIdx , Model model)throws Exception {
		
		AdminEventDTO eventDto = adminEventService.getEventDetail(eIdx);
		model.addAttribute("eventInfo", eventDto);
		
		return "admin/event/eventUpdate";
	}
	
	//이벤트 수정
	@PostMapping("/event/eventUpdate")
	@ResponseBody
	public Map<String, Object> updateEvent(@ModelAttribute("params") final AdminEventDTO params, Model model,
			HttpServletResponse response, HttpServletRequest request) throws Exception {

		//String deleteFile = params.getMImg();

		Map<String, Object> rtnMap = new HashMap<String, Object>();

		try {
			boolean isUpdated = adminEventService.updateEvent(params);
			rtnMap.put("result", isUpdated);
		} catch (DataAccessException e) {
		} catch (Exception e) {
		}

		return rtnMap;
		}
	
	
	
	//이벤트 등록 - 베지밀
	@PostMapping("/event/eventAdd")
	@ResponseBody
	public Map<String, Object> saveVegemilEvent(@ModelAttribute("params") final AdminEventDTO params, Model model,
			HttpServletResponse response, HttpServletRequest request) throws Exception {

		Map<String, Object> rtnMap = new HashMap<String, Object>();
		
		try {			
			boolean isRegistered = adminEventService.registerEvent(params);			
			rtnMap.put("result", isRegistered);
		} catch (DataAccessException e) {
		} catch (Exception e) {
		}
		return rtnMap;
	}
	
	
	//이벤트 조회페이지 - 베지밀
	@GetMapping("/event/eventVegemil")
	public String moveEventListPage() {
		return "admin/event/eventVegemil";
	}
	
	//이벤트 조회 - 베지밀
	@GetMapping("/event/eventVegemilList")
	public @ResponseBody DataTableDTO getVegemilEventList(
						@ModelAttribute("params") AdminEventDTO params, Model model, 
						@RequestParam Map<String, Object> commandMap) {
		
		DataTableDTO dataTableDto = adminEventService.getVegemilEventList(commandMap);
		
		return dataTableDto;
	}
	
	//이벤트 삭제 - 베지밀
	@RequestMapping(value = "/event/deleteVegemilEvent", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody boolean deleteVegemilEvent(@ModelAttribute("params") AdminEventDTO params, Model model,
			HttpServletResponse response, HttpServletRequest request) {
		try {
			String checkList[] = request.getParameterValues("checkList");
			log.info("check===========" + checkList);
			ArrayList<String> list = new ArrayList<>();
			for (int i = 0; i < checkList.length; i++) {
				list.add(checkList[i]);
			}

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("list", list);

			boolean isDeleted = adminEventService.deleteVegemilEvent(paramMap);
			if (!isDeleted) {
				return false;
			}

		} catch (DataAccessException e) {
			return false;
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	//메인페이지 진열정보 수정
	@GetMapping(value = "/event/displayEventInfo")
	public @ResponseBody boolean displayEventInfo(@RequestParam(value = "eIdx", required = false) Long eIdx,			
			@RequestParam(value = "eActive", required = false) int eActive, @RequestParam(value = "eBvactive", required = false) int eBvactive,
			HttpServletResponse response) throws Exception {
		boolean isRegistered = true;
		try {
			if (eIdx == null) {
				return false;
			}
			AdminEventDTO eventInfo = adminEventService.getEventDetail(eIdx);
			System.out.println(eventInfo.toString());
			eventInfo.setEIdx(eIdx);
			eventInfo.setEActive(eActive);
			eventInfo.setEBvactive(eBvactive);
			isRegistered = adminEventService.registerEvent(eventInfo);
			if (!isRegistered) {
				throw new IOException("저장에 실패하였습니다.");
			}
		}catch (DataAccessException e) {
    		e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isRegistered;
	}
	
	

}
	
	

