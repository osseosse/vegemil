package com.vegemil.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.vegemil.domain.AdminPrintAdDTO;
import com.vegemil.domain.AdminRadioCMDTO;

@Mapper
public interface AdminPrintAdMapper {
	
	public List<AdminPrintAdDTO> selectPrintADList(AdminPrintAdDTO params);
	public int insertPrintADData(AdminPrintAdDTO params);
	public int deletePrintADData(String tIdx);
	public AdminPrintAdDTO selectPrintADData(String tIdx);
	public int updatetOnairStatus(AdminPrintAdDTO params);
	public int updatePrintADData(AdminPrintAdDTO params);
}
