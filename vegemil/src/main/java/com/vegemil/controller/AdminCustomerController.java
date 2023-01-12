package com.vegemil.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.vegemil.domain.MemberDTO;
import com.vegemil.service.AdminCustomerService;
import com.vegemil.service.AdminFaqService;
import com.vegemil.util.UiUtils;

import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
public class AdminCustomerController extends UiUtils {
	
	@Autowired
	private AdminFaqService adminFaqService;
	
	@Autowired
	private AdminCustomerService adminCustomerService;
	
	@Value("${spring.servlet.multipart.location}")
    private String uploadPath;

	@RequestMapping(value = "/admin/manage/customer/{viewName}")
    public String adminMoveCustomer(@PathVariable(value = "viewName", required = false) String viewName)throws Exception{
		return "admin/customer/"+viewName;
    }
	
	@RequestMapping(value = "/admin/manage/customer/faqList")
	 public @ResponseBody DataTableDTO getFaqList(@ModelAttribute("params") FaqDTO params, Model model,
			 @RequestParam Map<String, Object> commandMap) {
		DataTableDTO dataTableDto = adminFaqService.getFaqList(commandMap);
		return dataTableDto;
	 }
	
	@RequestMapping(value = "/admin/manage/customer/faqAdd")
    public String openFaqAdd(@RequestParam(value = "fIdx", required = false) Long fIdx, HttpServletRequest req, Model model)throws Exception{

		AdminFaqDTO faqDto = adminFaqService.getFaqDetail(fIdx);
		model.addAttribute("faq", faqDto);
		
		return "admin/customer/faqAdd";
    }
	
	@PostMapping(value = "/admin/manage/customer/uploadFaq")
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
	
	@PostMapping(value = "/admin/manage/customer/saveFaq")
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
	
	@RequestMapping(value = "/admin/manage/customer/updateFaqDisplay", method = {RequestMethod.GET, RequestMethod.POST})
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
	
	@RequestMapping("/admin/manage/customer/deleteFaq")
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
	
	@RequestMapping(value = "/admin/manage/customer/faqScoreList")
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
	
	@RequestMapping(value = "/admin/manage/customer/supportList")
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
	
	@RequestMapping("/admin/manage/customer/deleteSupport")
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
	
	@GetMapping(value = "/admin/manage/customer/supportDetail")
    public String getSupportDetail(@ModelAttribute("params") AdminSupportDTO params, HttpServletRequest req, Model model)throws Exception{
		AdminSupportDTO supportDto = adminFaqService.getSupport(params);
		
		model.addAttribute("support", supportDto);
		
		return "admin/customer/supportDetail";
    }
	
	@PostMapping(value = "/admin/manage/customer/saveSupportDetail")
	public String saveSupportDetail(@ModelAttribute("params") final AdminSupportDTO params, Model model) {
		Map<String, Object> pagingParams = getPagingParams(params);
		try {
			boolean isRegistered = adminFaqService.registerSupportDetail(params);
			if (isRegistered == false) {
				return showMessageWithRedirect("게시글 등록에 실패하였습니다.", "/admin/manage/customer/support", Method.GET, pagingParams, model);
			}
		} catch (DataAccessException e) {
			return showMessageWithRedirect("데이터베이스 처리 과정에 문제가 발생하였습니다.", "/admin/manage/customer/support", Method.GET, pagingParams, model);

		} catch (Exception e) {
			return showMessageWithRedirect("시스템에 문제가 발생하였습니다.", "/admin/manage/customer/support", Method.GET, pagingParams, model);
		}

		return showMessageWithRedirect("게시글 등록이 완료되었습니다.", "/admin/manage/customer/support", Method.GET, null, model);
	}
	
	@RequestMapping(value = "/admin/manage/customer/greenbiaList")
	public @ResponseBody DataTableDTO getGreenbiaList(@ModelAttribute("params") MemberDTO params, Model model,
			@RequestParam Map<String, Object> commandMap) {
		
		DataTableDTO dataTableDto = adminCustomerService.getGreenbiaList(commandMap);
		return dataTableDto;
	}
	
	@RequestMapping(value = "/admin/manage/customer/deleteGreenBia", method = {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Map<String, Object> deleteGreenBia(@RequestParam(required = false) final Long mIdx, Model model) throws Exception {
		
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		
		try {
			boolean isdeleted = adminCustomerService.deleteGreenbia(mIdx);
			rtnMap.put("result", isdeleted);
		} catch (DataAccessException e) {
			throw new IOException("저장에 실패하였습니다.");
		} catch (Exception e) {
			throw new IOException("저장에 실패하였습니다.");
		}
		return rtnMap;
	}
	
	@GetMapping(value = "/admin/manage/customer/greenbiaDetail")
    public String getGreenbiaDetail(@RequestParam(value = "mIdx", required = false) Long mIdx, HttpServletRequest req, Model model)throws Exception{
		MemberDTO memberDto = adminCustomerService.getGreenbia(mIdx);
		
		model.addAttribute("member", memberDto);
		
		return "admin/customer/greenbiaDetail";
    }
	
	@PostMapping(value = "/admin/manage/customer/saveGreenbia")
	public String saveGreenbia(@ModelAttribute("params") final MemberDTO params, Model model) {
		try {
			boolean isRegistered = adminCustomerService.saveGreenbia(params);
			if (isRegistered == false) {
				return showMessageWithRedirect("그린비아회원관리 저장에 실패하였습니다.", "/admin/manage/customer/greenbia", Method.GET, null, model);
			}
		} catch (DataAccessException e) {
			return showMessageWithRedirect("데이터베이스 처리 과정에 문제가 발생하였습니다.", "/admin/manage/customer/greenbia", Method.GET, null, model);

		} catch (Exception e) {
			return showMessageWithRedirect("시스템에 문제가 발생하였습니다.", "/admin/manage/customer/greenbia", Method.GET, null, model);
		}

		return showMessageWithRedirect("수정되었습니다.", "/admin/manage/customer/greenbia", Method.GET, null, model);
	}
	
	@RequestMapping(value = "/admin/manage/customer/memberList")
	public @ResponseBody DataTableDTO getMemberList(@ModelAttribute("params") MemberDTO params, Model model,
			@RequestParam Map<String, Object> commandMap) {
		
		DataTableDTO dataTableDto = adminCustomerService.getMemberList(commandMap);
		return dataTableDto;
	}
	
	@GetMapping(value = "/admin/manage/customer/memberDetail")
    public String getMemberDetail(@RequestParam(value = "mIdx", required = false) Long mIdx, HttpServletRequest req, Model model)throws Exception{
		MemberDTO memberDto = adminCustomerService.getMember(mIdx);
		
		model.addAttribute("member", memberDto);
		
		return "admin/customer/memberDetail";
    }
	
	@PostMapping(value = "/admin/manage/customer/saveMember")
	public String saveMember(@ModelAttribute("params") final MemberDTO params, Model model) {
		try {
			boolean isRegistered = adminCustomerService.saveMember(params);
			if (isRegistered == false) {
				return showMessageWithRedirect("회원관리 저장에 실패하였습니다.", "/admin/manage/customer/member", Method.GET, null, model);
			}
		} catch (DataAccessException e) {
			return showMessageWithRedirect("데이터베이스 처리 과정에 문제가 발생하였습니다.", "/admin/manage/customer/member", Method.GET, null, model);

		} catch (Exception e) {
			return showMessageWithRedirect("시스템에 문제가 발생하였습니다.", "/admin/manage/customer/member", Method.GET, null, model);
		}

		return showMessageWithRedirect("수정되었습니다.", "/admin/manage/customer/member", Method.GET, null, model);
	}
	
	//정적 이미지 불러오기
	@GetMapping("/web/upload/{filename}")
	public ResponseEntity<Resource> display(@PathVariable(value = "filename", required = false) String filename) {
		Resource resource = new FileSystemResource(uploadPath + "/upload/CUSTOMER/" + filename);
		if(!resource.exists()) 
			return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);
		HttpHeaders header = new HttpHeaders();
		Path filePath = null;
		try {
			filePath = Paths.get(uploadPath + "/upload/CUSTOMER/" + filename);
			header.add("Content-type", Files.probeContentType(filePath));
		} catch(IOException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Resource>(resource, header, HttpStatus.OK);
	}
	
}
