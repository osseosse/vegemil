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
import com.vegemil.domain.AdminAviCFDTO;
import com.vegemil.domain.AdminVideoContestDTO;
import com.vegemil.mapper.AdminAviCFMapper;
import com.vegemil.mapper.AdminVideoContestMapper;

@Service
@Transactional
public class AdminAviCFtServiceImpl implements AdminAviCFService{
	
	@Autowired
	private AdminAviCFMapper adminAviCFMapper;
	
	@Value("${spring.servlet.multipart.location}")
    private String uploadPath;
	
	@Override
	public List<AdminAviCFDTO> getAdminAviCFList(AdminAviCFDTO params) {
		return adminAviCFMapper.selectAdminAviCFList(params);
	}
	@Override
	public boolean saveAviCF(AdminAviCFDTO params, MultipartFile uploadFile) {
		if(params.getTOnair() == null) {
			params.setTOnair("0");
		}
		
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
			
			if(originalName.length() > 0) {
				
				originalName = UUID.randomUUID().toString().substring(0,3) + "_" + originalName;			
								
				Path savePath = Paths.get(uploadPath+ "/upload/CF/" + originalName);					
				uploadFile.transferTo(savePath);
				params.setTYoutubeImg(originalName);
				
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
			File file = new File(uploadPath+ "/upload/CF/" +fileName);
			file.delete();
			return true;
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
}
