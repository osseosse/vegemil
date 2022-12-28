package com.vegemil.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vegemil.domain.AdminBeanSoupEventDTO;
import com.vegemil.domain.AdminBeanSoupNewsDTO;
import com.vegemil.domain.AdminBeanSoupVideoDTO;
import com.vegemil.mapper.AdminBeanSoupMapper;

@Service
public class AdminBeanSoupServiceImpl implements AdminBeanSoupService {

	@Autowired
	private AdminBeanSoupMapper adminBeanSoupMapper;
	
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

		if ("U".equals(params.getAction())) {
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
	public List<AdminBeanSoupEventDTO> getBeanSoupEventList(AdminBeanSoupEventDTO params) {
		List<AdminBeanSoupEventDTO> beanSoupEventList = Collections.emptyList();

		beanSoupEventList = adminBeanSoupMapper.selectBeanSoupEventList(params);

		return beanSoupEventList;
	}
	
	@Override
	public boolean saveBeanSoupEvent(AdminBeanSoupEventDTO params) {
		int queryResult = 0;

		if ("U".equals(params.getAction())) {
			queryResult = adminBeanSoupMapper.updateBeanSoupEvent(params);
		} else if("D".equals(params.getAction())) {
			queryResult = adminBeanSoupMapper.deleteBeanSoupEvent(params);
		}else {
			queryResult = adminBeanSoupMapper.insertBeanSoupEvent(params);
		}

		return (queryResult == 1) ? true : false;
	}
	
}
