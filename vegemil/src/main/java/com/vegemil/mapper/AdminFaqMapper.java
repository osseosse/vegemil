package com.vegemil.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.vegemil.domain.AdminFaqDTO;
import com.vegemil.domain.AdminFaqScoreDTO;
import com.vegemil.domain.AdminSupportDTO;

@Mapper
public interface AdminFaqMapper {

	public int insertFaq(AdminFaqDTO params);

	public AdminFaqDTO selectFaqDetail(Long idx);

	public int updateFaq(AdminFaqDTO params);

	public int deleteFaq(Map<String, Object> paramMap);

	public List<AdminFaqDTO> selectFaqList(Map<String, Object> paramMap);

	public int selectFaqTotalCount(Map<String, Object> paramMap);
	
	public int updateDisplay(Map<String, Object> paramMap);

	public int selectFaqScoreTotalCount(Map<String, Object> paramMap);
	
	public List<AdminFaqScoreDTO> selectFaqScoreList(Map<String, Object> paramMap);
	
	public List<AdminSupportDTO> selectSupportList(Map<String, Object> paramMap);
	
	public int deleteSupport(Map<String, Object> paramMap);
	
	public AdminSupportDTO selectSupport(AdminSupportDTO params);
	
	public int updateSupportDetail(AdminSupportDTO params);
}
