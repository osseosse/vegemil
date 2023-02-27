package com.vegemil.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.vegemil.domain.AdminBabyDTO;
import com.vegemil.domain.AdminBestReviewDTO;
import com.vegemil.domain.AdminCalendarModelDTO;
import com.vegemil.domain.AdminCalendarTitleDTO;
import com.vegemil.domain.AdminCfDTO;
import com.vegemil.domain.AdminSampleBabyDTO;
import com.vegemil.domain.DataTableDTO;

public interface AdminBabyService {
	
	public DataTableDTO getBabyInfoList(Map<String, Object> paramMap);
	
	public boolean registerBabyInfo(AdminBabyDTO params, MultipartFile uploadFile) throws Exception;
	
	public AdminBabyDTO getBabyInfoDetail(Long mbsIdx);
	
	public boolean deleteBabyInfo(Map<String, Object> paramMap);
		
	public List<AdminBabyDTO> getBabyQnaList(AdminBabyDTO params);
	
	public boolean registerBabyQna(AdminBabyDTO params) throws Exception;
	
	public AdminBabyDTO getBabyQnaDetail(Long mbsIdx);
	
	//=================== 아기달력모델  =========================	
	public boolean registerCalendarModel(AdminCalendarModelDTO params);

	public AdminCalendarModelDTO getCalendarModelDetail(Long idx);

	public boolean deleteCalendarModel(Map<String, Object> paramMap);

	public DataTableDTO getCalendarModelList(Map<String, Object> paramMap);
	
	public List<AdminCalendarModelDTO> selectModelRank1();	
	
	public boolean insertCalenderModelTitle(AdminCalendarTitleDTO adminCalendarTitleDTO);
	
	public List<AdminCalendarTitleDTO> selectCalenderModelTitle();
	
	boolean deleteCalenderModelTitle(Long tIdx);
	//=================== 아기달력모델  =========================
	
	
	//=================== 후기이벤트  =========================
	public boolean registerBestReview(AdminBestReviewDTO params);

	public AdminBestReviewDTO getBestReviewDetail(Long idx);

	public DataTableDTO getBestReviewList(Map<String, Object> paramMap);
	
	public boolean deleteBestReview(Map<String, Object> paramMap);
	//=================== 후기이벤트  =========================
		
	
	//=================== 샘플신청  =========================
	public boolean registerSampleBaby(AdminSampleBabyDTO params);

	public AdminSampleBabyDTO getSampleBabyDetail(Long idx);

	public boolean deleteSampleBaby(Map<String, Object> paramMap);

	public List<AdminSampleBabyDTO> getSampleBabyList(Map<String, Object> paramMap);
	//=================== 샘플신청  =========================
	
	
	//=================== TV CF관리 =========================	
	//TVCF 조회
	public DataTableDTO getBabyTvcfList(Map<String, Object> commandMap);

	//TVCF 등록	
	public boolean saveBabyTvcf(AdminCfDTO adminCfDTO) throws Exception;
	//=================== TV CF관리 =========================


	
	

	


	
}
