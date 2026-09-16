package com.vegemil.service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vegemil.domain.AdminEventDTO;
import com.vegemil.domain.DataTableDTO;
import com.vegemil.domain.PopupDTO;
import com.vegemil.domain.ThermometerLoveDTO;
import com.vegemil.mapper.AdminEventMapper;
import com.vegemil.mapper.PopupMapper;

import lombok.extern.slf4j.Slf4j;


@Service
@Transactional
@Slf4j
public class AdminEventServiceImpl implements AdminEventService {
	
	@Autowired
	private AdminEventMapper adminEventMapper;

	@Autowired
	private PopupMapper	popupMapper;

	@Autowired
	private ImageServerService imageServerService;
	
	//이벤트 조회 - 베지밀
	@Override
	public DataTableDTO getVegemilEventList(Map<String, Object> paramMap) {
		
		List<AdminEventDTO> vegemilEventList = Collections.emptyList();
		DataTableDTO dataTableDto = new DataTableDTO();

		int vegemilEventTotalCount = adminEventMapper.selectVegemilEventTotalCount(paramMap);		

		if (vegemilEventTotalCount > 0) {
			
			  int start = Integer.parseInt(paramMap.get("start").toString()); 
			  int length = Integer.parseInt(paramMap.get("length").toString());
			  
			  paramMap.put("start", start);
			  paramMap.put("length", length);
			 
			  vegemilEventList = adminEventMapper.selectVegemilEventList(paramMap);			 
		}
		
		dataTableDto.setData(vegemilEventList);
		dataTableDto.setRecordsTotal(vegemilEventTotalCount);
		dataTableDto.setRecordsFiltered(vegemilEventTotalCount);
		dataTableDto.setDraw(Integer.parseInt(paramMap.get("draw").toString()));

		return dataTableDto;
	}

	//이벤트 등록 및 리스트화면에서 전시여부 수정 - 베지밀
	@Override
	public boolean registerEvent(AdminEventDTO params) throws Exception {
		
		

		if (params.getEIdx() == null) {
			String originalName = params.getFileName().getOriginalFilename();

			if(originalName != null && !"".equals(originalName)) {
				String imageUrl = imageServerService.upload(params.getFileName(), "event");
				params.setEImg(imageUrl);
				params.setEImgOriginal(originalName);
			}
		}
		
		int queryResult = 0;
		if (params.getEIdx() == null) {
			queryResult = adminEventMapper.insertEvent(params);
		} else {
			queryResult = adminEventMapper.updateEvent(params);
		}
		return (queryResult == 1)? true : false;
	}
	
	//이벤트 수정
	@Override
	public boolean updateEvent(AdminEventDTO params) throws Exception {

		String storedImgOriginal = null;
		String storedImg = null;
		
		//DB에 저장된 파일 불러오기
		if(params.getCategory().equals("vegemil")) {
			storedImgOriginal = adminEventMapper.selectImgFileOriginal(params.getEIdx());	
			storedImg = adminEventMapper.selectImgFile(params.getEIdx());			
		}else if(params.getCategory().equals("vegemilBaby")) {
			storedImgOriginal = adminEventMapper.selectImgFileOriginalVB(params.getEIdx());
			storedImg = adminEventMapper.selectImgFileVB(params.getEIdx());
		}
		
		//전달된 파일
		String originalName = params.getFileName().getOriginalFilename();

		
		if(originalName != null && !"".equals(originalName)) {
			if(storedImgOriginal == null || !originalName.equals(storedImgOriginal)) {
				String imageUrl = imageServerService.upload(params.getFileName(), "event");
				params.setEImg(imageUrl);
				params.setEImgOriginal(originalName);
			}
		}

		int queryResult = 0;		
		queryResult = adminEventMapper.updateEvent(params);		
		return (queryResult == 1)? true : false;		
		}
	
	
	
	//이벤트삭제 - 베지밀
	@Override
	public boolean deleteVegemilEvent(Map<String, Object> paramMap) {
		int queryResult = 0;
		queryResult = adminEventMapper.deleteVegemilEvent(paramMap);
		return (queryResult > 0)?  true : false;
	}

	//이벤트삭제 - 영유아식
	@Override
	public boolean deleteVegemilBabyEvent(Map<String, Object> paramMap) {
		int queryResult = 0;
		queryResult = adminEventMapper.deleteVegemilBabyEvent(paramMap);
		return (queryResult > 0)?  true : false;
	}

	// 베지밀 이벤트 상세조회
	@Override
	public AdminEventDTO getEventDetail(Long eIdx) {
		return adminEventMapper.selectEventInfoDetail(eIdx);
	}

	// 영유아식 이벤트 상세조회
	@Override
	public AdminEventDTO getEventDetailVB(Long eIdx) {
		return adminEventMapper.selectEventInfoDetailVB(eIdx);
	}

	@Override
	public ThermometerLoveDTO getThermometerLove(int year) {
		
		
		ThermometerLoveDTO love= adminEventMapper.selectThermometerLove(year);
		
		if(love == null) {
			log.info("사랑의온도계 이벤트 정보 없음");
			return null;
		}
		
		LocalDateTime fromDate = love.getFromDate();
		LocalDateTime current = LocalDateTime.now();
		log.info("이벤트 오픈 ====== > " + fromDate);
		
		if(current.isAfter(fromDate) == false) {
			log.info("사랑의 온도계 오픈 전");
			return null;
		}
		
		return adminEventMapper.selectThermometerLove(year);
	}
	
	@Override
	public ThermometerLoveDTO getThermometerLoveAdmin(int year) {
		
		return adminEventMapper.selectThermometerLove(year);
	}
	

	@Override
	public boolean updateLoveThermometer(ThermometerLoveDTO dto) {
		
		ThermometerLoveDTO findDto = adminEventMapper.selectThermometerLove(dto.getYear());
	
		int result = 0;
		
		if(findDto == null) {
			result = adminEventMapper.insertLoveThermometer(dto);
		}else {			
			result = adminEventMapper.updateLoveThermometer(dto);
		}
		
		return (result > 0)? true : false;
	}

	@Override
	public DataTableDTO getPopupList(Map<String, Object> paramMap) {
		DataTableDTO datas = new DataTableDTO();
		
		datas.setData(popupMapper.selectAllPopupList());
		return datas;
	}

	@Override
	public int changeActiveStatus(PopupDTO popupDto) {
		return popupMapper.updateAvtiveColumn(popupDto);
	}

	@Override
	public int postPopup(PopupDTO popupDTO) {
		
		int result = 0;
		
		if(popupDTO.getIdx() == null) {
			result = popupMapper.insertPopupRow(popupDTO);
		}else {
			// 수정 추후 개발 
		}
		
		return result;
	}

	

}
