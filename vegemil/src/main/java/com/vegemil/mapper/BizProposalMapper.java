package com.vegemil.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.vegemil.domain.BizProposalDTO;

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
	public int updateIsCheck(@Param("id") Long id,@Param("isCheck") int isCheck);
	
	// 전체 데이터 수 
	public int selectAll();
	

}
