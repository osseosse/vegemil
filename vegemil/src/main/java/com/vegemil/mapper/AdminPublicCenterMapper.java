package com.vegemil.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.vegemil.domain.AdminMediaNewsDTO;

@Mapper
public interface AdminPublicCenterMapper {

	//보도자료 등록
	
	//보도자료 수
	public int selectMediaNewsTotalCount(Map<String, Object> paramMap);
	
	//보도자료 조회
	public List<AdminMediaNewsDTO> selectMediaNewsList(Map<String, Object> paramMap);
	
	//보도자료 수정
	
	//보도자료 삭제
	public int deleteMediaNews(Map<String, Object> paramMap);
}
