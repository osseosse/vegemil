package com.vegemil.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vegemil.domain.AdminBabyDTO;
import com.vegemil.domain.AdminBestReviewDTO;
import com.vegemil.domain.AdminCalendarModelDTO;
import com.vegemil.domain.AdminSaboDTO;
import com.vegemil.domain.AdminSampleBabyDTO;
import com.vegemil.domain.DataTableDTO;
import com.vegemil.mapper.AdminBabyMapper;
import com.vegemil.paging.PaginationInfo;

@Service
@Transactional
public class AdminBabyServiceImpl implements AdminBabyService {

	@Autowired
	private AdminBabyMapper adminBabyMapper;
	
	@Override
	public DataTableDTO getBabyInfoList(Map<String, Object> paramMap) {
		List<AdminBabyDTO> babyInfoList = Collections.emptyList();
		DataTableDTO dataTableDto = new DataTableDTO();
		
		int babyInfoTotalCount = adminBabyMapper.selectBabyInfoTotalCount(paramMap);

		if (babyInfoTotalCount > 0) {
			int start = Integer.parseInt(paramMap.get("start").toString());
			int length = Integer.parseInt(paramMap.get("length").toString());
			
			paramMap.put("start", start);
			paramMap.put("length", length);
			babyInfoList = adminBabyMapper.selectBabyInfoList(paramMap);
		}
		
		dataTableDto.setData(babyInfoList);
		dataTableDto.setRecordsTotal(babyInfoTotalCount);
		dataTableDto.setRecordsFiltered(babyInfoTotalCount);
		dataTableDto.setDraw(Integer.parseInt(paramMap.get("draw").toString()));

		return dataTableDto;
	}
	
	@Override
	public boolean registerBabyInfo(AdminBabyDTO params) {
		int queryResult = 0;

		if (params.getMbsIdx() == null) {
			queryResult = adminBabyMapper.insertBabyInfo(params);
		} else {
			queryResult = adminBabyMapper.updateBabyInfo(params);
		}

		return (queryResult == 1) ? true : false;
	}
	
	@Override
	public AdminBabyDTO getBabyInfoDetail(Long mbsIdx) {
		return adminBabyMapper.selectBabyInfoDetail(mbsIdx);
	}
	
	@Override
	public boolean deleteBabyInfo(Map<String, Object> paramMap) {
		int queryResult = 0;


		queryResult = adminBabyMapper.deleteBabyInfo(paramMap);

		return (queryResult > 0) ? true : false;
	}
	
	@Override
	public List<AdminBabyDTO> getBabyQnaList(AdminBabyDTO params) {
		List<AdminBabyDTO> babyQnaList = Collections.emptyList();

		int babyQnaTotalCount = adminBabyMapper.selectBabyQnaTotalCount(params);

		PaginationInfo paginationInfo = new PaginationInfo(params);
		paginationInfo.setTotalRecordCount(babyQnaTotalCount);

		params.setPaginationInfo(paginationInfo);

		if (babyQnaTotalCount > 0) {
			babyQnaList = adminBabyMapper.selectBabyQnaList(params);
		}

		return babyQnaList;
	}
	
	@Override
	public boolean registerBabyQna(AdminBabyDTO params) {
		int queryResult = 0;

		if (params.getMbsIdx() == null) {
			queryResult = adminBabyMapper.insertBabyQna(params);
		} else {
			queryResult = adminBabyMapper.updateBabyQna(params);
		}

		return (queryResult == 1) ? true : false;
	}
	
	@Override
	public AdminBabyDTO getBabyQnaDetail(Long mbsIdx) {
		return adminBabyMapper.selectBabyQnaDetail(mbsIdx);
	}
	
	
	@Override
	public boolean registerCalendarModel(AdminCalendarModelDTO params) {
		int queryResult = 0;

		if (params.getCIdx() == null) {
			queryResult = adminBabyMapper.insertCalendarModel(params);
		} else {
			queryResult = adminBabyMapper.updateCalendarModel(params);
		}

		return (queryResult == 1) ? true : false;
	}

	@Override
	public AdminCalendarModelDTO getCalendarModelDetail(Long idx) {
		return adminBabyMapper.selectCalendarModelDetail(idx);
	}

	@Override
	public boolean deleteCalendarModel(Map<String, Object> paramMap) {
		int queryResult = 0;

		queryResult = adminBabyMapper.deleteCalendarModel(paramMap);

		return (queryResult > 0) ? true : false;
	}

	@Override
	public DataTableDTO getCalendarModelList(Map<String, Object> paramMap) {
		List<AdminCalendarModelDTO> calendarModelList = Collections.emptyList();
		DataTableDTO dataTableDto = new DataTableDTO();

		int calendarModelTotalCount = adminBabyMapper.selectCalendarModelTotalCount(paramMap);

		System.out.println("paramMap: "+ paramMap);
		if (calendarModelTotalCount > 0) {
			int start = Integer.parseInt(paramMap.get("start").toString());
			int length = Integer.parseInt(paramMap.get("length").toString());
			
			
			paramMap.put("start", start);
			paramMap.put("length", length);
			calendarModelList = adminBabyMapper.selectCalendarModelList(paramMap);
		}
		
		dataTableDto.setData(calendarModelList);
		dataTableDto.setRecordsTotal(calendarModelTotalCount);
		dataTableDto.setRecordsFiltered(calendarModelTotalCount);
		dataTableDto.setDraw(Integer.parseInt(paramMap.get("draw").toString()));

		return dataTableDto;
	}
	
	
	@Override
	public boolean registerBestReview(AdminBestReviewDTO params) {
		int queryResult = 0;

		if (params.getSIdx() == null) {
			queryResult = adminBabyMapper.insertBestReview(params);
		} else {
			queryResult = adminBabyMapper.updateBestReview(params);
		}

		return (queryResult == 1) ? true : false;
	}

	@Override
	public AdminBestReviewDTO getBestReviewDetail(Long idx) {
		return adminBabyMapper.selectBestReviewDetail(idx);
	}

	@Override
	public DataTableDTO getBestReviewList(Map<String, Object> paramMap) {
		List<AdminBestReviewDTO> BestReviewList = Collections.emptyList();
		DataTableDTO dataTableDto = new DataTableDTO();

		int bestReviewTotalCount = adminBabyMapper.selectBestReviewTotalCount(paramMap);

		if (bestReviewTotalCount > 0) {
			int start = Integer.parseInt(paramMap.get("start").toString());
			int length = Integer.parseInt(paramMap.get("length").toString());
			
			paramMap.put("start", start);
			paramMap.put("length", length);
			BestReviewList = adminBabyMapper.selectBestReviewList(paramMap);
		}
		
		dataTableDto.setData(BestReviewList);
		dataTableDto.setRecordsTotal(bestReviewTotalCount);
		dataTableDto.setRecordsFiltered(bestReviewTotalCount);
		dataTableDto.setDraw(Integer.parseInt(paramMap.get("draw").toString()));

		return dataTableDto;
	}
	
	@Override
	public boolean deleteBestReview(Map<String, Object> paramMap) { 
        int queryResult = 0;
        queryResult = adminBabyMapper.deleteBestReview(paramMap);
		if(queryResult > 0) {
			return true;
		} else {
			return false;
		}
               
    }
	
	@Override
	public boolean registerSampleBaby(AdminSampleBabyDTO params) {
		int queryResult = 0;

		if (params.getSIdx() == null) {
			queryResult = adminBabyMapper.insertSampleBaby(params);
		} else {
			queryResult = adminBabyMapper.updateSampleBaby(params);
		}

		return (queryResult == 1) ? true : false;
	}

	@Override
	public AdminSampleBabyDTO getSampleBabyDetail(Long idx) {
		return adminBabyMapper.selectSampleBabyDetail(idx);
	}

	@Override
	public boolean deleteSampleBaby(Map<String, Object> paramMap) {
		int queryResult = 0;

		queryResult = adminBabyMapper.deleteSampleBaby(paramMap);

		return (queryResult > 0) ? true : false;
	}

	@Override
	public List<AdminSampleBabyDTO> getSampleBabyList(Map<String, Object> paramMap) {
		List<AdminSampleBabyDTO> sampleBabyList = Collections.emptyList();

		int SampleBabyTotalCount = adminBabyMapper.selectSampleBabyTotalCount(paramMap);

		if (SampleBabyTotalCount > 0) {
			sampleBabyList = adminBabyMapper.selectSampleBabyList(paramMap);
		}

		return sampleBabyList;
	}

}
