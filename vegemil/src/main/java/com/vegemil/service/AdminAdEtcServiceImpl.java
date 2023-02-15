package com.vegemil.service;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.vegemil.domain.AdminAdEctDTO;
import com.vegemil.mapper.AdminAdEtcMapper;

@Service
@Transactional
public class AdminAdEtcServiceImpl implements AdminAdEtcService{
	
	@Autowired
	private AdminAdEtcMapper adminAdEtcMapper;
	
	@Value("${spring.servlet.multipart.location}")
    private String uploadPath;
	
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
		
		if(params.getTOnair() == null) {
			params.setTOnair("0");
		}
		
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
			
			if(originalName.length() > 0) {
				
				originalName = UUID.randomUUID().toString().substring(0,3) + "_" + originalName;			
								
				Path savePath = Paths.get(uploadPath+ "/upload/OM/" + originalName);					
				uploadFile.transferTo(savePath);
				params.setTImgNew(originalName);
				
				return params;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return params;
	}
	
	public boolean deleteFile(String fileName) {
		
		try {
			File file = new File(uploadPath+ "/upload/OM/" +fileName);
			file.delete();
			return true;
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
}
