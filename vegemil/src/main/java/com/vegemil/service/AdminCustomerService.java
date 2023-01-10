package com.vegemil.service;

import java.util.Map;

import com.vegemil.domain.DataTableDTO;
import com.vegemil.domain.MemberDTO;

public interface AdminCustomerService {

	public DataTableDTO getGreenbiaList(Map<String, Object> paramMap);
	
	public boolean deleteGreenbia(Long mIdx);
	
	public MemberDTO getGreenbia(MemberDTO params);
	
	public boolean saveGreenbia(MemberDTO params);
}
