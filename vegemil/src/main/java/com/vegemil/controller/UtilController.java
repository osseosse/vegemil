package com.vegemil.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.vegemil.service.FileService;

@RestController
public class UtilController {
	
	@Autowired
	FileService fileService;
	
	
	@Value("${spring.servlet.multipart.location}")
    private String uploadPath;
	
	/**
	 *  업로드된 파일 다운로드 controller 
	 */
	
	@GetMapping("/download/web/upload/{dir}/{fileName}")
	public ResponseEntity<Resource> downloadFile(@PathVariable("dir") String dir, @PathVariable("fileName") String fileName) throws IOException {
	    Path filePath = Paths.get("/web/upload/"+dir+"/"+fileName); // 실제 파일 경로
		
		//Path filePath = Paths.get("D:\\uploadData\\"+fileName); // 테스트용
		
	    Resource resource = new UrlResource(filePath.toUri());

	    if (!resource.exists()) {
	        throw new FileNotFoundException("파일이 존재하지 않습니다.");
	    }

	    
	    String originName = fileService.getOriginFileName(dir,fileName);	    	    
	    
	    return ResponseEntity.ok()
	            .contentType(MediaType.APPLICATION_OCTET_STREAM)
	            .header(HttpHeaders.CONTENT_DISPOSITION,    "attachment; filename=\"" + URLEncoder.encode(originName, "UTF-8").replace("+", "%20") + "\"")
	            .body(resource);
	}

}
