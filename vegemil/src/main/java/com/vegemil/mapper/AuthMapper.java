package com.vegemil.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

@Mapper
public interface AuthMapper {
	
	List<String> selectAuthListByMemId(@Param("memberId") long memberId);
	
}
