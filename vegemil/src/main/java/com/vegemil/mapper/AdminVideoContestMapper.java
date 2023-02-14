package com.vegemil.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.vegemil.domain.AdminAdEctDTO;
import com.vegemil.domain.AdminVideoContestDTO;

@Mapper
public interface AdminVideoContestMapper {
	
	public List<AdminVideoContestDTO> selectVideoContestList(AdminVideoContestDTO params);
	public int insertVideoContest(AdminVideoContestDTO params);
	public int deleteVideoContestData(String tIdx);
	public AdminVideoContestDTO selectVideoContestData(String tIdx);
	public int updatetOnairStatus(AdminVideoContestDTO params);
	public int updateVideoContestData(AdminVideoContestDTO params);
}
