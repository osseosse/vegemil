package com.vegemil.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vegemil.domain.AdminFaqDTO;
import com.vegemil.domain.AdminFaqScoreDTO;
import com.vegemil.domain.AdminSupportDTO;
import com.vegemil.domain.DataTableDTO;
import com.vegemil.domain.FaqDTO;
import com.vegemil.mapper.AdminFaqMapper;

@Service
public class AdminFaqServiceImpl implements AdminFaqService {

	@Autowired
	private AdminFaqMapper adminFaqMapper;

	@Override
	public boolean registerFaq(AdminFaqDTO params) {
		int queryResult = 0;

		if (params.getFIdx() == null) {
			queryResult = adminFaqMapper.insertFaq(params);
		} else {
			queryResult = adminFaqMapper.updateFaq(params);
		}

		return (queryResult == 1) ? true : false;
	}

	@Override
	public AdminFaqDTO getFaqDetail(Long idx) {
		return adminFaqMapper.selectFaqDetail(idx);
	}

	@Override
	public boolean deleteFaq(Map<String, Object> paramMap) {
		int queryResult = 0;


		queryResult = adminFaqMapper.deleteFaq(paramMap);

		return (queryResult > 0) ? true : false;
	}

	@Override
	public DataTableDTO getFaqList(Map<String, Object> paramMap) {
		List<AdminFaqDTO> faqList = Collections.emptyList();
		DataTableDTO dataTableDto = new DataTableDTO();
		System.out.println("=====paramMap: " + paramMap);
		int faqTotalCount = adminFaqMapper.selectFaqTotalCount(paramMap);
		System.out.println("paramMap: "+ paramMap);

		if (faqTotalCount > 0) {
			int start = Integer.parseInt(paramMap.get("start").toString());
			int length = Integer.parseInt(paramMap.get("length").toString());
			
			paramMap.put("start", start);
			paramMap.put("length", length);
			faqList = adminFaqMapper.selectFaqList(paramMap);
		}
		
		dataTableDto.setData(faqList);
		dataTableDto.setRecordsTotal(faqTotalCount);
		dataTableDto.setRecordsFiltered(faqTotalCount);
		dataTableDto.setDraw(Integer.parseInt(paramMap.get("draw").toString()));

		return dataTableDto;
	}
	
	@Override
	public boolean updateDisplay(Map<String, Object> paramMap) {
		int queryResult = 0;


		queryResult = adminFaqMapper.updateDisplay(paramMap);

		return (queryResult > 0) ? true : false;
	}
	
	@Override
	public List<AdminFaqScoreDTO> getFaqScoreList(Map<String, Object> paramMap) {
		List<AdminFaqScoreDTO> faqScoreList = Collections.emptyList();

		int faqScoreTotalCount = adminFaqMapper.selectFaqScoreTotalCount(paramMap);

		if (faqScoreTotalCount > 0) {
			faqScoreList = adminFaqMapper.selectFaqScoreList(paramMap);
		}

		return faqScoreList;
	}
	
	@Override
	public List<AdminSupportDTO> getSupportList(Map<String, Object> paramMap) {
		List<AdminSupportDTO> supportList = adminFaqMapper.selectSupportList(paramMap);

		if(supportList == null) {
			supportList = Collections.emptyList();
		}

		return supportList;
	}
	
	@Override
	public boolean deleteSupport(Map<String, Object> paramMap) {
		int queryResult = 0;


		queryResult = adminFaqMapper.deleteSupport(paramMap);

		return (queryResult > 0) ? true : false;
	}
	
	@Override
	public AdminSupportDTO getSupport(AdminSupportDTO params) {
		return adminFaqMapper.selectSupport(params);
	}
	
	@Override
    public boolean registerSupportDetail(AdminSupportDTO params) { 
        int queryResult = 0;
        
		queryResult = adminFaqMapper.updateSupportDetail(params);
        
		if(queryResult == 1) {
			return true;
		} else {
			return false;
		}
               
    }

}
