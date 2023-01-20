package com.vegemil.service;

import java.util.List;
import java.util.Map;

import com.vegemil.domain.AdminFaqDTO;
import com.vegemil.domain.AdminFaqScoreDTO;
import com.vegemil.domain.AdminSupportDTO;
import com.vegemil.domain.DataTableDTO;

public interface AdminFaqService {

	public boolean registerFaq(AdminFaqDTO params);

	public AdminFaqDTO getFaqDetail(Long idx);

	public boolean deleteFaq(Map<String, Object> paramMap);

	public DataTableDTO getFaqList(Map<String, Object> paramMap);
	
	public boolean updateDisplay(Map<String, Object> paramMap);
	
	public List<AdminFaqScoreDTO> getFaqScoreList(Map<String, Object> paramMap);
	
	public DataTableDTO getSupportList(Map<String, Object> paramMap);

	public boolean deleteSupport(Map<String, Object> paramMap);
	
	public AdminSupportDTO getSupport(AdminSupportDTO params);
	
	public boolean registerSupportDetail(AdminSupportDTO params);
}
