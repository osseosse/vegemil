package com.vegemil.service;

import java.util.List;
import java.util.Map;

import com.vegemil.domain.BeansoupDTO;
import com.vegemil.domain.BeansoupEventDTO;
import com.vegemil.domain.BeansoupNewsDTO;
import com.vegemil.domain.BeansoupVideoDTO;
import com.vegemil.domain.SearchDTO;
import com.vegemil.domain.contest.PaintingContestDTO;

public interface BeansoupService {
	
	//BeansoupDTO
	public List<BeansoupDTO> selectBeansoupList();
	public int selectBeansoupListCount(BeansoupDTO beansoupDto);
	public List<BeansoupDTO> selectBeanListWithKeyword(String searchKeyword);
	public BeansoupDTO selectBeansoupDetail(String fileNo);
	public List<BeansoupDTO> selectBeansoupProposalList(String mCate);
	
	//BeansoupEventDTO 
	public List<BeansoupEventDTO> selectBeansoupEventList(BeansoupEventDTO params);
	public List<BeansoupEventDTO> selectMainBeansoupEventList();
	public int selectBeansoupEventCount(BeansoupEventDTO params);
	
	//BeansoupNewDTO
	public List<BeansoupNewsDTO> selectBeansoupNewsList();
	public int selectBeansoupNewsListCount(BeansoupNewsDTO beansoupDto);
	
	//BeansoupVideoMappser
	public List<BeansoupVideoDTO> selectBeansoupVideoList(BeansoupVideoDTO beansoupVideoDTO);
	public int selectBeansoupVideoCount(BeansoupVideoDTO beansoupVideoDTO);

	//그림동시대회
	public int submitPaintingPoetWork(PaintingContestDTO paintingContestDTO);
	// 지원서 목록
	public List<PaintingContestDTO> findAllSubmitList(PaintingContestDTO searchDTO);
	// 등수 매기기
	
	//추가작업용
	public Map<String, Integer> mappingCount(List<BeansoupDTO> beansoupList);
}
