package com.vegemil.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.vegemil.domain.AdminBeanSoupEventDTO;
import com.vegemil.domain.AdminBeanSoupNewsDTO;
import com.vegemil.domain.AdminBeanSoupVideoDTO;

@Mapper
public interface AdminBeanSoupMapper {
	
	public List<AdminBeanSoupVideoDTO> selectBeanSoupVideoList(AdminBeanSoupVideoDTO params);
	
	public AdminBeanSoupVideoDTO selectBeanSoupVideo(Long mIdx);
	
	public int insertBeanSoupVideo(AdminBeanSoupVideoDTO params);
	
	public int updateBeanSoupVideo(AdminBeanSoupVideoDTO params);
	
	public int deleteBeanSoupVideo(AdminBeanSoupVideoDTO params);
	
	public List<AdminBeanSoupNewsDTO> selectBeanSoupNewsList(AdminBeanSoupNewsDTO params);
	
	public int insertBeanSoupNews(AdminBeanSoupNewsDTO params);
	
	public int updateBeanSoupNews(AdminBeanSoupNewsDTO params);
	
	public int deleteBeanSoupNews(AdminBeanSoupNewsDTO params);
	
	public AdminBeanSoupNewsDTO selectBeanSoupNews(Long mIdx);
	
	public List<AdminBeanSoupEventDTO> selectBeanSoupEventList(AdminBeanSoupEventDTO params);
	
	public int updateBeanSoupEvent(AdminBeanSoupEventDTO params);

	public int updateBeanSoupFileInfo(AdminBeanSoupEventDTO params);
	
	public int deleteBeanSoupEvent(AdminBeanSoupEventDTO params);
	
	public int insertBeanSoupEvent(AdminBeanSoupEventDTO params);
}
