package com.vegemil.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.vegemil.domain.AdminSaboDTO;

@Mapper
public interface AdminSaboMapper {

	public int selectSaboSubscribeTotalCount(Map<String, Object> paramMap);
	
	public List<AdminSaboDTO> selectSaboSubscribeList(Map<String, Object> paramMap);
	
	public int deleteSaboSubscribe(Map<String, Object> paramMap);
}
