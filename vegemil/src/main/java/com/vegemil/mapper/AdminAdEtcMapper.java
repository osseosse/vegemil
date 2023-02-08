package com.vegemil.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.vegemil.domain.AdminAdEctDTO;

@Mapper
public interface AdminAdEtcMapper {
	public List<AdminAdEctDTO> selectAviAdList(AdminAdEctDTO params);
	public int insertAdEtc(AdminAdEctDTO params);
	public int deleteAdEtcData(String tIdx);
	public int updateAdEtcData(AdminAdEctDTO params);
	public int updatetOnairStatus(AdminAdEctDTO params);
	public AdminAdEctDTO selectAviData(String tIdx);
}
