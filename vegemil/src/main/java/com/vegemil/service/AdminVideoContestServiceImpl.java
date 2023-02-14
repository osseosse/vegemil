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
import com.vegemil.domain.AdminVideoContestDTO;
import com.vegemil.mapper.AdminVideoContestMapper;

@Service
@Transactional
public class AdminVideoContestServiceImpl implements AdminVideoContestService{
	
	@Autowired
	private AdminVideoContestMapper adminVideoContestMapper;
	
	@Value("${spring.servlet.multipart.location}")
    private String uploadPath;
	
	@Override
	public List<AdminVideoContestDTO> getAdminVideoContestList(AdminVideoContestDTO params) {
		return adminVideoContestMapper.selectVideoContestList(params);
	}
	
	@Override
	public boolean saveVideoContest(AdminVideoContestDTO params, MultipartFile uploadFile) {
		
		if(params.getTOnair() == null) {
			params.setTOnair("0");
		}
		
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
		
		/* for test 
		try {
			String originalName = uploadFile.getOriginalFilename();
			
			if(originalName.length() > 0) {
				
				String dirPath = "\\\\211.233.87.7\\data\\images\\dcf\\vegemil\\upload\\VC\\";//"D:/data/dcf/";
				originalName = UUID.randomUUID().toString().substring(0,3) + "_" + originalName;
				
				File file = new File(dirPath + originalName);
				uploadFile.transferTo(file);
				params.setTYoutubeImg(originalName);
				
				return params;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return params;
		*/
		
		try {
			String originalName = uploadFile.getOriginalFilename();
			
			if(originalName.length() > 0) {
				
				originalName = UUID.randomUUID().toString().substring(0,3) + "_" + originalName;			
								
				Path savePath = Paths.get(uploadPath+ "/upload/VC/" + originalName);					
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
			File file = new File(uploadPath+ "/upload/VC/" +fileName);
			file.delete();
			return true;
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return false;
		
		/* for test
		 
		String dirPath = "\\\\211.233.87.7\\data\\images\\dcf\\vegemil\\upload\\VC\\";//"D:/data/dcf/";
		
		try {
			File file = new File(dirPath+fileName);
			file.delete();
			return true;
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return false;
		 */
		
	}




	
}
