package com.vegemil.service;

import java.util.List;

import com.vegemil.domain.FactpostDTO;
import com.vegemil.domain.ScheduleDTO;
import com.vegemil.domain.SearchDTO;
import com.vegemil.domain.VisitDTO;

public interface RndService {
	
	public int insertMvisit(VisitDTO visitDTO);
	
	public int getApplyCount(VisitDTO visitDTO);
	
	public List<ScheduleDTO> getTourScheduleList();
	
	public List<VisitDTO> getVisitList(String vEmail);
	
	public List<FactpostDTO> getFactReviewList(SearchDTO params);
	
	public FactpostDTO getTourReview(String sIdx);
	
	public int saveReview(FactpostDTO review);
}
