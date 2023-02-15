package com.vegemil.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
public class AdminImageLoadController extends UiUtils {

	@Value("${spring.servlet.multipart.location}")
    private String uploadPath;
	
	// =========================홍보 센터 이미지 로드=========================
	
	//기타홍보영상 이미지 불러오기
	@GetMapping("/web/upload/OM/{filename}")
	public ResponseEntity<Resource> omDisplay(@PathVariable(value = "filename", required = false) String filename) {
		Resource resource = new FileSystemResource(uploadPath + "/upload/OM/" + filename);
		if(!resource.exists()) 
			return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);
		HttpHeaders header = new HttpHeaders();
		Path filePath = null;
		try {
			filePath = Paths.get(uploadPath + "/upload/OM/" + filename);
			header.add("Content-type", Files.probeContentType(filePath));
		} catch(IOException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Resource>(resource, header, HttpStatus.OK);
	}
	
	//공모전  이미지 불러오기
	@GetMapping("/web/upload/VC/{filename}")
	public ResponseEntity<Resource> vcDisplay(@PathVariable(value = "filename", required = false) String filename) {
		Resource resource = new FileSystemResource(uploadPath + "/upload/VC/" + filename);
		if(!resource.exists()) 
			return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);
		HttpHeaders header = new HttpHeaders();
		Path filePath = null;
		try {
			filePath = Paths.get(uploadPath + "/upload/VC/" + filename);
			header.add("Content-type", Files.probeContentType(filePath));
		} catch(IOException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Resource>(resource, header, HttpStatus.OK);
	}
	
	//라디오 씨엠 이미지 불러오기
	@GetMapping("/web/upload/RCM/{filename}")
	public ResponseEntity<Resource> cmDisplay(@PathVariable(value = "filename", required = false) String filename) {
		Resource resource = new FileSystemResource(uploadPath + "/upload/RCM/" + filename);
		if(!resource.exists()) 
			return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);
		HttpHeaders header = new HttpHeaders();
		Path filePath = null;
		try {
			filePath = Paths.get(uploadPath + "/upload/CM/" + filename);
			header.add("Content-type", Files.probeContentType(filePath));
		} catch(IOException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Resource>(resource, header, HttpStatus.OK);
	}
	
	//cf 이미지 불러오기
	@GetMapping("/web/upload/CF/{filename}")
	public ResponseEntity<Resource> cfDisplay(@PathVariable(value = "filename", required = false) String filename) {
		Resource resource = new FileSystemResource(uploadPath + "/upload/CF/" + filename);
		if(!resource.exists()) 
			return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);
		HttpHeaders header = new HttpHeaders();
		Path filePath = null;
		try {
			filePath = Paths.get(uploadPath + "/upload/CF/" + filename);
			header.add("Content-type", Files.probeContentType(filePath));
		} catch(IOException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Resource>(resource, header, HttpStatus.OK);
	}
	
}