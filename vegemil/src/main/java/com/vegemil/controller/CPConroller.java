package com.vegemil.controller;

import java.io.IOException;
import java.net.URLEncoder;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vegemil.constant.Method;
import com.vegemil.domain.ClaimDTO;
import com.vegemil.service.CpService;
import com.vegemil.util.UiUtils;

@Controller
public class CPConroller extends UiUtils{
	
	@Autowired
	private CpService cpService;

	@Autowired
	private ResourceLoader resourceLoader; 
	
	@GetMapping("/communication/cp")
	public String getCpPage() {
		return "/communication/cp/cp";
	}

	@GetMapping("/communication/cp/cpHandbook")
	public String getCpManual() {
		return "/communication/cp/cpHandbook";
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
		return "/communication/cp/cpEbookView";
	}
	
	@PostMapping("/cp/cIdCheck")
	public String cIdCheck(String cId, String fileName, Model model) {
		model.addAttribute("fileName",fileName);
		if (cpService.checkCompId(cId) <= 0) {
			model.addAttribute("msg","사번 조회에 실패했습니다.");
			model.addAttribute("validation","0");
			return "/communication/cp/cpEbookView";
		}
		
		model.addAttribute("validation","1");
		model.addAttribute("msg","");
		
		return "/communication/cp/cpEbookView";
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
	public String postCpClaim(Model model, ClaimDTO claimDTO) {
		
		int result = cpService.insertMclaim(claimDTO);
		
		if(result > 0) {
			return showMessageWithRedirect("신고가 정상적으로 접수되었습니다.", "/communication/cp", Method.GET, null, model);
		}
		
		return showMessageWithRedirect("신고 접수에 실패했습니다.", "/communication/cp/cpDeclaration", Method.GET, null, model);
	}
}
