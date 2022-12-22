package com.vegemil.service;

import com.vegemil.domain.ClaimDTO;

public interface CpService {
	
	public int checkCompId(String cId);
	public int insertMclaim(ClaimDTO claimDTO);

}
