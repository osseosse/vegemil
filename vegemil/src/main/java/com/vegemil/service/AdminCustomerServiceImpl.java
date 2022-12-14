package com.vegemil.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vegemil.domain.DataTableDTO;
import com.vegemil.domain.MemberDTO;
import com.vegemil.mapper.AdminCustomerMapper;

@Service
public class AdminCustomerServiceImpl implements AdminCustomerService {

	@Autowired
	private AdminCustomerMapper adminCustomerMapper;
	
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
}
