package com.vegemil.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vegemil.domain.AdminBeanSoupEventDTO;
import com.vegemil.domain.AdminBeanSoupNewsDTO;
import com.vegemil.domain.AdminBeanSoupVideoDTO;
import com.vegemil.domain.DataTableDTO;
import com.vegemil.mapper.AdminBeanSoupMapper;

@Service
@Transactional
public class AdminBeanSoupServiceImpl implements AdminBeanSoupService {
	
	@Autowired
	private AdminBeanSoupMapper adminBeanSoupMapper;

	@Autowired
	private ImageServerService imageServerService;
	
	@Override
	public List<AdminBeanSoupVideoDTO> getBeanSoupVideoList(AdminBeanSoupVideoDTO params) {
		List<AdminBeanSoupVideoDTO> beanSoupVideoList = Collections.emptyList();

		beanSoupVideoList = adminBeanSoupMapper.selectBeanSoupVideoList(params);

		return beanSoupVideoList;
	}
	
	@Override
	public AdminBeanSoupVideoDTO getBeanSoupVideo(Long mIdx) {
		return adminBeanSoupMapper.selectBeanSoupVideo(mIdx);
	}
	
	
	@Override
	public boolean registerBeanSoupVideo(AdminBeanSoupVideoDTO params) {
		int queryResult = 0;

		if (params.getMIdx() == null) {
			queryResult = adminBeanSoupMapper.insertBeanSoupVideo(params);
		} else {
			queryResult = adminBeanSoupMapper.updateBeanSoupVideo(params);
		}

		return (queryResult == 1) ? true : false;
	}
	
	@Override
	public boolean saveBeanSoupVideo(AdminBeanSoupVideoDTO params) {
		int queryResult = 0;

		if ("U".equals(params.getAction())) {
			queryResult = adminBeanSoupMapper.updateBeanSoupVideo(params);
		} else if("D".equals(params.getAction())) {
			queryResult = adminBeanSoupMapper.deleteBeanSoupVideo(params);
		}else {
			queryResult = adminBeanSoupMapper.insertBeanSoupVideo(params);
		}

		return (queryResult == 1) ? true : false;
	}
	
	@Override
	public List<AdminBeanSoupNewsDTO> getBeanSoupNewsList(AdminBeanSoupNewsDTO params) {
		List<AdminBeanSoupNewsDTO> beanSoupNewsList = Collections.emptyList();

		beanSoupNewsList = adminBeanSoupMapper.selectBeanSoupNewsList(params);

		return beanSoupNewsList;
	}
	
	@Override
	public boolean saveBeanSoupNews(AdminBeanSoupNewsDTO params) {
		int queryResult = 0;
		
		System.out.println("파람정보"+ params.getAction());

		if ("U".equals(params.getAction()) || "DI".equals(params.getAction())) {
			queryResult = adminBeanSoupMapper.updateBeanSoupNews(params);
		} else if("D".equals(params.getAction())) {
			queryResult = adminBeanSoupMapper.deleteBeanSoupNews(params);
		}else {
			queryResult = adminBeanSoupMapper.insertBeanSoupNews(params);
		}

		return (queryResult == 1) ? true : false;
	}
	
	@Override
	public AdminBeanSoupNewsDTO getBeanSoupNews(Long mIdx) {
		return adminBeanSoupMapper.selectBeanSoupNews(mIdx);
	}
	
	@Override
	public boolean registerBeanSoupNews(AdminBeanSoupNewsDTO params) {
		int queryResult = 0;

		if (params.getMIdx() == null) {
			queryResult = adminBeanSoupMapper.insertBeanSoupNews(params);
		} else {
			queryResult = adminBeanSoupMapper.updateBeanSoupNews(params);
		}

		return (queryResult == 1) ? true : false;
	}
	

	@Override
	public DataTableDTO getBeanSoupEventList(Map<String, Object> paramMap) {
		List<AdminBeanSoupEventDTO> beanSoupEventList = Collections.emptyList();
		DataTableDTO dataTableDto = new DataTableDTO();
		
		int beanSoupEventTotalCount = adminBeanSoupMapper.selectbeanSoupEventTotalCount(paramMap);				
		//beanSoupEventList = adminBeanSoupMapper.selectBeanSoupEventList(paramMap);
		if (beanSoupEventTotalCount > 0) {			
			
			  int start = Integer.parseInt(paramMap.get("start").toString()); 
			  int length = Integer.parseInt(paramMap.get("length").toString());
			  
			  paramMap.put("start", start);
			  paramMap.put("length", length);
			 
			  beanSoupEventList = adminBeanSoupMapper.selectBeanSoupEventList(paramMap);
		}
		
		dataTableDto.setData(beanSoupEventList);
		dataTableDto.setRecordsTotal(beanSoupEventTotalCount);
		dataTableDto.setRecordsFiltered(beanSoupEventTotalCount);
		dataTableDto.setDraw(Integer.parseInt(paramMap.get("draw").toString()));

		return dataTableDto;

		
	}
	
	@Override
	public boolean saveBeanSoupEvent(AdminBeanSoupEventDTO params) throws Exception {
		int queryResult = 0;

		if ("U".equals(params.getAction())) {
			if(params.getFileName() != null) {
				String originalName = params.getFileName().getOriginalFilename();
				if(originalName != null && !"".equals(originalName)) {
					String imageUrl = imageServerService.upload(params.getFileName(), "beansoup/event");
					params.setMThum(imageUrl);
					params.setMThumOriginal(originalName);
				}
			}
			queryResult = adminBeanSoupMapper.updateBeanSoupEvent(params);
		}
		else if("DI".equals(params.getAction())) {
			queryResult = adminBeanSoupMapper.updateBeanSoupFileInfo(params);
		}
		else if("D".equals(params.getAction())) {
			queryResult = adminBeanSoupMapper.deleteBeanSoupEvent(params);
		}else {
			queryResult = adminBeanSoupMapper.insertBeanSoupEvent(params);
		}

		return (queryResult == 1) ? true : false;
	}

	@Override
	public boolean updateBeanSoupEventImg(AdminBeanSoupEventDTO params) {

		
		return false;
	}
	
}
