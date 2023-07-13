package com.vegemil.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.vegemil.domain.ClaimDTO;
import com.vegemil.domain.EventDTO;

@Mapper
public interface CommunicationMapper {
	
	public int selectOneMemberCP(String cId);
	
	public List<EventDTO> selectEventList();
	
	public EventDTO selectEvent(String eIdx);
	
	public int insertMclaim(ClaimDTO claimDTO);
	
	public String selectClaimIPRecent();
	
}
