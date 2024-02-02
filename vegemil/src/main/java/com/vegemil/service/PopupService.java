package com.vegemil.service;

import java.util.List;

import com.vegemil.domain.FactpostDTO;
import com.vegemil.domain.PopupDTO;
import com.vegemil.domain.ScheduleDTO;
import com.vegemil.domain.SearchDTO;
import com.vegemil.domain.VisitDTO;

public interface PopupService {
	

	public List<PopupDTO> getPopupInfo();
	public int activePopupCount();
	
}
