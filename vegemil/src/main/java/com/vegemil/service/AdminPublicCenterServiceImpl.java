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
import org.springframework.transaction.annotation.Transactional;

import com.vegemil.domain.AdminMediaNewsDTO;
import com.vegemil.domain.DataTableDTO;
import com.vegemil.mapper.AdminPublicCenterMapper;

@Service
@Transactional
public class AdminPublicCenterServiceImpl implements AdminPublicCenterService {
	
	@Value("${spring.servlet.multipart.location}")
    private String uploadPath;

	@Autowired
	private AdminPublicCenterMapper adminPublicCenterMapper;
	
	//보도자료 등록
	@Override
	public boolean registerMediaNews(AdminMediaNewsDTO params) throws Exception {
		
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
			params.setMImg(savefileName);		
		}	
		
		int queryResult = 0;		
		queryResult = adminPublicCenterMapper.insertMediaNews(params);
		
		return (queryResult == 1)? true : false;
	}
	
	//보도자료 조회
	@Override
	public DataTableDTO getMediaNewsList(Map<String, Object> paramMap) {
		
		List<AdminMediaNewsDTO> mediaNewsList = Collections.emptyList();
		DataTableDTO dataTableDto = new DataTableDTO();

		int mediaNewsTotalCount = adminPublicCenterMapper.selectMediaNewsTotalCount(paramMap);		

		if (mediaNewsTotalCount > 0) {
			
			  int start = Integer.parseInt(paramMap.get("start").toString()); 
			  int length = Integer.parseInt(paramMap.get("length").toString());
			  
			  paramMap.put("start", start);
			  paramMap.put("length", length);
			 
			  mediaNewsList = adminPublicCenterMapper.selectMediaNewsList(paramMap);			 
		}
		
		dataTableDto.setData(mediaNewsList);
		dataTableDto.setRecordsTotal(mediaNewsTotalCount);
		dataTableDto.setRecordsFiltered(mediaNewsTotalCount);
		dataTableDto.setDraw(Integer.parseInt(paramMap.get("draw").toString()));

		return dataTableDto;
	}
	
	
	//보도자료 수정
	@Override
	public boolean updateMediaNews(AdminMediaNewsDTO params) {

		return false;
	}
		
	//보도자료 삭제
	@Override
	public boolean deleteMediaNews(Map<String, Object> paramMap) {
		int queryResult = 0;
		
		queryResult = adminPublicCenterMapper.deleteMediaNews(paramMap);
		
		return (queryResult > 0)?  true : false;
	}

	
	//보도자료 조회
	@Override
	public AdminMediaNewsDTO getMediaNewsDetail(Long mIdx) {
		return adminPublicCenterMapper.selectMediaNews(mIdx);
	}

	


}
