package com.vegemil.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UtilController {
	
	/**
	 *  ajax로 파일명 체크 
	 */
	
	@GetMapping("/filetype/check/img")
	public String fileTypeCheck(@RequestParam(value = "fileName", required = false) String fileName) {
		
		return "";
		
	}

}
