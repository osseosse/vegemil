package com.vegemil.service;

import java.util.List;

import com.vegemil.domain.AdminBeanSoupEventDTO;
import com.vegemil.domain.AdminBeanSoupNewsDTO;
import com.vegemil.domain.AdminBeanSoupVideoDTO;

public interface AdminBeanSoupService {

	public List<AdminBeanSoupVideoDTO> getBeanSoupVideoList(AdminBeanSoupVideoDTO params);
	
	public AdminBeanSoupVideoDTO getBeanSoupVideo(Long mIdx);
	
	public boolean registerBeanSoupVideo(AdminBeanSoupVideoDTO params);
	
	public boolean saveBeanSoupVideo(AdminBeanSoupVideoDTO params);
	
	public List<AdminBeanSoupNewsDTO> getBeanSoupNewsList(AdminBeanSoupNewsDTO params);
	
	public boolean saveBeanSoupNews(AdminBeanSoupNewsDTO params);
	
	public AdminBeanSoupNewsDTO getBeanSoupNews(Long mIdx);
	
	public boolean registerBeanSoupNews(AdminBeanSoupNewsDTO params);
	
	public List<AdminBeanSoupEventDTO> getBeanSoupEventList(AdminBeanSoupEventDTO params);
	
	public boolean saveBeanSoupEvent(AdminBeanSoupEventDTO params);
}
