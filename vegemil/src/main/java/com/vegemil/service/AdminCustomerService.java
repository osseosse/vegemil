package com.vegemil.service;

import java.util.List;
import java.util.Map;

import com.vegemil.domain.AdminFactpostDTO;
import com.vegemil.domain.AdminVisitDTO;
import com.vegemil.domain.AdminVisitSetupDTO;
import com.vegemil.domain.BizProposalDTO;
import com.vegemil.domain.DataTableDTO;
import com.vegemil.domain.MemberDTO;
import com.vegemil.domain.SearchDTO;

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
	
	// 비즈니스 문의 리스트 	
	public List<BizProposalDTO> getBizProposalList(SearchDTO searchDTO);
	public DataTableDTO getBizProposalList(Map<String, Object> paramMap);
	
	// 비즈니스 단건
	
	public BizProposalDTO getBizProposalData(Long id);
	
	
	//비즈니스 체크 기능 
	public boolean changeIsCheckStatus(Long id, int status);
	
	

}
