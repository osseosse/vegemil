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

	/* 베지밀 이벤트 수정 */
	public int updateEvent(AdminEventDTO params);
	
	/* 베지밀 이벤트  삭제*/
	public int deleteVegemilEvent(Map<String, Object> paramMap);
	
	/* 영유아식 이벤트  삭제*/
	public int deleteVegemilBabyEvent(Map<String, Object> paramMap);
	
	/* 베지밀 이벤트 파일 조회 - 변환명 */
	public String selectImgFile(Long eIdx);	
	/* 영유아식 이벤트 파일 조회 - 변환명 */
	public String selectImgFileVB(Long idx);

	/* 베지밀 이벤트 파일 조회 - 원본명 */
	public String selectImgFileOriginal(Long eIdx);
	/* 베지밀 이벤트 파일 조회 - 변환명 */
	public String selectImgFileOriginalVB(Long eIdx);
	
	/* 베지밀 이벤트 상세 조회  */
	public AdminEventDTO selectEventInfoDetail(Long eIdx);

	/* 영유아식 이벤트 상세 조회  */
	public AdminEventDTO selectEventInfoDetailVegemilBaby(Long eIdx);

	


	
	
	
}
