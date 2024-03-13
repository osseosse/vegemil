package com.vegemil.mapperEday;

import org.apache.ibatis.annotations.Mapper;

import com.vegemil.domain.MemberDTO;

@Mapper
public interface EdaymallDualJoinSPMapper {

	public MemberDTO spJoinMemFromVege(MemberDTO member);
}
