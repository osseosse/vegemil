package com.vegemil.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.vegemil.domain.FactpostDTO;
import com.vegemil.domain.ScheduleDTO;
import com.vegemil.domain.SearchDTO;
import com.vegemil.domain.VisitDTO;

@Mapper
public interface RndMapper {
	
	public int insertMvisit(VisitDTO visitDTO);

	public List<ScheduleDTO> selectTourScheduleList();
	
	public List<VisitDTO> selectVisitList(String vEmail);
	
	public List<FactpostDTO> selectTourReviewList(SearchDTO params);
	
	public FactpostDTO selectTourReviewByIdx(String sIdx);
	
	public int selectTourReviewCount(SearchDTO params);
	
	public int insertReview(FactpostDTO review);
	
}
