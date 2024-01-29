package com.vegemil.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.vegemil.domain.FactpostDTO;
import com.vegemil.domain.PopupDTO;
import com.vegemil.domain.ScheduleDTO;
import com.vegemil.domain.SearchDTO;
import com.vegemil.domain.VisitDTO;

@Mapper
public interface PopupMapper {
	
	public List<PopupDTO> selectPopupList();
	public int selectActiveCount();

}
