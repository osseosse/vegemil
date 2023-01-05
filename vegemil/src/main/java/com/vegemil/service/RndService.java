package com.vegemil.service;

import java.util.List;

import com.vegemil.domain.ScheduleDTO;
import com.vegemil.domain.VisitDTO;

public interface RndService {
	
	public int insertMvisit(VisitDTO visitDTO);
	
	public List<ScheduleDTO> getTourScheduleList();
	
	public List<VisitDTO> getVisitList();
	
}
