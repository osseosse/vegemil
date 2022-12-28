package com.vegemil.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vegemil.domain.ClaimDTO;
import com.vegemil.domain.EventDTO;
import com.vegemil.mapper.CommunicationMapper;

@Service
public class CommunicationServiceImpl implements CommunicationService{
	@Autowired
	CommunicationMapper communicationMapper;
	
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
	
}
