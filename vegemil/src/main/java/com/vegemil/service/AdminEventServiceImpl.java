package com.vegemil.service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.vegemil.domain.AdminEventDTO;
import com.vegemil.domain.DataTableDTO;
import com.vegemil.mapper.AdminEventMapper;

@Service
public class AdminEventServiceImpl implements AdminEventService {
	
	@Value("${spring.servlet.multipart.location}")
    private String uploadPath;


	@Autowired
	private AdminEventMapper adminEventMapper;

	
	//이벤트 조회 - 베지밀
	@Override
	public DataTableDTO getEventVegemilList(Map<String, Object> paramMap) {
		List<AdminEventDTO> vegemilEventList = Collections.emptyList();
		DataTableDTO dataTableDto = new DataTableDTO();

		int vegemilEventTotalCount = adminEventMapper.selectVegemilEventTotalCount(paramMap);		

		if (vegemilEventTotalCount > 0) {
			
			  int start = Integer.parseInt(paramMap.get("start").toString()); 
			  int length = Integer.parseInt(paramMap.get("length").toString());
			  
			  paramMap.put("start", start);
			  paramMap.put("length", length);
			 
			  vegemilEventList = adminEventMapper.selectVegemilEventList(paramMap);			 
		}
		
		dataTableDto.setData(vegemilEventList);
		dataTableDto.setRecordsTotal(vegemilEventTotalCount);
		dataTableDto.setRecordsFiltered(vegemilEventTotalCount);
		dataTableDto.setDraw(Integer.parseInt(paramMap.get("draw").toString()));

		return dataTableDto;
	}

	//이벤트 등록 - 베지밀
	@Override
	public boolean registerEvent(AdminEventDTO params) throws Exception {
		
		String uuid = UUID.randomUUID().toString();
		String originalName = params.getFileName().getOriginalFilename();
		
		if(!"".equals(originalName)) {
			
			String file = originalName.substring(originalName.lastIndexOf("\\")+1);			
			String savefileName = uuid + "_" +file;			
						
			//저장 - 실제경로
			//Path savePath = Paths.get(uploadPath+ "/upload/vegemilBaby/" + savefileName);
			
			//저장 - Test로컬경로
			Path savePath = Paths.get("D:/upload/admin/" + savefileName);											
		
			params.getFileName().transferTo(savePath);				
			params.setEImg(savefileName);	
			params.setEImgOriginal(originalName);
			
		}	
		
		int queryResult = 0;
		queryResult = adminEventMapper.insertEvent(params);
		
		return (queryResult == 1)? true : false;

	}
	

}
