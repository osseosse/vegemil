package com.vegemil.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vegemil.constant.Method;
import com.vegemil.domain.ClaimDTO;
import com.vegemil.domain.EventDTO;
import com.vegemil.domainEday.EdayVempDTO;
import com.vegemil.service.CommunicationService;
import com.vegemil.service.EdayVempService;
import com.vegemil.service.MailService;
import com.vegemil.util.UiUtils;

@Controller
public class CommunicationConroller extends UiUtils{
	
	@Autowired
	private CommunicationService communicationService;

	@Autowired
	private ResourceLoader resourceLoader; 
	
	@Autowired MailService mailService;
	
    @Autowired
    private EdayVempService edayVempService;
    
	@RequestMapping(value = "/communication/{viewName}")
    public String moveCommunication(@PathVariable(value = "viewName", required = false) String viewName) throws Exception {
		
		return "communication/"+viewName;
    }
	
	
	@GetMapping("/communication/cp")
	public String getCpPage() {
		return "communication/cp/cp";
	}

	@GetMapping("/communication/cp/cpHandbook")
	public String getCpManual() {
		return "communication/cp/cpHandbook";
	}
	
	@GetMapping("/communication/cp/cpProgram")
	public String getCpProgram() {
		return "communication/cp/cpProgram";
	}

	@GetMapping("/communication/cp/cpProgramStatus")
	public String getCpProgramStatus() {
		return "communication/cp/cpProgramStatus";
	}
	
	@GetMapping("/communication/cp/cpEbookView")
	public String getCpEbookView(Model model, String fileName) {
		model.addAttribute("fileName", fileName);
		return "communication/cp/cpEbookView";
	}
	
	@RequestMapping(value = "/event/list")
    public String moveEventList(@PathVariable(value = "viewName", required = false) String viewName, Model model)throws Exception{
		
		List<EventDTO> eventList = communicationService.getEnevetList();
		if(eventList != null) {
			model.addAttribute("eventList", eventList);
		}
		
		return "communication/event/list";
    }
	
	@RequestMapping(value = "/event/detail/{eIdx}")
    public String moveEventDetail(@PathVariable(value = "eIdx", required = false) String eIdx , Model model) throws Exception {
		
		EventDTO event = new EventDTO();
		
		event = communicationService.getEvent(eIdx);
		if(event != null) {
			model.addAttribute("event", event);
		}
		
		return "communication/event/detail";
    }
	
	@PostMapping("/cp/cIdCheck")
	public String cIdCheck(String cId, String fileName, Model model) {
		model.addAttribute("fileName",fileName);
		if (communicationService.checkCompId(cId) <= 0) {
			model.addAttribute("msg","사번 조회에 실패했습니다.");
			model.addAttribute("validation","0");
			return "communication/cp/cpEbookView";
		}
		
		model.addAttribute("validation","1");
		model.addAttribute("msg","");
		
		return "communication/cp/cpEbookView";
	}

	// 자율준수 편람 파일 다운 코드
	@GetMapping("/cpEbookDown")
	public ResponseEntity<Resource> cpEbookDown(@RequestParam("fileName")String fileName,
			@RequestHeader(name = "user-agent") String userAgent) throws IOException {

		try {
			
			fileName = fileName + ".pdf";
			
			Resource resource = resourceLoader.getResource("classpath:static/cp/papers/" + fileName);
			String downName = null;
			
			// 인터넷 익스플로러 인 경우
			boolean isMSIE = userAgent.indexOf("MSIE") != -1 || userAgent.indexOf("Trident") != -1;
			
			if (isMSIE) { // 익스플로러 대응
				downName = URLEncoder.encode(fileName, "UTF-8").replaceAll("/+", "%20");
			} else {
				downName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1"); // 크롬
			}
			
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + downName + "\"")
					.header(HttpHeaders.CONTENT_LENGTH, String.valueOf(resource.contentLength()))
					.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM.toString()).body(resource);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 실패 시 
	}

	@GetMapping("/communication/cp/cpDeclaration")
	public String cpDeclaration() {
		return "communication/cp/cpDeclaration";
	}

	@PostMapping("/communication/cp/cpDeclaration")
	public String postCpClaim(Model model, ClaimDTO claimDTO, HttpServletRequest req) {
		
		long timeGap = (new Date().getTime()-claimDTO.getSubmitTime().getTime());
		
		if(claimDTO.getSubmitTime() == null) {
			return showMessageWithRedirect("잘못된 접근입니다.", "/communication/cp/cpDeclaration", Method.GET, null, model);
		}
		
		//System.out.println("timeGap >>> " + timeGap);
		
		String ip = getClientIpVer2(req);
		if(ip.equals(communicationService.getRecentClaimIp()) || timeGap < 6000) {
			return showMessageWithRedirect("앞에서 같은 아이피 주소로 접수한 기록이 있습니다.", "/communication/cp/cpDeclaration", Method.GET, null, model);
		}
		
		claimDTO.setCIp(ip);
		int result = communicationService.insertMclaim(claimDTO);
		
		List<String> recipientsId = new ArrayList<>();
		//recipientsId.add("hypark023@osse.co.kr"); 테스트용
    	
		if(result > 0) {
			if(recipientsId.size()>0) {
				for(String recipientId : recipientsId) {
					EdayVempDTO receiverEmp = edayVempService.getVempInfo(recipientId);
					System.out.println(receiverEmp.toString());
					if(receiverEmp.getExpireYn().equals("1")) {
						// 담당자 확인 요청 메일 
						mailService.requestCheckPersonInCharge();
						recipientsId.remove(recipientId);
						break;
					}
				}
			}
			//관리자에게 알림 메일 발송
			mailService.alertSubmitCpDecl(claimDTO, recipientsId);
			//신고인에게 확인 메일 발송
			mailService.confirmSubmitCpDecl(claimDTO);
			return showMessageWithRedirect("신고가 정상적으로 접수되었습니다.", "/communication/cp", Method.GET, null, model);
		}
		
		return showMessageWithRedirect("신고 접수에 실패했습니다.", "/communication/cp/cpDeclaration", Method.GET, null, model);
	}
}
