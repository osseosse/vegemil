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

import com.vegemil.domain.AdminPrintAdDTO;
import com.vegemil.domain.AdminRadioCMDTO;
import com.vegemil.mapper.AdminPrintAdMapper;

@Service
@Transactional
public class AdminPrintADServiceImpl implements AdminPrintADService{
	
	@Autowired
	private AdminPrintAdMapper adminPrintAdMapper;
	
	@Value("${spring.servlet.multipart.location}")
    private String uploadPath;
	
	@Override
	public List<AdminPrintAdDTO> getPrintADList(AdminPrintAdDTO params) {
		return adminPrintAdMapper.selectPrintADList(params);
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
			
			if(originalName.length() > 0) {
				
				originalName = UUID.randomUUID().toString().substring(0,3) + "_" + originalName;			
								
				Path savePath = Paths.get(uploadPath+ "/upload/PAD/" + originalName);					
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
			File file = new File(uploadPath+ "/upload/PAD/" +fileName);
			file.delete();
			return true;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
