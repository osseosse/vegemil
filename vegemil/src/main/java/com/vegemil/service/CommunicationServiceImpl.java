package com.vegemil.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.vegemil.domain.BizProposalDTO;
import com.vegemil.domain.ClaimDTO;
import com.vegemil.domain.EventDTO;
import com.vegemil.mapper.BizProposalMapper;
import com.vegemil.mapper.CommunicationMapper;
import com.vegemil.paging.PaginationInfo;

@Service
public class CommunicationServiceImpl implements CommunicationService{
	@Autowired
	CommunicationMapper communicationMapper;
	@Autowired
	BizProposalMapper bizProposalMapper;
	
	@Override
	public BizProposalDTO getBizProposal(Long id) {
		// BizProposalDTO bizProposalDTO
		return bizProposalMapper.selectProposalById(id);
	}

	@Override
	public List<BizProposalDTO> getBizProposals(PaginationInfo pageInfo) {
		// TODO Auto-generated method stub
		return bizProposalMapper.selectProposalsWithPaging(pageInfo);
	}

	@Override
	public Long enrollBizProposal(BizProposalDTO bizProposalDTO) {
		// TODO Auto-generated method stub		
		return bizProposalMapper.insertBizProposal(bizProposalDTO);
	}

	@Override
	public BizProposalDTO changeCheckStatus(Long id, boolean check) {
		// TODO Auto-generated method stub
		// 이거 성공하면 1 뱉나? 확인해서 체크 코드 넣을 것 
		bizProposalMapper.updateIsCheck(id, check);
		return bizProposalMapper.selectProposalById(id);
	}

	@Override
	public BizProposalDTO changeCheckStatus(BizProposalDTO bizProposalDTO) {
		// TODO Auto-generated method stub
		
		if(!StringUtils.isEmpty(bizProposalDTO.getId())) {
			bizProposalMapper.updateIsCheck(bizProposalDTO.getId(), bizProposalDTO.isCheck());	
		}
		
		return bizProposalMapper.selectProposalById(bizProposalDTO.getId());
	}
	
	
	
	

	@Override
	public int checkCompId(String cId) {
		return communicationMapper.selectOneMemberCP(cId);
	}

	@Override
	public int insertMclaim(ClaimDTO claimDTO) {
		
		claimDTO.setCTel(claimDTO.getTel01().trim() + "-" + claimDTO.getTel02().trim() + "-" + claimDTO.getTel03().trim());
		claimDTO.setCHp(claimDTO.getHp01().trim() + "-" + claimDTO.getHp02().trim() + "-" + claimDTO.getHp03().trim());
		claimDTO.setCEmail(claimDTO.getEmail01().trim() + claimDTO.getEmail02());
		claimDTO.setCAnswer("N");
		return communicationMapper.insertMclaim(claimDTO);
	}
	
	@Override
	public List<EventDTO> getEnevetList() {
		
		List<EventDTO> eventList = Collections.emptyList();
		eventList = communicationMapper.selectEventList();
		
        return eventList;
		
	}
	
	@Override
	public EventDTO getEvent(String eIdx) {
		
		EventDTO event = communicationMapper.selectEvent(eIdx);
		
		return event;
	}

	@Override
	public String getRecentClaimIp() {
		return communicationMapper.selectClaimIPRecent();
	}


	
}
