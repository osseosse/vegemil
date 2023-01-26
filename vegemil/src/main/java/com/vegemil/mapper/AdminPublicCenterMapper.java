package com.vegemil.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.omg.CORBA.PUBLIC_MEMBER;

import com.vegemil.domain.AdminMediaNewsDTO;

@Mapper
public interface AdminPublicCenterMapper {

	//보도자료 등록
	public int insertMediaNews(AdminMediaNewsDTO params);
	
	//보도자료 수
	public int selectMediaNewsTotalCount(Map<String, Object> paramMap);
	
	//보도자료 조회
	public List<AdminMediaNewsDTO> selectMediaNewsList(Map<String, Object> paramMap);
	
	//보도자료 수정
	public int updateMediaNews(AdminMediaNewsDTO params);
	
	//첨부파일 조회 - 원본명
	public String selectImgFileOriginal(Long mIdx);

	//첨부파일 조회 - 변환명
	public String selectImgFile(Long mIdx);
	
	//첨부파일 삭제
	public void updateFileInfo(Long mIdx);
	
	//보도자료 삭제
	public int deleteMediaNews(Map<String, Object> paramMap);

	//보도자료 조회
	public AdminMediaNewsDTO selectMediaNews(Long mIdx);

	
}
