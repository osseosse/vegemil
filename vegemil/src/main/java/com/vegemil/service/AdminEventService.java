package com.vegemil.service;

import java.util.Map;

import com.vegemil.domain.AdminEventDTO;
import com.vegemil.domain.DataTableDTO;

public interface AdminEventService {
	
	//이벤트 조회 - 베지밀
	public DataTableDTO getVegemilEventList(Map<String, Object> paramMap);

	//이벤트 등록 - 베지밀
	public boolean registerEvent(AdminEventDTO params) throws Exception;
	
	//이벤트 수정 - 베지밀
	public boolean updateEvent(AdminEventDTO params) throws Exception;
	
	//이벤트삭제 - 베지밀
	public boolean deleteVegemilEvent(Map<String, Object> paramMap);
	
	//이벤트 상세 조회 - 베지밀
	public AdminEventDTO getEventDetail(Long eIdx);

	
	


	
}
