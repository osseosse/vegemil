package com.vegemil.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vegemil.domain.ScheduleDTO;
import com.vegemil.domain.VisitDTO;
import com.vegemil.mapper.RndMapper;

@Service
public class RndServiceImpl implements RndService {
	
	@Autowired
	RndMapper rndMapper;

	@Override
	public int insertMvisit(VisitDTO visitDTO) {
		if(visitDTO.getVTel() == null || "".equals(visitDTO.getVTel())) {
			visitDTO.setVTel(visitDTO.getVHp());
		}
		return rndMapper.insertMvisit(visitDTO);
	}
	
	@Override
    public List<VisitDTO> getVisitList() {
		
		List<VisitDTO> visitList = Collections.emptyList();
		visitList = rndMapper.selectVisitList();
		
        return visitList;
    }
	
	@Override
    public List<ScheduleDTO> getTourScheduleList() {
		
		List<ScheduleDTO> tourSchedule = Collections.emptyList();
		tourSchedule = rndMapper.selectTourScheduleList();
		
        return tourSchedule;
    }
	
}
