package com.vegemil.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.vegemil.domain.BeansoupDTO;
import com.vegemil.domain.BeansoupEventDTO;
import com.vegemil.domain.BeansoupNewsDTO;
import com.vegemil.domain.BeansoupVideoDTO;
import com.vegemil.domain.contest.PaintingContestAward23DTO;
import com.vegemil.domain.contest.PaintingContestDTO;

@Mapper
public interface BeansoupMapper {
	
	public List<BeansoupDTO> selectBeansoupList();
	public int selectBeansoupListCount(BeansoupDTO beansoupDto);
	public List<BeansoupDTO> selectBeanListWithKeyword(String searchKeyword);
	public List<BeansoupDTO> selectBeanListWithKeywordRenew(String tag);
	public BeansoupDTO selectBeansoupDetail(String fileNo);
	public List<BeansoupDTO> selectBeansoupProposalList(String mCate);
	
	// m_beanSoup_event
	public List<BeansoupEventDTO> selectBeansoupEventList(BeansoupEventDTO params);
	public List<BeansoupEventDTO> selectMainBeansoupEventList();
	public int selectBeansoupEventCount(BeansoupEventDTO params);
	
	//m_beanSoup_news
	public List<BeansoupNewsDTO> selectBeansoupNewsList();
	public int selectBeansoupNewsListCount(BeansoupNewsDTO beansoupDto);
	
	//m_beanSoup_video
	public List<BeansoupVideoDTO> selectBeansoupVideoList(BeansoupVideoDTO beansoupVideoDTO);
	public int selectBeansoupVideoCount(BeansoupVideoDTO beansoupVideoDTO);
	
	//23년 08월 오픈~ 그림 동시 대회 
	public int insertPaintingContest(PaintingContestDTO paintingContestDTO);
	public List<PaintingContestDTO> selectPaintingContestList(PaintingContestDTO searchDTO);	
	public int selectCountConData(String section);
	public int updatePaintingContestPrize(PaintingContestDTO paintingContestDTO);
	public Map<String,Integer> selectCountConData();
	
	public List<PaintingContestAward23DTO> selectContestAwawrdList23(String section);
	public List<PaintingContestAward23DTO> selectContestAwardPaging23(PaintingContestAward23DTO dto);
	public PaintingContestAward23DTO selectContestWinner(PaintingContestAward23DTO dto);
	
}
