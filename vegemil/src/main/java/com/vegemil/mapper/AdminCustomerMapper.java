package com.vegemil.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.vegemil.domain.AdminFactpostDTO;
import com.vegemil.domain.AdminVisitDTO;
import com.vegemil.domain.AdminVisitSetupDTO;
import com.vegemil.domain.MemberDTO;

@Mapper
public interface AdminCustomerMapper {

	public int selectGreenbiaMemberTotalCount(Map<String, Object> paramMap);
	
	public List<MemberDTO> selectGreenbiaMemberList(Map<String, Object> paramMap);
	
	public int deleteGreenbiaMember(Long mIdx);
	
	public MemberDTO selectGreenbiaMember(Long mIdx);
	
	public int updateGreenbiaMember(MemberDTO params);
	
	public int selectMemberTotalCount(Map<String, Object> paramMap);
	
	public List<MemberDTO> selectMemberList(Map<String, Object> paramMap);
	
	public MemberDTO selectMember(Long mIdx);
	
	public int updateMember(MemberDTO params);
	
	public int selectVisitTotalCount(Map<String, Object> paramMap);
	
	public List<AdminVisitDTO> selectVisitList(Map<String, Object> paramMap);
	
	public int updateVisit(AdminVisitDTO params);
	
	public int deleteVisit(Map<String, Object> paramMap);
	
	public AdminVisitDTO selectVisitDetail(Long vIdx);
	
	public int updateDisplayVisit(AdminVisitDTO params);
	
	public int selectFactoryTourReviewTotalCount(Map<String, Object> paramMap);
	
	public List<AdminFactpostDTO> selectFactoryTourReviewList(Map<String, Object> paramMap);
	
	public AdminVisitSetupDTO selectVisitSetup();
	
	public int updateVisitSetup(AdminVisitSetupDTO params);
	
	public int updateFactoryTourReview(AdminFactpostDTO params);
	
	public int deleteFactoryTourReview(AdminFactpostDTO params);
}
