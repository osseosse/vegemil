package com.vegemil.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.transaction.annotation.Transactional;

import com.vegemil.domain.MemberDTO;

@Mapper
public interface MemberMapper {

	public int saveMember(MemberDTO params);
	
	public int updateMember(MemberDTO params);
	
	public int activeMember(MemberDTO params);

	public MemberDTO getMemberAccount(String emailAddr);

	public int updateMemPwd(MemberDTO params);

	public List<MemberDTO> selectMemberList(MemberDTO params);

	public int selectMemberTotalCount(MemberDTO params);
	
	public int selectMemberCount(MemberDTO params);
	
	public int overlappedID(MemberDTO member) throws Exception;

}
