package com.vegemil.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.vegemil.domain.AdminBabyDTO;
import com.vegemil.domain.AdminBestReviewDTO;
import com.vegemil.domain.AdminCalendarModelDTO;
import com.vegemil.domain.AdminCalendarTitleDTO;
import com.vegemil.domain.AdminCfDTO;
import com.vegemil.domain.AdminSampleBabyDTO;

@Mapper
public interface AdminBabyMapper {
	
	//=================== 육아정보  =========================
	public List<AdminBabyDTO> selectBabyInfoList(Map<String, Object> paramMap);
	
	public int selectBabyInfoTotalCount(Map<String, Object> paramMap);
	
	public int updateBabyInfoCount(Long mbsIdx);
	
	public int insertBabyInfo(AdminBabyDTO params);
	
	public AdminBabyDTO selectBabyInfoDetail(Long mbsIdx);
	
	public int updateBabyInfo(AdminBabyDTO params);
	
	public int deleteBabyInfo(Map<String, Object> paramMap);
	
	public String selectImgFileOriginalBabyInfo(Long mbsIdx);
	
	public String selectImgFileBabyInfo(Long mbsIdx);
	//=================== 육아정보  =========================	
	
	
	public List<AdminBabyDTO> selectBabyQnaList(AdminBabyDTO params);
	
	public int selectBabyQnaTotalCount(AdminBabyDTO params);
	
	public int insertBabyQna(AdminBabyDTO params);
	
	public AdminBabyDTO selectBabyQnaDetail(Long mbsIdx);
	
	public int updateBabyQna(AdminBabyDTO params);
	
	
	//=================== 아기달력모델  =========================	
	public int insertCalendarModel(AdminCalendarModelDTO params);

	public AdminCalendarModelDTO selectCalendarModelDetail(Long idx);

	public int updateCalendarModel(AdminCalendarModelDTO params);
	
	public String selectBabyImg(AdminCalendarModelDTO params);
	public String selectBabyImg2(AdminCalendarModelDTO params);

	public int deleteCalendarModel(Map<String, Object> paramMap);

	public List<AdminCalendarModelDTO> selectCalendarModelList(Map<String, Object> paramMap);

	public int selectCalendarModelTotalCount(Map<String, Object> paramMap);	
	
	public List<AdminCalendarModelDTO> selectModelRank1();
	
	public int insertCalenderModelTitle(AdminCalendarTitleDTO adminCalendarTitleDTO);
	
	public List<AdminCalendarTitleDTO> selectCalenderModelTitle();

	public int deleteCalenderModelTitle(Long tIdx);
	
	public int selectSecond1stBaby(int rownum);
	
	public int updateTitle2ndInfo(int cIdx);
	//=================== 아기달력모델  =========================	
	
	
	//=================== 후기이벤트  =========================
	public int insertBestReview(AdminBestReviewDTO params);

	public AdminBestReviewDTO selectBestReviewDetail(Long idx);

	public int updateBestReview(AdminBestReviewDTO params);

	public int deleteBestReview(Map<String, Object> paramMap);

	public List<AdminBestReviewDTO> selectBestReviewList(Map<String, Object> paramMap);
	public List<AdminBestReviewDTO> selectBestReviewList2(Map<String, Object> paramMap);

	public int selectBestReviewTotalCount(Map<String, Object> paramMap);
	
	public String selectBestReviewImg(AdminBestReviewDTO params);
	//=================== 후기이벤트  =========================
	
	
	//=================== 샘플신청  =========================
	public int insertSampleBaby(AdminSampleBabyDTO params);

	public AdminSampleBabyDTO selectSampleBabyDetail(Long idx);

	public int updateSampleBaby(AdminSampleBabyDTO params);

	public int deleteSampleBaby(Map<String, Object> paramMap);

	public List<AdminSampleBabyDTO> selectSampleBabyList(Map<String, Object> paramMap);

	public int selectSampleBabyTotalCount(Map<String, Object> paramMap);
	//=================== 샘플신청  =========================
	
	
	//=================== TV CF관리 =========================	
	public int selectBabyTvcfTotalCount(Map<String, Object> paramMap);

	public List<AdminCfDTO> selectBabyTvcfList(Map<String, Object> commandMap);

	public int insertBabyTvcf(AdminCfDTO adminCfDTO);

	public String selectImgFile(String cIdx);
	
	public int deleteBabyTvcf(AdminCfDTO adminCfDTO);

	public int updateBabyTvcf(AdminCfDTO adminCfDTO);

	public int updateBabyTvcfFileInfo(AdminCfDTO adminCfDTO);

	



	
	
}
