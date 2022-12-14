package com.vegemil.mapper;

import com.vegemil.domain.WebzineDTO;
import com.vegemil.domain.WebzineEventDTO;
import com.vegemil.domain.SearchDTO;
import com.vegemil.domain.SubscribeDTO;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface WebzineMapper {

	public int webzineSave(WebzineDTO params);
	
	public int webzineEventSave(WebzineEventDTO params);
	
	public int subscribeSave(SubscribeDTO params);

	public WebzineDTO webzineFindByIdx(Long fIdx);
	
	public int webzineCountByIdx(Long fIdx);
	
	public int webzineEventCountByQrt(WebzineEventDTO eventDto);

	public void webzineUpdate(WebzineDTO params);

	public void deleteWebzineByIdx(Long fIdx);

	public List<WebzineDTO> webzineFindAll(SearchDTO params);
	
	public List<SubscribeDTO> selectSubscibeAll();
	
	public List<WebzineDTO> selectWebzineRandom(String qrtYear);
	
	public List<WebzineDTO> selectWebzineByQrtYear(String qrtYear);

	public int webzineCount();
	
	public int subscibeCount();
	
	public int isSubscibeByEmail(String email);
	
	public WebzineDTO selectWebzine(WebzineDTO param);

	public List<WebzineDTO> selectWebzineYear();
	
	public List<WebzineDTO> selectWebzineQrt();
	
	public List<WebzineDTO> selectWebzineLink();
	
	public List<WebzineDTO> selectWebzineQ(String qrtYear);
	
	public List<WebzineDTO> selectWebzine02();
	public List<WebzineDTO> selectWebzine03();
	public List<WebzineDTO> selectWebzine04();
	public List<WebzineDTO> selectWebzine05();
	public List<WebzineDTO> selectWebzine06();
	
	public List<WebzineDTO> selectWebzineSearchList(SearchDTO params);
	public int selectWebzineSearchCount(SearchDTO params);
	
	
}
