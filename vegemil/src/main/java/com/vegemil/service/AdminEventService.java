package com.vegemil.service;

import java.util.Map;

import com.vegemil.domain.AdminEventDTO;
import com.vegemil.domain.DataTableDTO;
import com.vegemil.domain.ThermometerLoveDTO;

public interface AdminEventService {
	
	//이벤트 조회 - 베지밀
	public DataTableDTO getVegemilEventList(Map<String, Object> paramMap);

	//이벤트 등록 - 베지밀
	public boolean registerEvent(AdminEventDTO params) throws Exception;
	
	//이벤트 수정 - 베지밀
	public boolean updateEvent(AdminEventDTO params) throws Exception;
	
	//이벤트삭제 - 베지밀
	public boolean deleteVegemilEvent(Map<String, Object> paramMap);

	//이벤트삭제 - 영유아식
	public boolean deleteVegemilBabyEvent(Map<String, Object> paramMap);
	
	//이벤트 상세 조회 - 베지밀
	public AdminEventDTO getEventDetail(Long eIdx);

	//이벤트 상세 조회 - 영유아식
	public AdminEventDTO getEventDetailVB(Long eIdx);

	// 사랑의 온도계 조회
	public ThermometerLoveDTO getThermometerLove(int year);
	
	// 사랑의 온도계 업데이트 
	public boolean updateLoveThermometer(ThermometerLoveDTO dto);
	
	// 팝업 리스트 조회 
	public DataTableDTO getPopupList(Map<String, Object> paramMap);

	
	


	
}
