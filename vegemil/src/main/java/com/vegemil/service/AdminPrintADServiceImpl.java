package com.vegemil.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.vegemil.domain.AdminPrintAdDTO;
import com.vegemil.mapper.AdminPrintAdMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class AdminPrintADServiceImpl implements AdminPrintADService{

	@Autowired
	private AdminPrintAdMapper adminPrintAdMapper;

	@Autowired
	private ImageServerService imageServerService;
	
	@Override
	public List<AdminPrintAdDTO> getPrintADList(AdminPrintAdDTO params) {
		return adminPrintAdMapper.selectPrintADList(params);
	}
	
	@Override
	public List<AdminPrintAdDTO> getPrintADListForDisplay() {
		return adminPrintAdMapper.selectPrintADListLimit();
	}
	
	@Override
	public boolean savePrintAD(AdminPrintAdDTO params, MultipartFile uploadFile) {
		
		int queryResult = 0;

		AdminPrintAdDTO preDto = adminPrintAdMapper.selectPrintADData(params.getTIdx());
		
		if ("U".equals(params.getAction())) {
			
			if(uploadFile.getOriginalFilename().length()>0) {
				
				if(deleteFile(preDto.getTImg())) {
					queryResult = adminPrintAdMapper.updatePrintADData(this.uploadFile(uploadFile, params));
				}
				
			} else {
				
				params.setTImg(preDto.getTImg());
				queryResult = adminPrintAdMapper.updatePrintADData(params);
			}
		} else if("D".equals(params.getAction())) {
			
			if(deleteFile(preDto.getTImg())) {
				queryResult = adminPrintAdMapper.deletePrintADData(params.getTIdx());
			}
		}else {
			queryResult = adminPrintAdMapper.insertPrintADData(uploadFile(uploadFile, params));
		}
		
		return (queryResult == 1) ? true : false;
	}
	
	@Override
	public boolean changeOnairStatus(AdminPrintAdDTO params) {
		int queryResult = 0;
		queryResult = adminPrintAdMapper.updatetOnairStatus(params);
		return (queryResult == 1) ? true : false;
	}
	
	@Override
	public AdminPrintAdDTO getPrintADData(String tIdx) {
		return adminPrintAdMapper.selectPrintADData(tIdx);
	}
	
	private AdminPrintAdDTO uploadFile(MultipartFile uploadFile, AdminPrintAdDTO params) {
		try {
			String originalName = uploadFile.getOriginalFilename();

			if(originalName != null && originalName.length() > 0) {
				String imageUrl = imageServerService.upload(uploadFile, "pad");
				params.setTImg(imageUrl);
				return params;
			}
		}catch(Exception e) {
			log.error("인쇄광고 이미지 업로드 실패", e);
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
