package com.vegemil.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vegemil.domain.ClaimDTO;
import com.vegemil.mapper.CpMapper;

@Service
public class CpServiceImpl implements CpService{
	@Autowired
	CpMapper mapper;
	
	@Override
	public int checkCompId(String cId) {
		return mapper.selectOneMemberCP(cId);
	}

	@Override
	public int insertMclaim(ClaimDTO claimDTO) {
		
		claimDTO.setCTel(claimDTO.getTel01().trim() + "-" + claimDTO.getTel02().trim() + "-" + claimDTO.getTel03().trim());
		claimDTO.setCHp(claimDTO.getHp01().trim() + "-" + claimDTO.getHp02().trim() + "-" + claimDTO.getHp03().trim());
		claimDTO.setCEmail(claimDTO.getEmail01().trim() + claimDTO.getEmail02());
		claimDTO.setCAnswer("N");
		return mapper.insertMclaim(claimDTO);
	}

}
