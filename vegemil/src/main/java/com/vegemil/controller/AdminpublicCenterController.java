package com.vegemil.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
import com.vegemil.domain.AdminAdEctDTO;
import com.vegemil.domain.AdminMediaNewsDTO;
import com.vegemil.domain.DataTableDTO;
import com.vegemil.service.AdminAdEtcService;
import com.vegemil.service.AdminPublicCenterService;
import com.vegemil.util.UiUtils;

import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
@RequestMapping("/admin/manage")
public class AdminpublicCenterController extends UiUtils {

	@Autowired
	private AdminPublicCenterService adminPublicCenterService;
	
	@Autowired
	private AdminAdEtcService adminAdEtcService;
	

	// 보도자료 등록 페이지
	@GetMapping("/publicCenter/mediaNewsAdd")
	public String moveMediaNewsAddPage(@RequestParam(value = "mIdx", required = false) Long mIdx,
			HttpServletRequest req, Model model) throws Exception {

		AdminMediaNewsDTO mediaNewsDto = adminPublicCenterService.getMediaNewsDetail(mIdx);
		model.addAttribute("mediaNewsDto", mediaNewsDto);

		return "admin/publicCenter/mediaNewsAdd";
	}

	// 보도자료 등록
	@PostMapping("/publicCenter/mediaNewsAdd")
	@ResponseBody
	public Map<String, Object> saveMediaNews(@ModelAttribute("params") final AdminMediaNewsDTO params, Model model,
			HttpServletResponse response, HttpServletRequest request) throws Exception {

		Map<String, Object> rtnMap = new HashMap<String, Object>();

		try {
			boolean isRegistered = adminPublicCenterService.registerMediaNews(params);
			rtnMap.put("result", isRegistered);
		} catch (DataAccessException e) {
		} catch (Exception e) {
		}

		return rtnMap;
	}

	// 보도자료 수정 페이지
	@GetMapping("/publicCenter/mediaNewsUpdate")
	public String moveMediaNewsUpdatePage(@RequestParam(value = "mIdx", required = false) Long mIdx,
			HttpServletRequest req, Model model) throws Exception {

		AdminMediaNewsDTO mediaNewsDto = adminPublicCenterService.getMediaNewsDetail(mIdx);
		model.addAttribute("mediaNewsDto", mediaNewsDto);

		return "admin/publicCenter/mediaNewsUpdate";
	}

	// 보도자료 수정
	@PostMapping("/publicCenter/mediaNewsUpdate")
	@ResponseBody
	public Map<String, Object> updateMediaNews(@ModelAttribute("params") final AdminMediaNewsDTO params, Model model,
			HttpServletResponse response, HttpServletRequest request) throws Exception {

		// MultipartFile deleteFile = params.getFileName();
		String deleteFile = params.getMImg();

		Map<String, Object> rtnMap = new HashMap<String, Object>();

		try {
			boolean isRegistered = adminPublicCenterService.updateMediaNews(params);
			rtnMap.put("result", isRegistered);
		} catch (DataAccessException e) {
		} catch (Exception e) {
		}

		return rtnMap;
	}

	// 보도자료 조회
	@GetMapping("/publicCenter/mediaNews")
	public String moveMediaNewsPage() {
		return "admin/publicCenter/mediaNews";
	}

	@RequestMapping(value = "/publicCenter/mediaNewsList")
	public @ResponseBody DataTableDTO getSaboSubscribeList(@ModelAttribute("params") AdminMediaNewsDTO params,
			Model model, @RequestParam Map<String, Object> commandMap) {

		DataTableDTO dataTableDto = adminPublicCenterService.getMediaNewsList(commandMap);
		return dataTableDto;
	}

	@RequestMapping(value = "/publicCenter/deleteMediaNews", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody boolean deleteMediaNews(@ModelAttribute("params") AdminMediaNewsDTO params, Model model,
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

			boolean isDeleted = adminPublicCenterService.deleteMediaNews(paramMap);
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
	
	
	//기타영상자료 메인
	@GetMapping("/publicCenter/adEtcList")
	public String getaAdEtcList(Model model) {
		return "admin/publicCenter/adEtcList";
	}
	
	//기타영상 리스트  
	@RequestMapping(value = "/publicCenter/getAdEtcList")
    public @ResponseBody JsonObject getAdEtcList(@ModelAttribute("params") final AdminAdEctDTO params, HttpServletRequest req, 
    			Map<String, Object> commandMap)throws Exception{
		System.out.println("params::" + params);
		List<AdminAdEctDTO> adEtcList = adminAdEtcService.getAdminAdEtcList(params);
		JsonObject jsonObj = new JsonObject();
		if (CollectionUtils.isEmpty(adEtcList) == false) {
			Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new GsonLocalDateTimeAdapter()).create();
			JsonArray jsonArr = gson.toJsonTree(adEtcList).getAsJsonArray();
			jsonObj.add("data", jsonArr);
		}
		
		return jsonObj;
    }
	
	
	//기타영상 자료 수정 삭제 
	@RequestMapping(value="/publicCenter/saveAdEtc")
    public @ResponseBody Map<String, Object> saveAdEtc(@ModelAttribute("params") final AdminAdEctDTO params,
    		@RequestParam(value="uploadFile", required=false) MultipartFile uploadFile )throws Exception{
		
		Map<String, Object> rtnMsg = new HashMap<String, Object>();
		
		try {
			boolean isResulted = adminAdEtcService.saveAdEtc(params, uploadFile);
			rtnMsg.put("result", isResulted);
		}catch (DataAccessException e) {
    		e.printStackTrace();
    		throw new Exception("저장에 실패하였습니다");
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("저장에 실패하였습니다");
		}
		return rtnMsg;
    }
	
	//기타영상 노출여부 변경
	@RequestMapping(value="/publicCenter/changeOnairStatus")
    public @ResponseBody Map<String, Object> getChangeOnairStatus(@ModelAttribute("params") final AdminAdEctDTO params)throws Exception{
		Map<String, Object> rtnMsg = new HashMap<String, Object>();
		
		try {
			boolean isResulted = adminAdEtcService.changeOnairStatus(params);
			rtnMsg.put("result", isResulted);
		}catch (DataAccessException e) {
    		e.printStackTrace();
    		throw new Exception("저장에 실패하였습니다");
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("저장에 실패하였습니다");
		}
		
		return rtnMsg;
    }
	
	// 기타 영상자료 등록 페이지 ㅇ동
	@GetMapping("/publicCenter/postAdEtc")
	public String getPostAdEtcView() {
		return "admin/publicCenter/adEtcPost";
	}
	
	// 새로운 기타홍보영상 등록
	@PostMapping("/publicCenter/postAdEtc")
    public @ResponseBody Map<String, Object> saveAdEtc22(@ModelAttribute("params") final AdminAdEctDTO params,
    		@RequestParam(value="uploadFile", required=false) MultipartFile uploadFile ) throws Exception{
		Map<String, Object> rtnMsg = new HashMap<String, Object>();
		
		try {
			boolean isResulted = adminAdEtcService.saveAdEtc(params, uploadFile);
			rtnMsg.put("result", isResulted);
			
		}catch (DataAccessException e) {
    		e.printStackTrace();
    		throw new Exception("저장에 실패하였습니다");
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("저장에 실패하였습니다");
		}
		return rtnMsg;
	}
	
	// 기타홍보영상 수정 화면 이동
	@GetMapping("/publicCenter/adEtcUpdate")
	public String getUpdateAdEtcView(@RequestParam("tIdx") String tIdx, Model model) {
		
		model.addAttribute("adEtc", adminAdEtcService.getAdminEtcData(tIdx));
		return "admin/publicCenter/adEtcUpdate";
	}
	
	
	
}