package com.vegemil.mapperEday;

import org.apache.ibatis.annotations.Mapper;

import com.vegemil.domainEday.EdayVempDTO;



@Mapper
public interface EdaymallVempMapper {
	

	public EdayVempDTO selectVempMember(String email);

}
