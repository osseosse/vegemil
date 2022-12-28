package com.vegemil.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.vegemil.domain.AdminDTO;
import com.vegemil.domain.MemberDTO;

@Mapper
public interface AdminMapper {

	public int insertAdmin(MemberDTO memberDTO);

	public MemberDTO findAdminById(String aId);

	public int updateAdminPwd(MemberDTO memberDTO);

	public List<AdminDTO> selectAdminList(MemberDTO memberDTO);

	public int selectAdminTotalCount(MemberDTO memberDTO);
	
	public int checkMember(MemberDTO memberDTO);
	
	public int selectAdminCount(MemberDTO memberDTO);
	
	public int activeAdmin(MemberDTO memberDTO);

}
