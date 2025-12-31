package com.vegemil.service;

import java.util.List;
import java.util.Map;

import com.vegemil.domain.BizProposalDTO;
import com.vegemil.domain.ClaimDTO;
import com.vegemil.domain.EventDTO;
import com.vegemil.domain.SearchDTO;
import com.vegemil.paging.PaginationInfo;

public interface CommunicationService {
	
	public int checkCompId(String cId);
	
	public int insertMclaim(ClaimDTO claimDTO);
	
	public List<EventDTO> getEnevetList();
	
	public EventDTO getEvent(String eIdx);
	
	public String getRecentClaimIp();
	
	// 업체 문의 기능 추가 // 
	
	// 문의글 단거 조회 
	
	public BizProposalDTO getBizProposal(Long id);
	
	// 문의글 페이징 조회
	public List<BizProposalDTO> getBizProposals(SearchDTO searchDTO);
	public List<BizProposalDTO> getBizProposals(Map<String, Object> map);
	
	// 문의글 등록
	public Long enrollBizProposal(BizProposalDTO bizProposalDTO);
	
	
	// 문의글 check 업데이트 
	public BizProposalDTO changeCheckStatus(Long id, int check);
	public BizProposalDTO changeCheckStatus(BizProposalDTO bizProposalDTO);

	
}
