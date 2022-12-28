package com.vegemil.controller;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
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
import com.vegemil.domain.AdminFaqDTO;
import com.vegemil.domain.AdminFaqScoreDTO;
import com.vegemil.domain.AdminSupportDTO;
import com.vegemil.domain.DataTableDTO;
import com.vegemil.domain.FaqDTO;
import com.vegemil.service.AdminFaqService;
import com.vegemil.util.UiUtils;

import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
public class AdminCustomerController extends UiUtils {
	
	@Autowired
	private AdminFaqService adminFaqService;

	@RequestMapping(value = "/admin/customer/{viewName}")
    public String adminMoveCustomer(@PathVariable(value = "viewName", required = false) String viewName)throws Exception{
		return "admin/customer/"+viewName;
    }
	
	@RequestMapping(value = "/admin/customer/faq/table")
	 public @ResponseBody DataTableDTO getFaqList(@ModelAttribute("params") FaqDTO params, Model model,
			 @RequestParam Map<String, Object> commandMap) {

		DataTableDTO dataTableDto = adminFaqService.getFaqList(commandMap);
		return dataTableDto;
	 }
	
	@RequestMapping(value = "/admin/customer/faqAdd")
    public String openFaqAdd(@RequestParam(value = "fIdx", required = false) Long fIdx, HttpServletRequest req, Model model)throws Exception{

		AdminFaqDTO faqDto = adminFaqService.getFaqDetail(fIdx);
		model.addAttribute("faq", faqDto);
		
		return "admin/customer/faqAdd";
    }
	
	@PostMapping(value = "/admin/customer/faq/fileUpload")
	@ResponseBody
	public Map<String, Object> UploadFaq(@ModelAttribute("params") final FaqDTO params, Model model, @RequestParam(value="uploadFile", required=false) MultipartFile uploadFile
			, HttpServletResponse response, HttpServletRequest request) {
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		try {
			String originalName = uploadFile.getOriginalFilename();
			String file = originalName.substring(originalName.lastIndexOf("\\") + 1);
			String uuid = UUID.randomUUID().toString();
			String savefileName = uuid + "_" + file;
			String dirPath = "\\\\211.233.87.7\\data\\images\\mail\\news\\dcf\\dcf2022\\temp_img\\";//"D:/data/dcf/";
//			Path savePath = Paths.get(dirPath + savefileName);
			File f1 = new File(dirPath + savefileName);
			
			uploadFile.transferTo(f1);

			rtnMap.put("uploadPath", f1);
			rtnMap.put("uuid", uuid);
			rtnMap.put("fileName", originalName);
		} catch(IOException e) {
			e.printStackTrace();
		}


		return rtnMap;
	}
	
	@PostMapping(value = "/admin/customer/faq/register")
	public String saveFaq(@ModelAttribute("params") final AdminFaqDTO params, Model model) {
		Map<String, Object> pagingParams = getPagingParams(params);
		try {
			boolean isRegistered = adminFaqService.registerFaq(params);
			if (isRegistered == false) {
				return showMessageWithRedirect("게시글 등록에 실패하였습니다.", "/admin/customer/faqAdd", Method.GET, pagingParams, model);
			}
		} catch (DataAccessException e) {
			return showMessageWithRedirect("데이터베이스 처리 과정에 문제가 발생하였습니다.", "/admin/customer/faqAdd", Method.GET, pagingParams, model);

		} catch (Exception e) {
			return showMessageWithRedirect("시스템에 문제가 발생하였습니다.", "/admin/customer/faqAdd", Method.GET, pagingParams, model);
		}

		return showMessageWithRedirect("게시글 등록이 완료되었습니다.", "/admin/customer/faq", Method.GET, null, model);
	}
	
	@RequestMapping(value = "/admin/customer/faq/updateDisplay", method = {RequestMethod.GET, RequestMethod.POST})
	public String updateFaqDisplay(@ModelAttribute("params") final AdminFaqDTO params, Model model,
			@RequestParam(value = "fIdx", required = false) Long fIdx, @RequestParam(value = "fView", required = false) Long fView) {
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("fIdx", fIdx);
			paramMap.put("fView", fView);
			boolean isUpdate = adminFaqService.updateDisplay(paramMap);
			if (isUpdate== false) {
				return showMessageWithRedirect("진열 등록에 실패하였습니다.", "/admin/customer/faq", Method.GET, null, model);
			}
		} catch (DataAccessException e) {
			return showMessageWithRedirect("데이터베이스 처리 과정에 문제가 발생하였습니다.", "/admin/customer/faq", Method.GET, null, model);

		} catch (Exception e) {
			return showMessageWithRedirect("시스템에 문제가 발생하였습니다.", "/admin/customer/faq", Method.GET, null, model);
		}

		return "/admin/customer/faq";
	}
	
	@RequestMapping("/admin/customer/faq/delete")
    public @ResponseBody boolean deleteFaqList(@ModelAttribute("params") AdminFaqDTO params, Model model, HttpServletResponse response, HttpServletRequest request) {
    	try {
    		String checkList[] = request.getParameterValues("checkList");
    		log.info("check==========="+checkList);
    		ArrayList<String> list = new ArrayList<>();
     		for(int i=0; i<checkList.length; i++) {
    			list.add(checkList[i]);
    		}
    		
    		Map<String, Object> paramMap = new HashMap<String, Object>();
    		paramMap.put("list", list);
    		
    		boolean isDeleted = adminFaqService.deleteFaq(paramMap);
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
	
	@RequestMapping(value = "/admin/customer/faqScore/table")
	 public @ResponseBody JsonObject getFaqScoreList(@ModelAttribute("params") AdminFaqScoreDTO params, Model model,
			 @RequestParam Map<String, Object> commandMap) {

		JsonObject jsonObj = new JsonObject();
		List<AdminFaqScoreDTO> faqScoreList = adminFaqService.getFaqScoreList(commandMap);
		if (CollectionUtils.isEmpty(faqScoreList ) == false) {
			Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new GsonLocalDateTimeAdapter()).create();
			JsonArray jsonArr = gson.toJsonTree(faqScoreList ).getAsJsonArray();
			jsonObj.add("data", jsonArr);
		}
		
		model.addAttribute("faqScoreList ", faqScoreList);

		return jsonObj;
	 }
	
	@RequestMapping(value = "/admin/customer/support/table")
	 public @ResponseBody JsonObject getSupportlList(@ModelAttribute("params") AdminFaqScoreDTO params, Model model,
			 @RequestParam Map<String, Object> commandMap) {

		JsonObject jsonObj = new JsonObject();
		List<AdminSupportDTO> supportList = adminFaqService.getSupportList(commandMap);
		if (CollectionUtils.isEmpty(supportList ) == false) {
			Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new GsonLocalDateTimeAdapter()).create();
			JsonArray jsonArr = gson.toJsonTree(supportList ).getAsJsonArray();
			jsonObj.add("data", jsonArr);
		}
		
		model.addAttribute("supportList ", supportList);

		return jsonObj;
	 }
	
	@RequestMapping("/admin/customer/support/delete")
    public @ResponseBody boolean deleteSupportList(@ModelAttribute("params") AdminSupportDTO params, Model model, HttpServletResponse response, HttpServletRequest request) {
    	try {
    		String checkList[] = request.getParameterValues("checkList");
    		log.info("check==========="+checkList);
    		ArrayList<String> list = new ArrayList<>();
     		for(int i=0; i<checkList.length; i++) {
    			list.add(checkList[i]);
    		}
    		
    		Map<String, Object> paramMap = new HashMap<String, Object>();
    		paramMap.put("list", list);
    		
    		boolean isDeleted = adminFaqService.deleteSupport(paramMap);
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
	
	@GetMapping(value = "/admin/customer/supportDetail")
    public String getSupportDetail(@ModelAttribute("params") AdminSupportDTO params, HttpServletRequest req, Model model)throws Exception{
		AdminSupportDTO supportDto = adminFaqService.getSupport(params);
		
		model.addAttribute("support", supportDto);
		
		return "admin/customer/supportDetail";
    }
	
	@PostMapping(value = "/admin/customer/registerSupportDetail")
	public String saveSupportDetail(@ModelAttribute("params") final AdminSupportDTO params, Model model) {
		Map<String, Object> pagingParams = getPagingParams(params);
		try {
			boolean isRegistered = adminFaqService.registerSupportDetail(params);
			if (isRegistered == false) {
				return showMessageWithRedirect("게시글 등록에 실패하였습니다.", "/admin/customer/support", Method.GET, pagingParams, model);
			}
		} catch (DataAccessException e) {
			return showMessageWithRedirect("데이터베이스 처리 과정에 문제가 발생하였습니다.", "/admin/customer/support", Method.GET, pagingParams, model);

		} catch (Exception e) {
			return showMessageWithRedirect("시스템에 문제가 발생하였습니다.", "/admin/customer/support", Method.GET, pagingParams, model);
		}

		return showMessageWithRedirect("게시글 등록이 완료되었습니다.", "/admin/customer/support", Method.GET, null, model);
	}
	
}
