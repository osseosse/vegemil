package com.vegemil.mapper;

import com.vegemil.domain.MediaNewsDTO;
import com.vegemil.domain.SearchDTO;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CompanyMapper {

	public int mediaNewsSave(MediaNewsDTO params);

	public MediaNewsDTO mediaNewsFindByIdx(Long fIdx);
	
	public int mediaNewsCountByIdx(Long fIdx);
	
	public void mediaNewsUpdate(MediaNewsDTO params);

	public void deleteMediaNewsByIdx(Long fIdx);

	public List<MediaNewsDTO> mediaNewsFindAll(SearchDTO params);
	
	public List<MediaNewsDTO> selectMediaNewsTop3();

	public int updateMediaNewsCount(Long mIdx);

	public int mediaNewsCount(SearchDTO params);

}
