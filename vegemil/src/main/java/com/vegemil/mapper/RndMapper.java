package com.vegemil.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.vegemil.domain.VisitDTO;

@Mapper
public interface RndMapper {
	
	public int insertMvisit(VisitDTO visitDTO);

}
