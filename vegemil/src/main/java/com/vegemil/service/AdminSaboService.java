package com.vegemil.service;

import java.util.Map;

import com.vegemil.domain.DataTableDTO;

public interface AdminSaboService {	
	
	//사보 신청자 리스트
	public DataTableDTO getSaboSubscribeList(Map<String, Object> paramMap);
	
	//사보 신청자 삭제
	public boolean deleteSaboSubscribe(Map<String, Object> paramMap);	

	/**
	 * 웹진이벤트관리
	 * @param paramMap
	 * @return
	 */
	public DataTableDTO getWebzineEventList(Map<String, Object> paramMap);
}
