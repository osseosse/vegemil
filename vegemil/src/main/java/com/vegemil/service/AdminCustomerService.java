package com.vegemil.service;

import java.util.Map;

import com.vegemil.domain.AdminFactpostDTO;
import com.vegemil.domain.AdminVisitDTO;
import com.vegemil.domain.AdminVisitSetupDTO;
import com.vegemil.domain.DataTableDTO;
import com.vegemil.domain.MemberDTO;

public interface AdminCustomerService {

	public DataTableDTO getGreenbiaList(Map<String, Object> paramMap);
	
	public boolean deleteGreenbia(Long mIdx);
	
	public MemberDTO getGreenbia(Long mIdx);
	
	public boolean saveGreenbia(MemberDTO params);
	
	public DataTableDTO getMemberList(Map<String, Object> paramMap);
	
	public MemberDTO getMember(Long mIdx);
	
	public boolean withdrawalMember(Long mIdx);
	
	public boolean saveMember(MemberDTO params);
	
	public DataTableDTO getVisitList(Map<String, Object> paramMap);
	
	public boolean saveVisit(AdminVisitDTO params);
	
	public boolean deleteVisit(Map<String, Object> paramMap);
	
	public AdminVisitDTO getVisitDetail(Long vIdx); 
	
	public boolean saveDisplayVisit(AdminVisitDTO params); 
	
	public DataTableDTO getFactoryTourReviewList(Map<String, Object> paramMap);
	
	public AdminVisitSetupDTO getVisitSetup();
	
	public boolean saveVisitSetup(AdminVisitSetupDTO params);
	
	public boolean saveFactoryTourReview(AdminFactpostDTO params);
	
	public boolean savePassword(MemberDTO params);
	

}
