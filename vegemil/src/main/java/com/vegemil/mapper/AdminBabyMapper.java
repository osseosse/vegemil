package com.vegemil.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.vegemil.domain.AdminBabyDTO;
import com.vegemil.domain.AdminBestReviewDTO;
import com.vegemil.domain.AdminCalendarModelDTO;
import com.vegemil.domain.AdminSampleBabyDTO;

@Mapper
public interface AdminBabyMapper {
	
	public List<AdminBabyDTO> selectBabyInfoList(Map<String, Object> paramMap);
	
	public int selectBabyInfoTotalCount(Map<String, Object> paramMap);
	
	public int insertBabyInfo(AdminBabyDTO params);
	
	public AdminBabyDTO selectBabyInfoDetail(Long mbsIdx);
	
	public int updateBabyInfo(AdminBabyDTO params);
	
	public int deleteBabyInfo(Map<String, Object> paramMap);
	
	public List<AdminBabyDTO> selectBabyQnaList(AdminBabyDTO params);
	
	public int selectBabyQnaTotalCount(AdminBabyDTO params);
	
	public int insertBabyQna(AdminBabyDTO params);
	
	public AdminBabyDTO selectBabyQnaDetail(Long mbsIdx);
	
	public int updateBabyQna(AdminBabyDTO params);
	
	public int insertCalendarModel(AdminCalendarModelDTO params);

	public AdminCalendarModelDTO selectCalendarModelDetail(Long idx);

	public int updateCalendarModel(AdminCalendarModelDTO params);

	public int deleteCalendarModel(Map<String, Object> paramMap);

	public List<AdminCalendarModelDTO> selectCalendarModelList(Map<String, Object> paramMap);

	public int selectCalendarModelTotalCount(Map<String, Object> paramMap);
	
	
	public int insertBestReview(AdminBestReviewDTO params);

	public AdminBestReviewDTO selectBestReviewDetail(Long idx);

	public int updateBestReview(AdminBestReviewDTO params);

	public int deleteBestReview(Map<String, Object> paramMap);

	public List<AdminBestReviewDTO> selectBestReviewList(Map<String, Object> paramMap);

	public int selectBestReviewTotalCount(Map<String, Object> paramMap);
	
	public int insertSampleBaby(AdminSampleBabyDTO params);

	public AdminSampleBabyDTO selectSampleBabyDetail(Long idx);

	public int updateSampleBaby(AdminSampleBabyDTO params);

	public int deleteSampleBaby(Map<String, Object> paramMap);

	public List<AdminSampleBabyDTO> selectSampleBabyList(Map<String, Object> paramMap);

	public int selectSampleBabyTotalCount(Map<String, Object> paramMap);
}
