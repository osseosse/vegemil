package com.vegemil.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.vegemil.domain.MemberDTO;

@Mapper
public interface AdminCustomerMapper {

	public int selectGreenbiaMemberTotalCount(Map<String, Object> paramMap);
	
	public List<MemberDTO> selectGreenbiaMemberList(Map<String, Object> paramMap);
	
	public int deleteGreenbiaMember(Long mIdx);
	
	public MemberDTO selectGreenbiaMember(Long mIdx);
	
	public int updateGreenbiaMember(MemberDTO params);
	
	public int selectMemberTotalCount(Map<String, Object> paramMap);
	
	public List<MemberDTO> selectMemberList(Map<String, Object> paramMap);
	
	public MemberDTO selectMember(Long mIdx);
}
