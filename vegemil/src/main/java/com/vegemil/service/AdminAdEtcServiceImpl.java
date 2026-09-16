package com.vegemil.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.vegemil.domain.AdminAdEctDTO;
import com.vegemil.mapper.AdminAdEtcMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class AdminAdEtcServiceImpl implements AdminAdEtcService{

	@Autowired
	private AdminAdEtcMapper adminAdEtcMapper;

	@Autowired
	private ImageServerService imageServerService;
	
	@Override
	public List<AdminAdEctDTO> getAdminAdEtcList(AdminAdEctDTO params) {
		return adminAdEtcMapper.selectAviAdList(params);
	}
	
	@Override
	public AdminAdEctDTO getAdminEtcData(String tIdx) {
		return adminAdEtcMapper.selectAviData(tIdx);
	}
	
	@Override
	public boolean saveAdEtc(AdminAdEctDTO params, MultipartFile uploadFile) {
		
		int queryResult = 0;

		AdminAdEctDTO preDto = adminAdEtcMapper.selectAviData(params.getTIdx());
		
		if ("U".equals(params.getAction())) {
			
			if(uploadFile.getOriginalFilename().length()>0) {
				
				if(deleteFile(preDto.getTImgNew())) {
					queryResult = adminAdEtcMapper.updateAdEtcData(uploadFile(uploadFile, params));
				}
				
			} else {
				
				params.setTImgNew(preDto.getTImgNew());
				queryResult = adminAdEtcMapper.updateAdEtcData(params);
			}
			
		} else if("D".equals(params.getAction())) {
			
			if(deleteFile(preDto.getTImgNew())) {
				queryResult = adminAdEtcMapper.deleteAdEtcData(preDto.getTIdx());
			}
			
		}else {
			queryResult = adminAdEtcMapper.insertAdEtc(uploadFile(uploadFile, params));
		}
		
		return (queryResult == 1) ? true : false;
	}

	@Override
	public boolean changeOnairStatus(AdminAdEctDTO params) {
		int queryResult = 0;
		
		if(params.getTIdx() != null) {
			queryResult = adminAdEtcMapper.updatetOnairStatus(params);
		}
		return (queryResult == 1) ? true : false;
	}
	
	private AdminAdEctDTO uploadFile(MultipartFile uploadFile, AdminAdEctDTO params) {
		try {
			String originalName = uploadFile.getOriginalFilename();

			if(originalName != null && originalName.length() > 0) {
				String imageUrl = imageServerService.upload(uploadFile, "om");
				params.setTImgNew(imageUrl);
				return params;
			}
		}catch(Exception e) {
			log.error("기타광고 이미지 업로드 실패", e);
		}
		return params;
	}

	public boolean deleteFile(String fileName) {
		if(fileName == null || "".equals(fileName)) {
			return true;
		}
		log.info("이미지 서버 파일 삭제 생략: {}", fileName);
		return true;
	}
}
