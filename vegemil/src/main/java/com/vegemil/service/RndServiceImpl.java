package com.vegemil.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vegemil.domain.FactpostDTO;
import com.vegemil.domain.MediaNewsDTO;
import com.vegemil.domain.ScheduleDTO;
import com.vegemil.domain.SearchDTO;
import com.vegemil.domain.VisitDTO;
import com.vegemil.mapper.RndMapper;
import com.vegemil.paging.PaginationInfo;

@Service
public class RndServiceImpl implements RndService {
	
	@Autowired
	RndMapper rndMapper;

	@Override
	public int insertMvisit(VisitDTO visitDTO) {
		if(visitDTO.getVTel() == null || "".equals(visitDTO.getVTel())) {
			visitDTO.setVTel(visitDTO.getVHp());
		}
		return rndMapper.insertMvisit(visitDTO);
	}
	
	@Override
	public int getApplyCount(VisitDTO visitDTO) {

		int result = 0;
		result = rndMapper.selectApplyCount(visitDTO);

		return result;
	}
	
	@Override
	public int getApplyCount(String date) {

		int result = 0;
		result = rndMapper.selectConfirmApplyCount(date);

		return result;
	}
	
	@Override
    public List<VisitDTO> getVisitList(String vEmail) {
		
		List<VisitDTO> visitList = Collections.emptyList();
		visitList = rndMapper.selectVisitList(vEmail);
		
        return visitList;
    }
	
	@Override
    public List<ScheduleDTO> getTourScheduleList() {
		
		List<ScheduleDTO> tourSchedule = Collections.emptyList();
		tourSchedule = rndMapper.selectTourScheduleList();
		
        return tourSchedule;
    }

	@Override
	public List<FactpostDTO> getFactReviewList(SearchDTO params) {
		
		List<FactpostDTO> reviewList = Collections.emptyList();
		
        int count = rndMapper.selectTourReviewCount(params);
        
        PaginationInfo paginationInfo = new PaginationInfo(params);
		paginationInfo.setTotalRecordCount(count);
		params.setPaginationInfo(paginationInfo);
		
		if(count > 0) {
			reviewList = rndMapper.selectTourReviewList(params);
		}
		return reviewList;
	}
	
	@Override
	public FactpostDTO getTourReview(String sIdx) {
		
		FactpostDTO review = rndMapper.selectTourReviewByIdx(sIdx);
		
		return review;
	}
	
	@Override
	public int saveReview(FactpostDTO review) {

		int result = 0;
		result = rndMapper.insertReview(review);

		return result;
	}
	
}
