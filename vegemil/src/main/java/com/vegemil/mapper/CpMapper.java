package com.vegemil.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.vegemil.domain.ClaimDTO;

@Mapper
public interface CpMapper {
	public int selectOneMemberCP(String cId);
	public int insertMclaim(ClaimDTO claimDTO);
}
