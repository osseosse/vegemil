package com.vegemil.service;

import java.util.List;

import com.vegemil.domain.ClaimDTO;
import com.vegemil.domain.EventDTO;

public interface CommunicationService {
	
	public int checkCompId(String cId);
	
	public int insertMclaim(ClaimDTO claimDTO);
	
	public List<EventDTO> getEnevetList();
	
	public EventDTO getEvent(String eIdx);

}
