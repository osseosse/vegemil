package com.vegemil.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.vegemil.domain.AdminSaboDTO;
import com.vegemil.domain.AdminWebzineEventDTO;

@Mapper
public interface AdminSaboMapper {

	public int selectSaboSubscribeTotalCount(Map<String, Object> paramMap);
	
	public List<AdminSaboDTO> selectSaboSubscribeList(Map<String, Object> paramMap);
	
	public int deleteSaboSubscribe(Map<String, Object> paramMap);
	
	public int selectWebzineEventTotalCount(Map<String, Object> paramMap);
	
	public List<AdminWebzineEventDTO> selectWebzineEventList(Map<String, Object> paramMap);
}
