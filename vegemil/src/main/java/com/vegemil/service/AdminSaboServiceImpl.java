package com.vegemil.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vegemil.domain.AdminSaboDTO;
import com.vegemil.domain.AdminWebzineEventDTO;
import com.vegemil.domain.DataTableDTO;
import com.vegemil.mapper.AdminSaboMapper;

@Service
@Transactional
public class AdminSaboServiceImpl implements AdminSaboService {

	@Autowired
	private AdminSaboMapper adminSaboMapper;
		

	//사보 신청자 조회
	@Override
	public DataTableDTO getSaboSubscribeList(Map<String, Object> paramMap) {
		List<AdminSaboDTO> saboSubscriberList = Collections.emptyList();
		DataTableDTO dataTableDto = new DataTableDTO();

		int saboSubscriberTotalCount = adminSaboMapper.selectSaboSubscribeTotalCount(paramMap);

		if (saboSubscriberTotalCount > 0) {			
			
			  int start = Integer.parseInt(paramMap.get("start").toString()); 
			  int length = Integer.parseInt(paramMap.get("length").toString());
			  
			  paramMap.put("start", start);
			  paramMap.put("length", length);
			 
			  saboSubscriberList = adminSaboMapper.selectSaboSubscribeList(paramMap);
			  			 
		}
		
		dataTableDto.setData(saboSubscriberList);
		dataTableDto.setRecordsTotal(saboSubscriberTotalCount);
		dataTableDto.setRecordsFiltered(saboSubscriberTotalCount);
		dataTableDto.setDraw(Integer.parseInt(paramMap.get("draw").toString()));

		return dataTableDto;
	}


	@Override
	public boolean deleteSaboSubscribe(Map<String, Object> paramMap) {
		int queryResult = 0;
		
		queryResult = adminSaboMapper.deleteSaboSubscribe(paramMap);
		
		return (queryResult > 0)?  true : false;
	}
	
	/**
	 * 웹진이벤트관리
	 */
	@Override
	public DataTableDTO getWebzineEventList(Map<String, Object> paramMap) {
		List<AdminWebzineEventDTO> webzineEventList = Collections.emptyList();
		DataTableDTO dataTableDto = new DataTableDTO();

		int totalCount = adminSaboMapper.selectWebzineEventTotalCount(paramMap);

		if (totalCount > 0) {			
			
			  int start = Integer.parseInt(paramMap.get("start").toString()); 
			  int length = Integer.parseInt(paramMap.get("length").toString());
			  
			  paramMap.put("start", start);
			  paramMap.put("length", length);
			 
			  webzineEventList = adminSaboMapper.selectWebzineEventList(paramMap);
			  			 
		}
		
		dataTableDto.setData(webzineEventList);
		dataTableDto.setRecordsTotal(totalCount);
		dataTableDto.setRecordsFiltered(totalCount);
		dataTableDto.setDraw(Integer.parseInt(paramMap.get("draw").toString()));

		return dataTableDto;
	}
}
