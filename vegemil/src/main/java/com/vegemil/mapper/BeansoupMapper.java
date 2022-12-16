package com.vegemil.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.vegemil.domain.BeansoupDTO;
import com.vegemil.domain.BeansoupEventDTO;
import com.vegemil.domain.BeansoupNewsDTO;
import com.vegemil.domain.BeansoupVideoDTO;

@Mapper
public interface BeansoupMapper {
	
	public List<BeansoupDTO> selectBeansoupList();
	public int selectBeansoupListCount(BeansoupDTO beansoupDto);
	public List<BeansoupDTO> selectBeanListWithKeyword(String searchKeyword);
	public BeansoupDTO selectBeansoupDetail(String fileNo);
	public List<BeansoupDTO> selectBeansoupProposalList(String mCate);
	
	// m_beanSoup_event
	public List<BeansoupEventDTO> selectBeansoupEventList(BeansoupEventDTO params);
	public int selectBeansoupEventCount(BeansoupEventDTO params);
	
	//m_beanSoup_news
	public List<BeansoupNewsDTO> selectBeansoupNewsList();
	public int selectBeansoupNewsListCount(BeansoupNewsDTO beansoupDto);
	
	//m_beanSoup_video
	public List<BeansoupVideoDTO> selectBeansoupVideoList(BeansoupVideoDTO beansoupVideoDTO);
	public int selectBeansoupVideoCount(BeansoupVideoDTO beansoupVideoDTO);
}
