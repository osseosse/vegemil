package com.vegemil.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.vegemil.domain.AdminRadioCMDTO;

@Mapper
public interface AdminAviRadioCMMapper {
	
	public List<AdminRadioCMDTO> selectRadioCMList(AdminRadioCMDTO params);
	public int insertRadioCMData(AdminRadioCMDTO params);
	public int deleteRadioCMData(String tIdx);
	public AdminRadioCMDTO selectRadioCMData(String tIdx);
	public int updatetOnairStatus(AdminRadioCMDTO params);
	public int updateRadioCMData(AdminRadioCMDTO params);
}
