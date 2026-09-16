package com.vegemil.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.vegemil.domain.AdminVideoContestDTO;
import com.vegemil.mapper.AdminVideoContestMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class AdminVideoContestServiceImpl implements AdminVideoContestService{

	@Autowired
	private AdminVideoContestMapper adminVideoContestMapper;

	@Autowired
	private ImageServerService imageServerService;
	
	@Override
	public List<AdminVideoContestDTO> getAdminVideoContestList(AdminVideoContestDTO params) {
		return adminVideoContestMapper.selectVideoContestList(params);
	}
	
	@Override
	public boolean saveVideoContest(AdminVideoContestDTO params, MultipartFile uploadFile) {
		
		int queryResult = 0;

		AdminVideoContestDTO preDto = adminVideoContestMapper.selectVideoContestData(params.getTIdx());
		
		if ("U".equals(params.getAction())) {
			
			if(uploadFile.getOriginalFilename().length()>0) {
				
				if(deleteFile(preDto.getTYoutubeImg())) {
					queryResult = adminVideoContestMapper.updateVideoContestData(this.uploadFile(uploadFile, params));
				}
				
			} else {
				
				params.setTYoutubeImg(preDto.getTYoutubeImg());
				queryResult = adminVideoContestMapper.updateVideoContestData(params);
			}
		} else if("D".equals(params.getAction())) {
			
			if(deleteFile(preDto.getTYoutubeImg())) {
				queryResult = adminVideoContestMapper.deleteVideoContestData(preDto.getTIdx());
			}
		}else {
			queryResult = adminVideoContestMapper.insertVideoContest(uploadFile(uploadFile, params));
		}
		
		return (queryResult == 1) ? true : false;
	}
	
	
	@Override
	public boolean changeOnairStatus(AdminVideoContestDTO params) {
		int queryResult = 0;
		queryResult = adminVideoContestMapper.updatetOnairStatus(params);
		return (queryResult == 1) ? true : false;
	}

	@Override
	public AdminVideoContestDTO getAdminContestVideoData(String tIdx) {
		return adminVideoContestMapper.selectVideoContestData(tIdx);
	}	
	
	
	private AdminVideoContestDTO uploadFile(MultipartFile uploadFile, AdminVideoContestDTO params) {
		try {
			String originalName = uploadFile.getOriginalFilename();

			if(originalName != null && originalName.length() > 0) {
				String imageUrl = imageServerService.upload(uploadFile, "vc");
				params.setTYoutubeImg(imageUrl);
				return params;
			}
		}catch(Exception e) {
			log.error("영상공모전 이미지 업로드 실패", e);
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
