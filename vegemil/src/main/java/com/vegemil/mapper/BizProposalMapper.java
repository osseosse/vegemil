package com.vegemil.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.vegemil.domain.BizProposalDTO;
import com.vegemil.domain.SearchDTO;
import com.vegemil.paging.PaginationInfo;

@Mapper
public interface BizProposalMapper {
	
	
	// 단건 조회 
	public BizProposalDTO selectProposalById(Long id);
	
	// 10개씩 페이징 조회
	
//	public List<BizProposalDTO> selectProposalsWithPaging(SearchDTO pageInfo);
	public List<BizProposalDTO> selectProposalsWithPaging(Map<String, Object> map);
	
	// 인서트 
	public Long insertBizProposal(BizProposalDTO bizProposalDTO);
	
	// 확인 체크 
	public int updateIsCheck(Long id, int status);
	

}
