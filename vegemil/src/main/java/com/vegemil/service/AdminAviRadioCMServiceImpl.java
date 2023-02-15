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
import com.vegemil.domain.AdminRadioCMDTO;
import com.vegemil.domain.AdminVideoContestDTO;
import com.vegemil.mapper.AdminAviRadioCMMapper;
import com.vegemil.mapper.AdminVideoContestMapper;

@Service
@Transactional
public class AdminAviRadioCMServiceImpl implements AdminAviRadioCMService{
	
	@Autowired
	private AdminAviRadioCMMapper adminRadioCMMapper;
	
	@Value("${spring.servlet.multipart.location}")
    private String uploadPath;
	
	@Override
	public List<AdminRadioCMDTO> getRadioCMList(AdminRadioCMDTO params) {
		return adminRadioCMMapper.selectRadioCMList(params);
	}
	
	@Override
	public boolean saveRadioCM(AdminRadioCMDTO params, MultipartFile uploadFile) {
		if(params.getTOnair() == null) {
			params.setTOnair("0");
		}
		
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
			
			if(originalName.length() > 0) {
				
				originalName = UUID.randomUUID().toString().substring(0,3) + "_" + originalName;			
								
				Path savePath = Paths.get(uploadPath+ "/upload/RCM/" + originalName);					
				uploadFile.transferTo(savePath);
				params.setTImg(originalName);

				
				return params;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return params;
		
	}
	public boolean deleteFile(String fileName) {
		
		if(fileName == null || "".equals(fileName)) {
			return true;
		}

		try {
			File file = new File(uploadPath+ "/upload/RCM/" +fileName);
			file.delete();
			return true;
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return false;
		
	}
}
