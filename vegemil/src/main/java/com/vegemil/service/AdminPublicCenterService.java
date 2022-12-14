package com.vegemil.service;

import java.util.Map;

import com.vegemil.domain.AdminMediaNewsDTO;
import com.vegemil.domain.DataTableDTO;

public interface AdminPublicCenterService {	
	
	//보도자료 등록
	public boolean registerMediaNews(AdminMediaNewsDTO params);

	//보도자료 리스트
	public DataTableDTO getMediaNewsList(Map<String, Object> paramMap);
		
	//보도자료 수정
	
	//보도자료 삭제
	public boolean deleteMediaNews(Map<String, Object> paramMap);	

	
	
}
