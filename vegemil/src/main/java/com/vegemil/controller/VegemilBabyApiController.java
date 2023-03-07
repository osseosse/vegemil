package com.vegemil.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vegemil.domain.AdminCalendarTitleDTO;
import com.vegemil.domain.CMRespDto;
import com.vegemil.paging.BoardListSearchDTO;
import com.vegemil.paging.CustomBaseResponse;
import com.vegemil.service.vegemilBaby.VegemilBabyCommunityService;
import com.vegemil.util.UiUtils;


@CrossOrigin(origins="*", allowedHeaders = "*")
@RestController
public class VegemilBabyApiController extends UiUtils {

	@Autowired
	private VegemilBabyCommunityService vegemilBabyCommunityService;
	
	@Value("${spring.servlet.multipart.location}")
    private String uploadPath;

	@GetMapping("/api/vegemilBaby/event/model")
	public ResponseEntity<?> moveEventModelPage(BoardListSearchDTO boardListSearchDTO) {
		
        PageHelper.startPage(boardListSearchDTO);
       
        return new ResponseEntity<>(new CMRespDto<>(1, "성공", PageInfo.of(vegemilBabyCommunityService.selectModelList(boardListSearchDTO))), HttpStatus.OK);

	}
	
	@GetMapping("/api/vegemilBaby/event/model/title")
	public ResponseEntity<?> eventModelTitle() {
		
		List<AdminCalendarTitleDTO> titleList = vegemilBabyCommunityService.selectCalenderModelTitle(); 
        return new ResponseEntity<>(new CMRespDto<>(1, "성공", titleList), HttpStatus.OK);
		
	}
	
	@GetMapping("/api/vegemilBaby/event/model/title/{rownum}")
	public ResponseEntity<?> eventModelTitle2(@PathVariable("rownum") String rownum) {
		
		AdminCalendarTitleDTO title = vegemilBabyCommunityService.selectCalenderModelTitlebyRownum(rownum); 
        return new ResponseEntity<>(new CMRespDto<>(1, "성공", title), HttpStatus.OK);

	}
	


}
