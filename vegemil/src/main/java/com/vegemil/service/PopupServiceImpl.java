package com.vegemil.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vegemil.domain.FactpostDTO;
import com.vegemil.domain.MediaNewsDTO;
import com.vegemil.domain.PopupDTO;
import com.vegemil.domain.ScheduleDTO;
import com.vegemil.domain.SearchDTO;
import com.vegemil.domain.VisitDTO;
import com.vegemil.mapper.PopupMapper;
import com.vegemil.mapper.RndMapper;
import com.vegemil.paging.PaginationInfo;

@Service
public class PopupServiceImpl implements PopupService {
	
	@Autowired
	PopupMapper popupMapper;

	@Override
	public List<PopupDTO> getPopupInfo() {
		return popupMapper.selectPopupList();
	}

	@Override
	public int activePopupCount() {
		return popupMapper.selectActiveCount();
	}


	
}