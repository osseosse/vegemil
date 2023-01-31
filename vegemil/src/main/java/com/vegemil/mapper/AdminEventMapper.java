package com.vegemil.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.vegemil.domain.AdminEventDTO;

@Mapper
public interface AdminEventMapper {
	
	/* 베지밀 이벤트 갯수 */
	public int selectVegemilEventTotalCount(Map<String, Object> paramMap);

	/* 베지밀 이벤트 조회 */
	public List<AdminEventDTO> selectVegemilEventList(Map<String, Object> paramMap);

	/* 베지밀 이벤트  등록*/
	public int insertEvent(AdminEventDTO params);
	
	/* 베지밀 이벤트  삭제*/
	public int deleteVegemilEvent(Map<String, Object> paramMap);
	
	/* 첨부파일 조회 - 변환명 */
	public String selectImgFile(Long mIdx);
	
	
}
