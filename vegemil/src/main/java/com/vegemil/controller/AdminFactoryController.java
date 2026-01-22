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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vegemil.domain.AdminFactpostDTO;
import com.vegemil.domain.AdminVisitDTO;
import com.vegemil.domain.AdminVisitSetupDTO;
import com.vegemil.domain.DataTableDTO;
import com.vegemil.service.AdminCustomerService;
import com.vegemil.util.UiUtils;

import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
public class AdminFactoryController extends UiUtils{
	
	
	@Autowired
	private AdminCustomerService adminCustomerService;
	
	@RequestMapping(value = "/admin/manage/factory/{viewName}")
    public String adminMoveCustomer(@PathVariable(value = "viewName", required = false) String viewName)throws Exception{
		return "admin/factory/"+viewName;
    }
	
	@RequestMapping(value = "/admin/manage/factory/visit")
	public String openVisit(@ModelAttribute("params") AdminVisitSetupDTO params, Model model,
			@RequestParam Map<String, Object> commandMap) {

		AdminVisitSetupDTO setupDto = adminCustomerService.getVisitSetup();
		model.addAttribute("visitSetup", setupDto);
		return "admin/customer/visit";
	}
	
	@RequestMapping(value = "/admin/manage/factory/visitList")
	public @ResponseBody DataTableDTO getVisitList(@ModelAttribute("params") AdminVisitDTO params, Model model,
			@RequestParam Map<String, Object> commandMap) {

		DataTableDTO dataTableDto = adminCustomerService.getVisitList(commandMap);
		return dataTableDto;
	}
	
	@RequestMapping(value = "/admin/manage/factory/saveVisit", method = {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Map<String, Object> saveVisit(@ModelAttribute("params") final AdminVisitDTO params, Model model) throws Exception {
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		try {
			boolean isUpdate = adminCustomerService.saveVisit(params);
			rtnMap.put("result", isUpdate);
		} catch (DataAccessException e) {
			log.error("fail to process file", e);
			throw new IOException("저장에 실패하였습니다.");
		} catch (Exception e) {
			log.error("fail to process file", e);
			throw new IOException("저장에 실패하였습니다.");
		}

		return rtnMap;
	}
	
	@RequestMapping(value = "/admin/manage/factory/deleteVisit", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody boolean deleteVisit(@ModelAttribute("params") AdminVisitDTO params, Model model, 
    		HttpServletResponse response, HttpServletRequest request) throws Exception {
		try {
	   		String checkList[] = request.getParameterValues("checkList");
	   		ArrayList<String> list = new ArrayList<>();
    		for(int i=0; i<checkList.length; i++) {
   			list.add(checkList[i]);
   		}
   		
   		Map<String, Object> paramMap = new HashMap<String, Object>();
   		paramMap.put("list", list);
   		
   		boolean isDeleted = adminCustomerService.deleteVisit(paramMap);
   		if (!isDeleted) {
			return false;
		}
   		
		} catch (DataAccessException e) {
			log.error("fail to process file", e);
			throw new IOException("저장에 실패하였습니다.");
		} catch (Exception e) {
			log.error("fail to process file", e);
			throw new IOException("저장에 실패하였습니다.");
		}
		return true;
   }
	
	@GetMapping(value = "/admin/manage/factory/displayVisit")
	public @ResponseBody boolean displayVisit(@RequestParam(value = "vIdx", required = true) Long vIdx, 
			@RequestParam(value = "vDisplay", required = true) int vDisplay, HttpServletResponse response) throws Exception {
		boolean isRegistered = true;
		try {
			if (vIdx == null) {
				return false;
			}
			AdminVisitDTO visitDto = adminCustomerService.getVisitDetail(vIdx);
			visitDto.setVIdx(vIdx);
			visitDto.setVDisplay(vDisplay);
			isRegistered = adminCustomerService.saveDisplayVisit(visitDto);
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
	
	@RequestMapping(value = "/admin/manage/factory/factoryTourReviewList")
	public @ResponseBody DataTableDTO getFactoryTourReviewList(@ModelAttribute("params") AdminFactpostDTO params, Model model,
			@RequestParam Map<String, Object> commandMap) {
		
		DataTableDTO dataTableDto = adminCustomerService.getFactoryTourReviewList(commandMap);
		return dataTableDto;
	}
	
	@RequestMapping(value = "/admin/manage/factory/saveVisitSetup", method = {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Map<String, Object> saveVisitSetup(@ModelAttribute("params") final AdminVisitSetupDTO params, Model model) throws Exception {
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		try {

			boolean isUpdate = adminCustomerService.saveVisitSetup(params);
			rtnMap.put("result", isUpdate);
		} catch (DataAccessException e) {
			log.error("fail to process file", e);
			throw new IOException("저장에 실패하였습니다.");
		} catch (Exception e) {
			log.error("fail to process file", e);
			throw new IOException("저장에 실패하였습니다.");
		}

		return rtnMap;
	}
	
	@RequestMapping(value = "/admin/manage/factory/saveFactoryTourReview", method = {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Map<String, Object> saveFactoryTourReview(@ModelAttribute("params") final AdminFactpostDTO params, Model model) throws Exception {
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		try {
			boolean isUpdate = adminCustomerService.saveFactoryTourReview(params);
			rtnMap.put("result", isUpdate);
		} catch (DataAccessException e) {
			log.error("fail to process file", e);
			throw new IOException("저장에 실패하였습니다.");
		} catch (Exception e) {
			log.error("fail to process file", e);
			throw new IOException("저장에 실패하였습니다.");
		}

		return rtnMap;
	}
}
