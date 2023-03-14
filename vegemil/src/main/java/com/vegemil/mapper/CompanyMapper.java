package com.vegemil.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.vegemil.domain.AgencyDTO;
import com.vegemil.domain.MediaNewsDTO;
import com.vegemil.domain.SearchDTO;

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
	
	public List<AgencyDTO> selectAgencyByArea(AgencyDTO params);

}
