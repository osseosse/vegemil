package com.vegemil.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.vegemil.domain.AdminAviCFDTO;
import com.vegemil.mapper.AdminAviCFMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class AdminAviCFtServiceImpl implements AdminAviCFService{

	@Autowired
	private AdminAviCFMapper adminAviCFMapper;

	@Autowired
	private ImageServerService imageServerService;
	
	@Override
	public List<AdminAviCFDTO> getAdminAviCFList(AdminAviCFDTO params) {
		return adminAviCFMapper.selectAdminAviCFList(params);
	}
	@Override
	public boolean saveAviCF(AdminAviCFDTO params, MultipartFile uploadFile) {
		
		int queryResult = 0;

		AdminAviCFDTO preDto = adminAviCFMapper.selectAdminAviCFData(params.getTIdx());
		
		if ("U".equals(params.getAction())) {
			
			if(uploadFile.getOriginalFilename().length()>0) {
				
				if(deleteFile(preDto.getTYoutubeImg())) {
					queryResult = adminAviCFMapper.updateAdminAviCFData(this.uploadFile(uploadFile, params));
				}
				
			} else {
				
				params.setTYoutubeImg(preDto.getTYoutubeImg());
				queryResult = adminAviCFMapper.updateAdminAviCFData(params);
			}
		} else if("D".equals(params.getAction())) {
			
			if(deleteFile(preDto.getTYoutubeImg())) {
				queryResult = adminAviCFMapper.deleteAdminAviCFData(preDto.getTIdx());
			}
		}else {
			queryResult = adminAviCFMapper.insertAdminAviCF(uploadFile(uploadFile, params));
		}
		
		return (queryResult == 1) ? true : false;
	}
	
	@Override
	public boolean changeOnairStatus(AdminAviCFDTO params) {
		int queryResult = 0;
		queryResult = adminAviCFMapper.updatetOnairStatus(params);
		return (queryResult == 1) ? true : false;
	}
	
	@Override
	public AdminAviCFDTO getAdminAviCFData(String tIdx) {
		return adminAviCFMapper.selectAdminAviCFData(tIdx);
	}

	private AdminAviCFDTO uploadFile(MultipartFile uploadFile, AdminAviCFDTO params) {
		try {
			String originalName = uploadFile.getOriginalFilename();

			if(originalName != null && originalName.length() > 0) {
				String imageUrl = imageServerService.upload(uploadFile, "cf");
				params.setTYoutubeImg(imageUrl);
				return params;
			}
		}catch(Exception e) {
			log.error("CF 이미지 업로드 실패", e);
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
