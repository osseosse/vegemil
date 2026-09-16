package com.vegemil.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.vegemil.domain.AdminRadioCMDTO;
import com.vegemil.mapper.AdminAviRadioCMMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class AdminAviRadioCMServiceImpl implements AdminAviRadioCMService{

	@Autowired
	private AdminAviRadioCMMapper adminRadioCMMapper;

	@Autowired
	private ImageServerService imageServerService;
	
	@Override
	public List<AdminRadioCMDTO> getRadioCMList(AdminRadioCMDTO params) {
		return adminRadioCMMapper.selectRadioCMList(params);
	}
	
	@Override
	public boolean saveRadioCM(AdminRadioCMDTO params, MultipartFile uploadFile) {
		
		int queryResult = 0;

		AdminRadioCMDTO preDto = adminRadioCMMapper.selectRadioCMData(params.getTIdx());
		
		if ("U".equals(params.getAction())) {
			
			if(uploadFile.getOriginalFilename().length()>0) {
				
				if(deleteFile(preDto.getTImg())) {
					queryResult = adminRadioCMMapper.updateRadioCMData(this.uploadFile(uploadFile, params));
				}
				
			} else {
				
				params.setTImg(preDto.getTImg());
				queryResult = adminRadioCMMapper.updateRadioCMData(params);
			}
		} else if("D".equals(params.getAction())) {
			
			if(deleteFile(preDto.getTImg())) {
				queryResult = adminRadioCMMapper.deleteRadioCMData(preDto.getTIdx());
			}
		}else {
			queryResult = adminRadioCMMapper.insertRadioCMData(uploadFile(uploadFile, params));
		}
		
		return (queryResult == 1) ? true : false;
	}
	
	@Override
	public boolean changeOnairStatus(AdminRadioCMDTO params) {
		int queryResult = 0;
		queryResult = adminRadioCMMapper.updatetOnairStatus(params);
		return (queryResult == 1) ? true : false;
	}
	
	@Override
	public AdminRadioCMDTO getRadioCMData(String tIdx) {
		return adminRadioCMMapper.selectRadioCMData(tIdx);
	}
	
	private AdminRadioCMDTO uploadFile(MultipartFile uploadFile, AdminRadioCMDTO params) {
		try {
			String originalName = uploadFile.getOriginalFilename();

			if(originalName != null && originalName.length() > 0) {
				String imageUrl = imageServerService.upload(uploadFile, "rcm");
				params.setTImg(imageUrl);
				return params;
			}
		}catch(Exception e) {
			log.error("라디오CM 이미지 업로드 실패", e);
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
