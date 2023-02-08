package com.vegemil.service;

import java.io.File;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
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

		if ("U".equals(params.getAction())) {
			AdminAdEctDTO preDto = adminAdEtcMapper.selectAviData(params.getTIdx());
			
			if(uploadFile.getOriginalFilename().length()>0) {
				
				if(deleteFile(preDto.getTImgNew())) {
					queryResult = adminAdEtcMapper.updateAdEtcData(uploadFile(uploadFile, params));
				}
				
			} else {
				
				params.setTImgNew(preDto.getTImgNew());
				queryResult = adminAdEtcMapper.updateAdEtcData(params);
			}
			
		} else if("D".equals(params.getAction())) {
			
			if(deleteFile(params.getTImgNew())) {
				queryResult = adminAdEtcMapper.deleteAdEtcData(params.getTIdx());
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
				
				String dirPath = "\\\\211.233.87.7\\data\\images\\dcf\\vegemil\\upload\\OM\\";//"D:/data/dcf/";
				originalName = UUID.randomUUID().toString().substring(0,3) + "_" + originalName;
				
				File file = new File(dirPath + originalName);
				uploadFile.transferTo(file);
				params.setTImgNew(originalName);
				
				return params;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return params;
		
	}
	public boolean deleteFile(String fileName) {
		
		String dirPath = "\\\\211.233.87.7\\data\\images\\dcf\\vegemil\\upload\\OM\\";//"D:/data/dcf/";
		
		try {
			File file = new File(dirPath+fileName);
			file.delete();
			return true;
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
}
