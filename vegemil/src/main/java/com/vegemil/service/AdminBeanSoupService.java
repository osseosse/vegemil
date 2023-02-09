package com.vegemil.service;

import java.util.List;
import java.util.Map;

import com.vegemil.domain.AdminBeanSoupEventDTO;
import com.vegemil.domain.AdminBeanSoupNewsDTO;
import com.vegemil.domain.AdminBeanSoupVideoDTO;
import com.vegemil.domain.DataTableDTO;

public interface AdminBeanSoupService {

	public List<AdminBeanSoupVideoDTO> getBeanSoupVideoList(AdminBeanSoupVideoDTO params);
	
	public AdminBeanSoupVideoDTO getBeanSoupVideo(Long mIdx);
	
	public boolean registerBeanSoupVideo(AdminBeanSoupVideoDTO params);
	
	public boolean saveBeanSoupVideo(AdminBeanSoupVideoDTO params);
	
	public List<AdminBeanSoupNewsDTO> getBeanSoupNewsList(AdminBeanSoupNewsDTO params);
	
	public boolean saveBeanSoupNews(AdminBeanSoupNewsDTO params);
	
	public AdminBeanSoupNewsDTO getBeanSoupNews(Long mIdx);
	
	public boolean registerBeanSoupNews(AdminBeanSoupNewsDTO params);
	
//	public List<AdminBeanSoupEventDTO> getBeanSoupEventList(AdminBeanSoupEventDTO params);
	public DataTableDTO getBeanSoupEventList(Map<String, Object> paramMap);
	
	public boolean saveBeanSoupEvent(AdminBeanSoupEventDTO params) throws Exception;

	public boolean updateBeanSoupEventImg(AdminBeanSoupEventDTO params);
}
