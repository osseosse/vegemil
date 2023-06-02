package com.vegemil.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vegemil.domainEday.EdayVempDTO;
import com.vegemil.mapperEday.EdaymallVempMapper;

@Service
public class EdayVempServiceImpl implements EdayVempService {

	@Autowired
	private EdaymallVempMapper  edaymallVempMapper;

	@Override
	public EdayVempDTO getVempInfo(String email) {
		return edaymallVempMapper.selectVempMember(email);
	}

	

}
