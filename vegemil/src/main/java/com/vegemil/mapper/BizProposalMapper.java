package com.vegemil.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.vegemil.domain.BizProposalDTO;
import com.vegemil.paging.PaginationInfo;

@Mapper
public interface BizProposalMapper {
	
	
	// 단건 조회 
	public BizProposalDTO selectProposalById(Long id);
	
	// 10개씩 페이징 조회
	
	public List<BizProposalDTO> selectProposalsWithPaging(PaginationInfo pageInfo);
	
	// 인서트 
	public Long insertBizProposal(BizProposalDTO bizProposalDTO);
	
	// 확인 체크 
	public void updateIsCheck(Long id, boolean check);
	

}
