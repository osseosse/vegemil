package com.vegemil.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.vegemil.domain.AdminFactpostDTO;
import com.vegemil.domain.AdminVisitDTO;
import com.vegemil.domain.AdminVisitSetupDTO;
import com.vegemil.domain.DataTableDTO;
import com.vegemil.domain.MemberDTO;
import com.vegemil.mapper.AdminCustomerMapper;

@Service
public class AdminCustomerServiceImpl implements AdminCustomerService {

	@Autowired
	private AdminCustomerMapper adminCustomerMapper;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	@Override
	public DataTableDTO getGreenbiaList(Map<String, Object> paramMap) {
		List<MemberDTO> greenbiaMemberList = Collections.emptyList();
		DataTableDTO dataTableDto = new DataTableDTO();

		int greenbiaMemberTotalCount = adminCustomerMapper.selectGreenbiaMemberTotalCount(paramMap);

		if (greenbiaMemberTotalCount > 0) {
			int start = Integer.parseInt(paramMap.get("start").toString());
			int length = Integer.parseInt(paramMap.get("length").toString());
			
			paramMap.put("start", start);
			paramMap.put("length", length);
			greenbiaMemberList = adminCustomerMapper.selectGreenbiaMemberList(paramMap);
		}
		
		dataTableDto.setData(greenbiaMemberList);
		dataTableDto.setRecordsTotal(greenbiaMemberTotalCount);
		dataTableDto.setRecordsFiltered(greenbiaMemberTotalCount);
		dataTableDto.setDraw(Integer.parseInt(paramMap.get("draw").toString()));

		return dataTableDto;
	}
	
	@Override
	public boolean deleteGreenbia(Long mIdx) {
		int queryResult = 0;
		
		queryResult = adminCustomerMapper.deleteGreenbiaMember(mIdx);
		
		return (queryResult == 1) ? true : false;
	}
	
	@Override
	public MemberDTO getGreenbia(Long mIdx) {
		return adminCustomerMapper.selectGreenbiaMember(mIdx);
	}
	
	@Override
	public boolean saveGreenbia(MemberDTO params) {
		int queryResult = 0;
		
		queryResult = adminCustomerMapper.updateGreenbiaMember(params);
		
		return (queryResult == 1) ? true : false;
	}
	
	@Override
	public DataTableDTO getMemberList(Map<String, Object> paramMap) {
		List<MemberDTO> memberList = Collections.emptyList();
		DataTableDTO dataTableDto = new DataTableDTO();

		int memberTotalCount = adminCustomerMapper.selectMemberTotalCount(paramMap);

		if (memberTotalCount > 0) {
			int start = Integer.parseInt(paramMap.get("start").toString());
			int length = Integer.parseInt(paramMap.get("length").toString());
			
			paramMap.put("start", start);
			paramMap.put("length", length);
			memberList = adminCustomerMapper.selectMemberList(paramMap);
		}
		
		dataTableDto.setData(memberList);
		dataTableDto.setRecordsTotal(memberTotalCount);
		dataTableDto.setRecordsFiltered(memberTotalCount);
		dataTableDto.setDraw(Integer.parseInt(paramMap.get("draw").toString()));

		return dataTableDto;
	}
	
	@Override
	public MemberDTO getMember(Long mIdx) {
		return adminCustomerMapper.selectMember(mIdx);
	}
	
	@Override
	public boolean saveMember(MemberDTO params) {
		int queryResult = 0;
		
		queryResult = adminCustomerMapper.updateMember(params);
		
		return (queryResult == 1) ? true : false;
	}
	
	@Override
	public DataTableDTO getVisitList(Map<String, Object> paramMap) {
		List<AdminVisitDTO> visitList = Collections.emptyList();
		DataTableDTO dataTableDto = new DataTableDTO();

		int visitTotalCount = adminCustomerMapper.selectVisitTotalCount(paramMap);

		if (visitTotalCount > 0) {
			int start = Integer.parseInt(paramMap.get("start").toString());
			int length = Integer.parseInt(paramMap.get("length").toString());
			
			paramMap.put("start", start);
			paramMap.put("length", length);
			visitList = adminCustomerMapper.selectVisitList(paramMap);
		}
		
		dataTableDto.setData(visitList);
		dataTableDto.setRecordsTotal(visitTotalCount);
		dataTableDto.setRecordsFiltered(visitTotalCount);
		dataTableDto.setDraw(Integer.parseInt(paramMap.get("draw").toString()));

		return dataTableDto;
	}
	
	@Override
	public boolean saveVisit(AdminVisitDTO params) {
		int queryResult = 0;
		
		queryResult = adminCustomerMapper.updateVisit(params);
		
		return (queryResult == 1) ? true : false;
	}
	
	@Override
	public boolean deleteVisit(Map<String, Object> paramMap) {
		int queryResult = 0;


		queryResult = adminCustomerMapper.deleteVisit(paramMap);

		return (queryResult > 0) ? true : false;
	}
	
	@Override
	public AdminVisitDTO getVisitDetail(Long mIdx) {
		return adminCustomerMapper.selectVisitDetail(mIdx);
	}
	
	@Override
	public boolean saveDisplayVisit(AdminVisitDTO params) {
		int queryResult = 0;
		
		queryResult = adminCustomerMapper.updateDisplayVisit(params);
		
		return (queryResult == 1) ? true : false;
	}
	
	@Override
	public DataTableDTO getFactoryTourReviewList(Map<String, Object> paramMap) {
		List<AdminFactpostDTO> factoryTourReviewList = Collections.emptyList();
		DataTableDTO dataTableDto = new DataTableDTO();

		int totalCount = adminCustomerMapper.selectFactoryTourReviewTotalCount(paramMap);

		if (totalCount > 0) {
			int start = Integer.parseInt(paramMap.get("start").toString());
			int length = Integer.parseInt(paramMap.get("length").toString());
			
			paramMap.put("start", start);
			paramMap.put("length", length);
			factoryTourReviewList = adminCustomerMapper.selectFactoryTourReviewList(paramMap);
		}
		
		dataTableDto.setData(factoryTourReviewList);
		dataTableDto.setRecordsTotal(totalCount);
		dataTableDto.setRecordsFiltered(totalCount);
		dataTableDto.setDraw(Integer.parseInt(paramMap.get("draw").toString()));

		return dataTableDto;
	}
	
	@Override
	public AdminVisitSetupDTO getVisitSetup() {
		return adminCustomerMapper.selectVisitSetup();
	}
	
	@Override
	public boolean saveVisitSetup(AdminVisitSetupDTO params) {
		int queryResult = 0;
		
		queryResult = adminCustomerMapper.updateVisitSetup(params);
		
		return (queryResult == 1) ? true : false;
	}
	
	@Override
	public boolean saveFactoryTourReview(AdminFactpostDTO params) {
		int queryResult = 0;
		
		if("U".equals(params.getAction())) {
			queryResult = adminCustomerMapper.updateFactoryTourReview(params);
		}else if("D".equals(params.getAction())) {
			queryResult = adminCustomerMapper.deleteFactoryTourReview(params);
		}
		
		return (queryResult == 1) ? true : false;
	}
	
	@Override
	public boolean savePassword(MemberDTO params) {
		int queryResult = 0;
		
		String password = "abcd1234";
		params.setMPwd(passwordEncoder.encode(password));
		
		queryResult = adminCustomerMapper.updatePassword(params);
		
		return (queryResult == 1) ? true : false;
	}
	
	
	
}
