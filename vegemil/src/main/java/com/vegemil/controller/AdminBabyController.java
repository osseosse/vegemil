package com.vegemil.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
import com.vegemil.domain.AdminBabyDTO;
import com.vegemil.domain.AdminBestReviewDTO;
import com.vegemil.domain.AdminCalendarModelDTO;
import com.vegemil.domain.AdminSampleBabyDTO;
import com.vegemil.domain.DataTableDTO;
import com.vegemil.service.AdminBabyService;
import com.vegemil.util.UiUtils;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
public class AdminBabyController extends UiUtils {

	@Autowired
	private AdminBabyService adminBabyService;
	
	@Value("${spring.servlet.multipart.location}")
    private String uploadPath;
	
	@RequestMapping(value = "/admin/manage/baby/{viewName}")
    public String adminMoveCustomer(@PathVariable(value = "viewName", required = false) String viewName)throws Exception{
		return "admin/baby/"+viewName;
    }
	
	@RequestMapping(value = "/admin/manage/baby/babyInfoListData")
	public @ResponseBody DataTableDTO getBabyInfoList(@ModelAttribute("params") AdminBabyDTO params, Model model,
			@RequestParam Map<String, Object> commandMap) {
		
		DataTableDTO dataTableDto = adminBabyService.getBabyInfoList(commandMap);
		return dataTableDto;
	}
	
	@PostMapping(value = "/admin/manage/baby/registerBabyInfo")
	@ResponseBody
	public Map<String, Object> saveBabyInfo(@ModelAttribute("params") final AdminBabyDTO params, @RequestParam(value="uploadFile", required=false) MultipartFile uploadFile, 
			Model model, HttpServletResponse response, HttpServletRequest request) throws Exception {
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		try {
			String originalName = uploadFile.getOriginalFilename();
			if(!"".equals(originalName)) {
				String ext = originalName.substring(originalName.lastIndexOf("\\") + 1);
				String uuid = UUID.randomUUID().toString();
				String savefileName = uuid + "_" + ext;
				Path savePath = Paths.get(uploadPath + "/Admin/summerNote/" + savefileName);
				params.getFileName1().transferTo(savePath);
				params.setMbsImage(savefileName);
			}
			
			boolean isRegistered = adminBabyService.registerBabyInfo(params);
			rtnMap.put("result", isRegistered);
		} catch (DataAccessException e) {
			log.error("fail to process file", e);
		} catch (Exception e) {
			log.error("fail to process file", e);
		}
		

		return rtnMap;
	}
	
	@GetMapping(value = "/admin/manage/baby/babyInfoDetail")
	public String openBabyInfoDetail(@ModelAttribute("params") AdminBabyDTO params, @RequestParam(value = "mbsIdx", required = false) Long mbsIdx, Model model, HttpServletResponse response) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		if (mbsIdx == null) {
			out.println("<script>alert('???????????? ?????? ???????????????.'); history.go(-1);</script>");
			out.flush();
			return showMessageWithRedirect("???????????? ?????? ???????????????.", "admin/baby/babyInfoList", Method.GET, null, model);
		}
		AdminBabyDTO babyInfo = adminBabyService.getBabyInfoDetail(mbsIdx);
		if (babyInfo == null) {
			out.println("<script>alert('?????? ?????????????????? ?????? ????????? ??????????????????.'); history.go(-1);</script>");
			out.flush();
			return showMessageWithRedirect("?????? ?????????????????? ?????? ????????? ??????????????????.", "admin/baby/babyInfoList", Method.GET, null, model);
		}
		babyInfo.setMbsIdx(mbsIdx);
		model.addAttribute("babyInfo", babyInfo);

		return "admin/baby/babyInfoDetail";
	}
	
	@PostMapping(value = "/admin/manage/baby/babyInfoUpdate")
	public String updateBabyInfo(@ModelAttribute("params") final AdminBabyDTO params, @RequestParam("fileName") MultipartFile fileName, Model model, HttpServletResponse response, HttpServletRequest request) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		try {
				if(!fileName.getOriginalFilename().equals("")) {
					String originalName = fileName.getOriginalFilename();
					String file = originalName.substring(originalName.lastIndexOf("\\") + 1);
					String uuid = UUID.randomUUID().toString();
					String savefileName = uuid + "_" + file;
					Path savePath = Paths.get(request.getSession().getServletContext().getRealPath("/..") + "/WEB-INF/classes/static/upload/admin/babyInfo/" + savefileName);
					
					fileName.transferTo(savePath);
					params.setMbsImage(savefileName);
				}
			boolean isRegistered = adminBabyService.registerBabyInfo(params);
			if (isRegistered == false) {
				out.println("<script>alert('????????? ????????? ?????????????????????.'); history.go(-1);</script>");
				out.flush();
			}
		} catch (DataAccessException e) {
			out.println("<script>alert('?????????????????? ?????? ????????? ????????? ?????????????????????.'); history.go(-1);</script>");
			out.flush();
			return showMessageWithRedirect("?????????????????? ?????? ????????? ????????? ?????????????????????.", "/admin/baby/babyInfoList", Method.GET, null, model);

		} catch (Exception e) {
			out.println("<script>alert('???????????? ????????? ?????????????????????.'); history.go(-1);</script>");
			out.flush();
			return showMessageWithRedirect("???????????? ????????? ?????????????????????.", "/admin/baby/babyInfoList", Method.GET, null, model);
		}
		
		out.println("<script>alert('????????? ????????? ?????????????????????.'); window.location='/admin/baby/babyInfoList';</script>");
		out.flush();

		return showMessageWithRedirect("????????? ????????? ?????????????????????.", "/admin/baby/babyInfoList", Method.GET, null, model);
	}
	
	@GetMapping(value = "/admin/manage/baby/displayBabyInfo")
	public @ResponseBody boolean displayBabyInfo(@RequestParam(value = "mbsIdx", required = false) Long mbsIdx, 
			@RequestParam(value = "mbsActive", required = false) int mbsActive, @RequestParam(value = "mbsCheck", required = false) int mbsCheck,
			HttpServletResponse response) throws Exception {
		boolean isRegistered = true;
		try {
			if (mbsIdx == null) {
				return false;
			}
			AdminBabyDTO babyInfo = adminBabyService.getBabyInfoDetail(mbsIdx);
			babyInfo.setMbsIdx(mbsIdx);
			babyInfo.setMbsActive(mbsActive);
			babyInfo.setMbsCheck(mbsCheck);
			isRegistered = adminBabyService.registerBabyInfo(babyInfo);
			if (!isRegistered) {
				throw new IOException("????????? ?????????????????????.");
			}
		}catch (DataAccessException e) {
    		e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isRegistered;
	}
	
	@GetMapping(value = "/admin/manage/baby/babyInfoCheck")
	public void babyInfoCheck(@RequestParam(value = "mbsIdx", required = false) Long mbsIdx, @RequestParam(value = "display", required = false) int display, HttpServletResponse response) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		if (mbsIdx == null) {
			out.println("<script>alert('???????????? ?????? ???????????????.'); history.go(-1);</script>");
			out.flush();
		}
		AdminBabyDTO babyInfo = adminBabyService.getBabyInfoDetail(mbsIdx);
		babyInfo.setMbsIdx(mbsIdx);
		babyInfo.setMbsCheck(display);
		boolean isRegistered = adminBabyService.registerBabyInfo(babyInfo);
		if (isRegistered == false) {
			out.println("<script>alert('???????????? ?????? ????????? ?????????????????????.'); window.location='/admin/baby/babyInfoList';</script>");
			out.flush();
		}
	}
	
	@RequestMapping("/admin/manage/baby/deleteBabyInfo")
    public @ResponseBody boolean deleteBabyInfo(@ModelAttribute("params") AdminBabyDTO params, Model model, HttpServletResponse response, HttpServletRequest request) {
    	try {
    		String checkList[] = request.getParameterValues("checkList");
    		log.info("check==========="+checkList);
    		ArrayList<String> list = new ArrayList<>();
     		for(int i=0; i<checkList.length; i++) {
    			list.add(checkList[i]);
    		}
    		
    		Map<String, Object> paramMap = new HashMap<String, Object>();
    		paramMap.put("list", list);
    		
    		boolean isDeleted = adminBabyService.deleteBabyInfo(paramMap);
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
	
	@RequestMapping(value = "/admin/manage/baby/babyInfoAdd")
    public String openBabyInfoAdd(@RequestParam(value = "mbsIdx", required = false) Long mbsIdx, HttpServletRequest req, Model model)throws Exception{
		AdminBabyDTO babyDto = adminBabyService.getBabyInfoDetail(mbsIdx);
		
		model.addAttribute("babyInfo", babyDto);
		
		return "admin/baby/babyInfoAdd";
    }
	
	@PostMapping(value = "/admin/manage/baby/uploadBabyInfo")
	@ResponseBody
	public Map<String, Object> uploadBabyInfo(@ModelAttribute("params") final AdminBabyDTO params, Model model, @RequestParam(value="uploadFile", required=false) MultipartFile uploadFile
			, HttpServletResponse response, HttpServletRequest request) {
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		try {
			String originalName = uploadFile.getOriginalFilename();
			String file = originalName.substring(originalName.lastIndexOf("\\") + 1);
			String uuid = UUID.randomUUID().toString();
			String savefileName = uuid + "_" + file;
			Path savePath = Paths.get(uploadPath + "/Admin/summerNote/" + savefileName);
			params.getFileName1().transferTo(savePath);
			params.setMbsImage(savefileName);

			rtnMap.put("uploadPath", savePath);
			rtnMap.put("uuid", uuid);
			rtnMap.put("fileName", originalName);
		} catch(IOException e) {
			e.printStackTrace();
		}


		return rtnMap;
	}
	
	@RequestMapping(value = "/admin/manage/baby/babyQnaListData")
	public @ResponseBody JsonObject getBabyQnaList(@ModelAttribute("params") AdminBabyDTO params, Model model,
			@RequestParam Map<String, Object> commandMap) {
		
		List<AdminBabyDTO> babyInfoList = adminBabyService.getBabyQnaList(params);
		JsonObject jsonObj = new JsonObject();
		if (CollectionUtils.isEmpty(babyInfoList) == false) {
			Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new GsonLocalDateTimeAdapter()).create();
			JsonArray jsonArr = gson.toJsonTree(babyInfoList).getAsJsonArray();
			jsonObj.add("data", jsonArr);
		}

		return jsonObj;
	}
	
	@GetMapping(value = "/admin/manage/baby/displayBabyQna")
	public @ResponseBody boolean displayBabyQna(@RequestParam(value = "mbsIdx", required = false) Long mbsIdx, 
			@RequestParam(value = "mbsActive", required = false) int mbsActive, @RequestParam(value = "mbsCheck", required = false) int mbsCheck,
			HttpServletResponse response) throws Exception {
		boolean isRegistered = true;
		try {
			if (mbsIdx == null) {
				return false;
			}
			AdminBabyDTO babyInfo = adminBabyService.getBabyInfoDetail(mbsIdx);
			babyInfo.setMbsIdx(mbsIdx);
			babyInfo.setMbsActive(mbsActive);
			babyInfo.setMbsCheck(mbsCheck);
			isRegistered = adminBabyService.registerBabyInfo(babyInfo);
			if (!isRegistered) {
				throw new IOException("????????? ?????????????????????.");
			}
		}catch (DataAccessException e) {
    		e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isRegistered;
	}
	
//	@GetMapping(value = "/admin/manage/baby/babyQnaList")
//	public String openBabyQnaList(@ModelAttribute("params") AdminBabyDTO params, Model model) {
//		List<AdminBabyDTO> babyQnaList = adminBabyService.getBabyQnaList(params);
//		model.addAttribute("babyQnaList", babyQnaList);
//
//		return "admin/baby/babyQnaList";
//	}
	
	@PostMapping(value = "/admin/manage/baby/registerBabyQna")
	@ResponseBody
	public Map<String, Object> saveBabyQna(@ModelAttribute("params") final AdminBabyDTO params, @RequestParam("uploadFile") MultipartFile uploadFile, 
			Model model, HttpServletResponse response, HttpServletRequest request) throws Exception {
		
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		try {
			String originalName = uploadFile.getOriginalFilename();
			if(!"".equals(originalName)) {
				String file = originalName.substring(originalName.lastIndexOf("\\") + 1);
				String uuid = UUID.randomUUID().toString();
				String savefileName = uuid + "_" + file;
				
				Path savePath = Paths.get(uploadPath + "/Admin/summerNote/" + savefileName);
				params.getFileName1().transferTo(savePath);
				params.setMbsImage(savefileName);
			}
			boolean isRegistered = adminBabyService.registerBabyQna(params);
			rtnMap.put("result", isRegistered);
		} catch (DataAccessException e) {

		} catch (Exception e) {
			
		}
		
		return rtnMap;
	}
	
	@GetMapping(value = "/admin/manage/baby/babyQnaDetail")
	public String openBabyQnaDetail(@ModelAttribute("params") AdminBabyDTO params, @RequestParam(value = "mbsIdx", required = false) Long mbsIdx, Model model, HttpServletResponse response) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		if (mbsIdx == null) {
			out.println("<script>alert('???????????? ?????? ???????????????.'); history.go(-1);</script>");
			out.flush();
			return showMessageWithRedirect("???????????? ?????? ???????????????.", "admin/baby/babyQnaList", Method.GET, null, model);
		}
		AdminBabyDTO babyQna = adminBabyService.getBabyQnaDetail(mbsIdx);
		if (babyQna == null) {
			out.println("<script>alert('?????? ?????????????????? ?????? ????????? ??????????????????.'); history.go(-1);</script>");
			out.flush();
			return showMessageWithRedirect("?????? ?????????????????? ?????? ????????? ??????????????????.", "admin/baby/babyQnaList", Method.GET, null, model);
		}
		babyQna.setMbsIdx(mbsIdx);
		model.addAttribute("babyQna", babyQna);

		return "admin/baby/babyQnaDetail";
	}
	
	@PostMapping(value = "/admin/manage/baby/babyQnaUpdate")
	public String updateBabyQna(@ModelAttribute("params") final AdminBabyDTO params, @RequestParam("fileName") MultipartFile fileName, 
			Model model, HttpServletResponse response, HttpServletRequest request) throws Exception {
		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		try {
			if(!fileName.getOriginalFilename().equals("")) {
				String originalName = fileName.getOriginalFilename();
				String file = originalName.substring(originalName.lastIndexOf("\\") + 1);
				String uuid = UUID.randomUUID().toString();
				String savefileName = uuid + "_" + file;
				Path savePath = Paths.get(request.getSession().getServletContext().getRealPath("/") + "/WEB-INF/classes/static/upload/admin/babyQna/" + savefileName);
					
				fileName.transferTo(savePath);
				params.setMbsImage(savefileName);
			}
			boolean isRegistered = adminBabyService.registerBabyQna(params);
			if (isRegistered == false) {
				out.println("<script>alert('????????? ????????? ?????????????????????.'); history.go(-1);</script>");
				out.flush();
			}
		} catch (DataAccessException e) {
			out.println("<script>alert('?????????????????? ?????? ????????? ????????? ?????????????????????.'); history.go(-1);</script>");
			out.flush();
			return showMessageWithRedirect("?????????????????? ?????? ????????? ????????? ?????????????????????.", "/admin/baby/babyQnaList", Method.GET, null, model);

		} catch (Exception e) {
			out.println("<script>alert('???????????? ????????? ?????????????????????.'); history.go(-1);</script>");
			out.flush();
			return showMessageWithRedirect("???????????? ????????? ?????????????????????.", "/admin/baby/babyQnaList", Method.GET, null, model);
		}
		
		out.println("<script>alert('????????? ????????? ?????????????????????.'); window.location='/admin/baby/babyQnaList';</script>");
		out.flush();

		return showMessageWithRedirect("????????? ????????? ?????????????????????.", "/admin/baby/babyQnaList", Method.GET, null, model);
	}
	
	@GetMapping(value = "/admin/manage/baby/babyQnaActive")
	public void updateQnaActive(@RequestParam(value = "mbsIdx", required = false) Long mbsIdx, 
			@RequestParam(value = "display", required = false) int display, HttpServletResponse response) throws Exception {
		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		if (mbsIdx == null) {
			out.println("<script>alert('???????????? ?????? ???????????????.'); history.go(-1);</script>");
			out.flush();
		}
		AdminBabyDTO babyQna = adminBabyService.getBabyQnaDetail(mbsIdx);
		babyQna.setMbsIdx(mbsIdx);
		babyQna.setMbsActive(display);
		boolean isRegistered = adminBabyService.registerBabyQna(babyQna);
		if (isRegistered == false) {
			out.println("<script>alert('???????????? ?????? ????????? ?????????????????????.'); window.location='/admin/baby/babyQnaList';</script>");
			out.flush();
		}
	}
	
	@GetMapping(value = "/admin/manage/baby/babyQnaCheck")
	public void updateQnaCheck(@RequestParam(value = "mbsIdx", required = false) Long mbsIdx, 
			@RequestParam(value = "display", required = false) int display, HttpServletResponse response) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		if (mbsIdx == null) {
			out.println("<script>alert('???????????? ?????? ???????????????.'); history.go(-1);</script>");
			out.flush();
		}
		AdminBabyDTO babyQna = adminBabyService.getBabyQnaDetail(mbsIdx);
		babyQna.setMbsIdx(mbsIdx);
		babyQna.setMbsCheck(display);
		boolean isRegistered = adminBabyService.registerBabyQna(babyQna);
		if (isRegistered == false) {
			out.println("<script>alert('???????????? ?????? ????????? ?????????????????????.'); window.location='/admin/baby/babyQnaList';</script>");
			out.flush();
		}
	}
	
	
	
	@RequestMapping(value = "/admin/manage/baby/calendarModelList")
	 public @ResponseBody DataTableDTO getCalenModelList(@ModelAttribute("params") AdminCalendarModelDTO params, Model model, 
			 @RequestParam Map<String, Object> commandMap) {

		DataTableDTO dataTableDto = adminBabyService.getCalendarModelList(commandMap);
		return dataTableDto;
	 }
	
	@RequestMapping(value = "/admin/manage/baby/updateCalendarModel")
	public @ResponseBody Map<String, Object> updateCalendarModelList(@ModelAttribute("params") final AdminCalendarModelDTO params, 
			Model model, HttpServletResponse response) {
		
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		
		try {
			boolean isUpdate = adminBabyService.registerCalendarModel(params);
			rtnMap.put("result", isUpdate);
		} catch (DataAccessException e) {
			log.error("fail to process file", e);

		} catch (Exception e) {
			log.error("fail to process file", e);
		}

		return rtnMap;
	}
	
	@RequestMapping(value = "/admin/manage/baby/deleteCalendarModel", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody boolean deleteCalendarModelList(@ModelAttribute("params") AdminBestReviewDTO params, Model model, 
    		HttpServletResponse response, HttpServletRequest request) {
   	
		try {
	   		String checkList[] = request.getParameterValues("checkList");
	   		ArrayList<String> list = new ArrayList<>();
    		for(int i=0; i<checkList.length; i++) {
   			list.add(checkList[i]);
   		}
   		
   		Map<String, Object> paramMap = new HashMap<String, Object>();
   		paramMap.put("list", list);
   		
   		boolean isDeleted = adminBabyService.deleteCalendarModel(paramMap);
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
	
	
	@RequestMapping(value = "/admin/manage/baby/bestReviewList", method = {RequestMethod.GET, RequestMethod.POST})
	 public @ResponseBody DataTableDTO getBestReviewList(@ModelAttribute("params") AdminBestReviewDTO params, Model model, @RequestParam Map<String, Object> commandMap) {

		DataTableDTO dataTableDto = adminBabyService.getBestReviewList(commandMap);
		return dataTableDto;
	 }
	
	@RequestMapping(value = "/admin/manage/baby/updateBestReview", method = {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Map<String, Object> updateBestReviewList(@ModelAttribute("params") final AdminBestReviewDTO params, Model model) {
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		try {
			boolean isUpdate = adminBabyService.registerBestReview(params);
			rtnMap.put("result", isUpdate);
		} catch (DataAccessException e) {
			log.error("fail to process file", e);

		} catch (Exception e) {
			log.error("fail to process file", e);
		}

		return rtnMap;
	}
	
	@RequestMapping(value = "/admin/manage/baby/deleteBestReview", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody boolean deleteBetReviewList(@ModelAttribute("params") AdminBestReviewDTO params, Model model, 
    		HttpServletResponse response, HttpServletRequest request) {
		try {
	   		String checkList[] = request.getParameterValues("checkList");
	   		ArrayList<String> list = new ArrayList<>();
    		for(int i=0; i<checkList.length; i++) {
   			list.add(checkList[i]);
   		}
   		
   		Map<String, Object> paramMap = new HashMap<String, Object>();
   		paramMap.put("list", list);
   		
   		boolean isDeleted = adminBabyService.deleteBestReview(paramMap);
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
	
	@RequestMapping(value = "/admin/manage/baby/sampleBabyList")
	 public @ResponseBody JsonObject getSampleBabyList(@ModelAttribute("params") AdminSampleBabyDTO params, Model model
			 , @RequestParam Map<String, Object> commandMap) {

		JsonObject jsonObj = new JsonObject();
		List<AdminSampleBabyDTO> sampleBabyList = adminBabyService.getSampleBabyList(commandMap);
		if (CollectionUtils.isEmpty(sampleBabyList) == false) {
			Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new GsonLocalDateTimeAdapter()).create();
			JsonArray jsonArr = gson.toJsonTree(sampleBabyList).getAsJsonArray();
			jsonObj.add("data", jsonArr);
		}

		return jsonObj;
	 }
	
	@RequestMapping(value = "/admin/manage/baby/updateSampleBaby", method = {RequestMethod.GET, RequestMethod.POST})
	public String updateSampleBabyList(@ModelAttribute("params") final AdminSampleBabyDTO params, Model model) {
		try {
			boolean isUpdate = adminBabyService.registerSampleBaby(params);
			if (isUpdate== false) {
				return showMessageWithRedirect("?????? ????????? ?????????????????????.", "/admin/event/sampleBaby", Method.GET, null, model);
			}
		} catch (DataAccessException e) {
			return showMessageWithRedirect("?????????????????? ?????? ????????? ????????? ?????????????????????.", "/admin/event/sampleBaby", Method.GET, null, model);

		} catch (Exception e) {
			return showMessageWithRedirect("???????????? ????????? ?????????????????????.", "/admin/event/sampleBaby", Method.GET, null, model);
		}

		return "/admin/event/sampleBaby";
	}
	
	@RequestMapping(value = "/admin/manage/baby/deleteSampleBaby", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody boolean deleteSampleBabyList(@ModelAttribute("params") AdminBestReviewDTO params, Model model, 
    		HttpServletResponse response, HttpServletRequest request) {
		try {
	   		String checkList[] = request.getParameterValues("checkList");
	   		ArrayList<String> list = new ArrayList<>();
    		for(int i=0; i<checkList.length; i++) {
    			list.add(checkList[i]);
    		}
   		
	   		Map<String, Object> paramMap = new HashMap<String, Object>();
	   		paramMap.put("list", list);
	   		
	   		boolean isDeleted = adminBabyService.deleteSampleBaby(paramMap);
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
	
	@RequestMapping(value = "/admin/manage/baby/babyQnaAdd")
    public String openBabyQnaAdd(@RequestParam(value = "mbsIdx", required = false) Long mbsIdx, HttpServletRequest req, Model model)throws Exception{
		AdminBabyDTO babyDto = adminBabyService.getBabyQnaDetail(mbsIdx);
		
		model.addAttribute("babyQna", babyDto);
		
		return "admin/baby/babyQnaAdd";
    }
}
