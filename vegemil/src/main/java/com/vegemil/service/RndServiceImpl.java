package com.vegemil.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vegemil.domain.VisitDTO;
import com.vegemil.mapper.RndMapper;

@Service
public class RndServiceImpl implements RndService {
	
	@Autowired
	RndMapper mapper;

	@Override
	public int insertMvisit(VisitDTO visitDTO) {
		return mapper.insertMvisit(visitDTO);
	}
	
	

}
