package com.vegemil.service;

import java.util.Map;

import com.vegemil.domain.AdminEventDTO;
import com.vegemil.domain.DataTableDTO;

public interface AdminEventService {
	
	//이벤트 조회 - 베지밀
	public DataTableDTO getEventVegemilList(Map<String, Object> paramMap);

	//이벤트 등록 - 베지밀
	public boolean registerEvent(AdminEventDTO params) throws Exception;
	
	
	
	


	
}
