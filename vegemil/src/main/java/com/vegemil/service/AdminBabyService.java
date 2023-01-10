package com.vegemil.service;

import java.util.List;
import java.util.Map;

import com.vegemil.domain.AdminBabyDTO;
import com.vegemil.domain.AdminBestReviewDTO;
import com.vegemil.domain.AdminCalendarModelDTO;
import com.vegemil.domain.AdminSampleBabyDTO;
import com.vegemil.domain.DataTableDTO;

public interface AdminBabyService {
	
	public DataTableDTO getBabyInfoList(Map<String, Object> paramMap);
	
	public boolean registerBabyInfo(AdminBabyDTO params);
	
	public AdminBabyDTO getBabyInfoDetail(Long mbsIdx);
	
	public boolean deleteBabyInfo(Map<String, Object> paramMap);

	/*
	public boolean registerBoard(BoardDTO params);

	public BoardDTO getBoardDetail(Long idx);

	public boolean deleteBoard(Long idx);

	public List<BoardDTO> getBoardList(BoardDTO params);
	 */
	
	public List<AdminBabyDTO> getBabyQnaList(AdminBabyDTO params);
	
	public boolean registerBabyQna(AdminBabyDTO params);
	
	public AdminBabyDTO getBabyQnaDetail(Long mbsIdx);
	
	public boolean registerCalendarModel(AdminCalendarModelDTO params);

	public AdminCalendarModelDTO getCalendarModelDetail(Long idx);

	public boolean deleteCalendarModel(Map<String, Object> paramMap);

	public DataTableDTO getCalendarModelList(Map<String, Object> paramMap);
	
	
	public boolean registerBestReview(AdminBestReviewDTO params);

	public AdminBestReviewDTO getBestReviewDetail(Long idx);

	public DataTableDTO getBestReviewList(Map<String, Object> paramMap);
	
	public boolean deleteBestReview(Map<String, Object> paramMap);
	
	
	public boolean registerSampleBaby(AdminSampleBabyDTO params);

	public AdminSampleBabyDTO getSampleBabyDetail(Long idx);

	public boolean deleteSampleBaby(Map<String, Object> paramMap);

	public List<AdminSampleBabyDTO> getSampleBabyList(Map<String, Object> paramMap);


	
}
