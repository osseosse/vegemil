package com.vegemil.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vegemil.constant.Method;
import com.vegemil.domain.AdminEventDTO;
import com.vegemil.domain.DataTableDTO;
import com.vegemil.domain.PopupDTO;
import com.vegemil.domain.ThermometerLoveDTO;
import com.vegemil.service.AdminEventService;
import com.vegemil.util.UiUtils;

import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
@RequestMapping("/admin/manage")
public class AdminEventController extends UiUtils{
	
	@Value("${spring.servlet.multipart.location}")
    private String uploadPath;
	
	@Autowired
	private AdminEventService adminEventService;
	
	//이벤트 등록 페이지
//	@GetMapping("/event/eventAdd")
//	public String moveEventAddPage(@RequestParam(value = "eIdx", required = false) Long eIdx , Model model)throws Exception {
//		
//		/*
//		 * AdminEventDTO eventDto = adminEventService.getEventDetail(eIdx);
//		 * model.addAttribute("eventInfo", eventDto);
//		 */
//		
//		return "admin/event/eventAdd";
//	}
	
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
	
	
	//이벤트 조회페이지 
	@GetMapping("/event/{viewName}")
	public String moveEventListPage(@PathVariable(value = "viewName", required = false) String viewName)throws Exception {
		return "admin/event/"+viewName;
	}
		
	//이벤트 조회 - 베지밀
	@GetMapping("/event/eventVegemilList")
	public @ResponseBody DataTableDTO getVegemilEventList(
						@ModelAttribute("params") AdminEventDTO params, Model model, 
						@RequestParam Map<String, Object> commandMap) {
		
		DataTableDTO dataTableDto = adminEventService.getVegemilEventList(commandMap);
		
		return dataTableDto;
	}
	
	//이벤트 수정 페이지 - 베지밀
	@GetMapping("/event/eventUpdate")
	public String moveEventUpdatePage(@RequestParam(value = "eIdx", required = false) Long eIdx , Model model)throws Exception {
		
		AdminEventDTO eventDto = adminEventService.getEventDetail(eIdx);
		model.addAttribute("eventInfo", eventDto);
		
		return "admin/event/eventUpdate";
	}
	
	//이벤트 수정 페이지 - 영유아식
	@GetMapping("/event/eventUpdateVegemilBaby")
	public String moveEventUpdateVBPage(@RequestParam(value = "eIdx", required = false) Long eIdx , Model model)throws Exception {
		
		AdminEventDTO eventDto = adminEventService.getEventDetailVB(eIdx);
		model.addAttribute("eventInfo", eventDto);
		
		return "admin/event/eventUpdateVegemilBaby";
	}

	
	//이벤트 수정
	@PostMapping("/event/eventUpdate")
	@ResponseBody
	public Map<String, Object> updateEvent(@ModelAttribute("params") final AdminEventDTO params, Model model,
			HttpServletResponse response, HttpServletRequest request) throws Exception {


		Map<String, Object> rtnMap = new HashMap<String, Object>();

		try {
			boolean isUpdated = adminEventService.updateEvent(params);
			rtnMap.put("result", isUpdated);
		} catch (DataAccessException e) {
		} catch (Exception e) {
		}

		return rtnMap;
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
	
	//이벤트 삭제 - 영유아식
	@RequestMapping(value = "/event/deleteVegemilBabyEvent", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody boolean deleteVegemilBabyEvent(@ModelAttribute("params") AdminEventDTO params, Model model,
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

			boolean isDeleted = adminEventService.deleteVegemilBabyEvent(paramMap);
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
	
	//메인페이지 진열정보 수정 - 베지밀
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
			eventInfo.setEIdx(eIdx);
			eventInfo.setEActive(eActive);
			eventInfo.setEBvactive(eBvactive);
			eventInfo.setCategory("vegemil");
			
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
	
	//메인페이지 진열정보 수정 - 영유아식
	@GetMapping(value = "/event/displayEventInfoVB")
	public @ResponseBody boolean displayEventInfoVB(@RequestParam(value = "eIdx", required = false) Long eIdx,			
			@RequestParam(value = "eActive", required = false) int eActive, @RequestParam(value = "eBvactive", required = false) int eBvactive,
			HttpServletResponse response) throws Exception {
		boolean isRegistered = true;
		try {
			if (eIdx == null) {
				return false;
			}
			AdminEventDTO eventInfo = adminEventService.getEventDetailVB(eIdx);
			eventInfo.setEIdx(eIdx);
			eventInfo.setEActive(eActive);
			eventInfo.setEBvactive(eBvactive);
			eventInfo.setCategory("vegemilBaby");
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
	
	// 사랑의 온도계
	@GetMapping("/event/thermometerOfLove")
	public String moveThermometerOfLoveAdminView(Model model) {
		
		int year = LocalDateTime.now().getYear();
		ThermometerLoveDTO dto = adminEventService.getThermometerLove(year);
		model.addAttribute("dto", dto);
		
		return "admin/event/thermometerOfLove";		
	}
	
	@PostMapping("/event/thermometerOfLove")
	public String submitThermometerOfLove(ThermometerLoveDTO params, Model model) {
		
		adminEventService.updateLoveThermometer(params);
		return showMessageWithRedirect("사랑의 온도를 저장했습니다.", "/admin/manage/event/thermometerOfLove",Method.GET, null, model);
	}
	
	@GetMapping("/event/popup")
	public String getPopupAdmin() {
		return "admin/event/popup";
	}
	
	//이벤트 조회 - 베지밀
	@GetMapping("/event/popupList")
	public @ResponseBody DataTableDTO getPopupData(
						@ModelAttribute("params") PopupDTO params, Model model, 
						@RequestParam Map<String, Object> commandMap) {
		
		DataTableDTO dataTableDto = adminEventService.getPopupList(commandMap);
		
		return dataTableDto;
	}

	
	//정적 이미지 불러오기
	@GetMapping("/web/upload/EVENT/{filename}")
	public ResponseEntity<Resource> display(@PathVariable(value = "filename", required = false) String filename) {
		Resource resource = new FileSystemResource(uploadPath + "/upload/EVENT/" + filename);
		//Resource resource = new FileSystemResource("D:/upload/admin/" + filename);
		if(!resource.exists()) 
			return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);
		HttpHeaders header = new HttpHeaders();
		Path filePath = null;
		try {
			filePath = Paths.get(uploadPath + "/upload/EVENT/" + filename);
			//filePath = Paths.get("D:/upload/admin/" + filename);
			header.add("Content-type", Files.probeContentType(filePath));
		} catch(IOException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Resource>(resource, header, HttpStatus.OK);
	}
		
	
	

}
	
	

