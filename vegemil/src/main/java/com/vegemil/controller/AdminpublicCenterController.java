package com.vegemil.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import com.vegemil.domain.AdminAviCFDTO;
import com.vegemil.domain.AdminMediaNewsDTO;
import com.vegemil.domain.AdminRadioCMDTO;
import com.vegemil.domain.AdminVideoContestDTO;
import com.vegemil.domain.DataTableDTO;
import com.vegemil.service.AdminAdEtcService;
import com.vegemil.service.AdminAviCFService;
import com.vegemil.service.AdminAviRadioCMService;
import com.vegemil.service.AdminPublicCenterService;
import com.vegemil.service.AdminVideoContestService;
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
	
	@Autowired 
	private AdminVideoContestService adminVideoContestService;
	
	@Autowired
	private AdminAviRadioCMService adminRadioCMSerive;
	
	@Autowired
	private AdminAviCFService adminAviCFService;
	
	@Value("${spring.servlet.multipart.location}")
    private String uploadPath;
	
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
	
	//===========================기타 영상 자료===========================//
	
	//기타영상자료 메인
	@GetMapping("/publicCenter/adEtcList")
	public String getaAdEtcList(Model model) {
		return "admin/publicCenter/adEtcList";
	}
	
	//기타영상 리스트  
	@RequestMapping(value = "/publicCenter/getAdEtcList")
    public @ResponseBody JsonObject getAdEtcList(@ModelAttribute("params") final AdminAdEctDTO params, HttpServletRequest req, 
    			Map<String, Object> commandMap)throws Exception{
		
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
	
	//===========================공모전 동영상===========================//
	
	//공모전동영상 메인
	@GetMapping("/publicCenter/videoContestList")
	public String getaVideoContestList(Model model) {
		return "admin/publicCenter/videoContestList";
	}
	
	// 공모전동영상 리스트  
	@RequestMapping(value = "/publicCenter/getvideoContestList")
    public @ResponseBody JsonObject getvideoContestList(@ModelAttribute("params") final AdminVideoContestDTO params, HttpServletRequest req, 
    			Map<String, Object> commandMap)throws Exception{
		System.out.println("params::" + params);
		List<AdminVideoContestDTO> videoContestList = adminVideoContestService.getAdminVideoContestList(params);
		JsonObject jsonObj = new JsonObject();
		if (CollectionUtils.isEmpty(videoContestList) == false) {
			Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new GsonLocalDateTimeAdapter()).create();
			JsonArray jsonArr = gson.toJsonTree(videoContestList).getAsJsonArray();
			jsonObj.add("data", jsonArr);
		}
		
		return jsonObj;
    }
	
	// 공모전동영상 등록 페이지 ㅇ동
	@GetMapping("/publicCenter/postVideoContest")
	public String getPostVideoContestView() {
		return "admin/publicCenter/videoContestPost";
	}
	
	// 새로운 공모전영상 데이터 등록
	@PostMapping("/publicCenter/postVideoContest")
	 public @ResponseBody Map<String, Object> saveNewVideoContest(@ModelAttribute("params") final AdminVideoContestDTO params,
	    		@RequestParam(value="uploadFile", required=false) MultipartFile uploadFile ) throws Exception{
		
		System.out.println("*******"+params);
		Map<String, Object> rtnMsg = new HashMap<String, Object>();
		
		try {
			boolean isResulted = adminVideoContestService.saveVideoContest(params, uploadFile);
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
	
	// 공모전 영상 수정 삭제
	@RequestMapping(value="/publicCenter/saveVideoContest")
    public @ResponseBody Map<String, Object> saveVideoContest(@ModelAttribute("params") final AdminVideoContestDTO params,
    		@RequestParam(value="uploadFile", required=false) MultipartFile uploadFile )throws Exception{
		
		Map<String, Object> rtnMsg = new HashMap<String, Object>();
		
		try {
			boolean isResulted = adminVideoContestService.saveVideoContest(params, uploadFile);
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
	
	// 공모전 영상 노출여부 변경
	@RequestMapping(value="/publicCenter/changeContestOnairStatus")
    public @ResponseBody Map<String, Object> getChangeContestOnairStatus(@ModelAttribute("params") final AdminVideoContestDTO params)throws Exception{
		Map<String, Object> rtnMsg = new HashMap<String, Object>();
		
		try {
			boolean isResulted = adminVideoContestService.changeOnairStatus(params);
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
	
	// 공모전 영상 수정 화면 이동
	@GetMapping("/publicCenter/videoContestUpdate")
	public String getvideoContestView(@RequestParam("tIdx") String tIdx, Model model) {
		
		model.addAttribute("videoContest", adminVideoContestService.getAdminContestVideoData(tIdx));
		return "admin/publicCenter/videoContestUpdate";
	}
	
	//===========================라디오 씨엠 영상===========================//
	
	// 라디오 씨엠 메인
	@GetMapping("/publicCenter/radioCMList")
	public String getaradioCMList(Model model) {
		return "admin/publicCenter/redioCMList";
	}
	
	// 라디오 씨엠 리스트  
	@RequestMapping(value = "/publicCenter/getRadioCMList")
    public @ResponseBody JsonObject getvideoContestList(@ModelAttribute("params") final AdminRadioCMDTO params, HttpServletRequest req, 
    			Map<String, Object> commandMap)throws Exception{
		System.out.println("params::" + params);
		List<AdminRadioCMDTO> radioCMList = adminRadioCMSerive.getRadioCMList(params);
		JsonObject jsonObj = new JsonObject();
		if (CollectionUtils.isEmpty(radioCMList) == false) {
			Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new GsonLocalDateTimeAdapter()).create();
			JsonArray jsonArr = gson.toJsonTree(radioCMList).getAsJsonArray();
			jsonObj.add("data", jsonArr);
		}
		return jsonObj;
    }
	
	// 라디오 씨엠 등록 페이지 ㅇ동
	@GetMapping("/publicCenter/radioCMPost")
	public String getPostRadioCMView() {
		return "admin/publicCenter/radioCMPost";
	}
	
	// 라디오 씨엠 수정 화면 이동
	@GetMapping("/publicCenter/radioCMUpdate")
	public String getRadioCMUpdateView(@RequestParam("tIdx") String tIdx, Model model) {
		
		model.addAttribute("radioCM", adminRadioCMSerive.getRadioCMData(tIdx));
		return "admin/publicCenter/radioCMUpdate";
	}
	
	// 새로운 라디오 씨엠 데이터 등록 수정 삭제
	@RequestMapping(value="/publicCenter/saveRadioCM")
	public @ResponseBody Map<String, Object> saveRadioCM(@ModelAttribute("params") final AdminRadioCMDTO params,
	    		@RequestParam(value="uploadFile", required=false) MultipartFile uploadFile ) throws Exception{
		
		System.out.println("*******"+params);
		Map<String, Object> rtnMsg = new HashMap<String, Object>();
		
		try {
			boolean isResulted = adminRadioCMSerive.saveRadioCM(params, uploadFile);
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
	// 라디오 씨엠 영상 노출여부 변경
	@RequestMapping(value="/publicCenter/changeRadioCMOnairStatus")
    public @ResponseBody Map<String, Object> getChangeRadioCMOnairStatus(@ModelAttribute("params") final AdminRadioCMDTO params)throws Exception{
		Map<String, Object> rtnMsg = new HashMap<String, Object>();
		
		try {
			boolean isResulted = adminRadioCMSerive.changeOnairStatus(params);
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
	
	//===========================CF영상===========================//
	
	// CF영상 메인
	@GetMapping("/publicCenter/aviCFList")
	public String getAviCFListList(Model model) {
		return "admin/publicCenter/aviCFList";
	}
	
	// CF영상 리스트  
	@RequestMapping(value = "/publicCenter/getAviCFList")
	public @ResponseBody JsonObject getAviCFList(@ModelAttribute("params") final AdminAviCFDTO params, HttpServletRequest req, 
			Map<String, Object> commandMap)throws Exception{
		System.out.println("params::" + params);
		List<AdminAviCFDTO> aviCFList = adminAviCFService.getAdminAviCFList(params);
		JsonObject jsonObj = new JsonObject();
		if (CollectionUtils.isEmpty(aviCFList) == false) {
			Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new GsonLocalDateTimeAdapter()).create();
			JsonArray jsonArr = gson.toJsonTree(aviCFList).getAsJsonArray();
			jsonObj.add("data", jsonArr);
		}
		return jsonObj;
	}
	
	// CF영상 등록 페이지 ㅇ동
	@GetMapping("/publicCenter/aviCFPost")
	public String getPostAviCFView() {
		return "admin/publicCenter/aviCFPost";
	}
	
	// CF영상 수정 화면 이동
	@GetMapping("/publicCenter/aviCFUpdate")
	public String getAviCFUpdateView(@RequestParam("tIdx") String tIdx, Model model) {
		
		model.addAttribute("aviCF", adminAviCFService.getAdminAviCFData(tIdx));
		return "admin/publicCenter/aviCFUpdate";
	}
	
	// CF영상 데이터 등록 수정 삭제
	@RequestMapping(value="/publicCenter/saveAviCF")
	public @ResponseBody Map<String, Object> saveAviCF(@ModelAttribute("params") final AdminAviCFDTO params,
			@RequestParam(value="uploadFile", required=false) MultipartFile uploadFile ) throws Exception{
		
		System.out.println("*******"+params);
		Map<String, Object> rtnMsg = new HashMap<String, Object>();
		
		try {
			boolean isResulted = adminAviCFService.saveAviCF(params, uploadFile);
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
	
	// CF영상 노출여부 변경
	@RequestMapping(value="/publicCenter/changeAviCFOnairStatus")
	public @ResponseBody Map<String, Object> getChangeAviCFOnairStatus(@ModelAttribute("params") final AdminAviCFDTO params)throws Exception{
		Map<String, Object> rtnMsg = new HashMap<String, Object>();
		
		try {
			boolean isResulted = adminAviCFService.changeOnairStatus(params);
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
}