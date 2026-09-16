package com.vegemil.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vegemil.domain.AdminMediaNewsDTO;
import com.vegemil.domain.DataTableDTO;
import com.vegemil.mapper.AdminPublicCenterMapper;

@Service
@Transactional
public class AdminPublicCenterServiceImpl implements AdminPublicCenterService {
	
	@Autowired
	private AdminPublicCenterMapper adminPublicCenterMapper;

	@Autowired
	private ImageServerService imageServerService;
	
	//보도자료 등록
	@Override
	public boolean registerMediaNews(AdminMediaNewsDTO params) throws Exception {

		String originalName = params.getFileName().getOriginalFilename();

		if(originalName != null && !"".equals(originalName)) {
			String imageUrl = imageServerService.upload(params.getFileName(), "mediaNews");
			params.setMImg(imageUrl);
			params.setMImgOriginal(originalName);
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
	public boolean updateMediaNews(AdminMediaNewsDTO params) throws Exception {

		String storedImgOriginal = adminPublicCenterMapper.selectImgFileOriginal(params.getMIdx());
		String originalName = params.getFileName().getOriginalFilename();

		if(originalName != null && !"".equals(originalName)) {
			if(storedImgOriginal == null || !originalName.equals(storedImgOriginal)) {
				String imageUrl = imageServerService.upload(params.getFileName(), "mediaNews");
				params.setMImg(imageUrl);
				params.setMImgOriginal(originalName);
			}
		}

		int queryResult = 0;
		queryResult = adminPublicCenterMapper.updateMediaNews(params);
		return (queryResult == 1)? true : false;
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
