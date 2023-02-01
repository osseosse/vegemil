package com.vegemil.service;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vegemil.domain.AdminMediaNewsDTO;
import com.vegemil.domain.DataTableDTO;
import com.vegemil.mapper.AdminPublicCenterMapper;

@Service
@Transactional
public class AdminPublicCenterServiceImpl implements AdminPublicCenterService {
	
	@Value("${spring.servlet.multipart.location}")
    private String uploadPath;

	@Autowired
	private AdminPublicCenterMapper adminPublicCenterMapper;
	
	//보도자료 등록
	@Override
	public boolean registerMediaNews(AdminMediaNewsDTO params) throws Exception {
		
		String uuid = UUID.randomUUID().toString();
		String originalName = params.getFileName().getOriginalFilename();
		
		if(!"".equals(originalName)) {
			String file = originalName.substring(originalName.lastIndexOf("\\")+1);			
			String savefileName = uuid + "_" +file;			
			
			//저장 - 실제경로
			Path savePath = Paths.get(uploadPath+ "/upload/MediaNews/" + savefileName);			
			//저장 - Test로컬경로
			//Path savePath = Paths.get("D:/upload/admin/" + savefileName);											
		
			params.getFileName().transferTo(savePath);				
			params.setMImg(savefileName);	
			params.setMImgOriginal(originalName);
			
		}	
		
		int queryResult = 0;		
		queryResult = adminPublicCenterMapper.insertMediaNews(params);
		
		return (queryResult == 1)? true : false;
	}
	
	//보도자료 조회
	@Override
	public DataTableDTO getMediaNewsList(Map<String, Object> paramMap) {
		
		List<AdminMediaNewsDTO> mediaNewsList = Collections.emptyList();
		DataTableDTO dataTableDto = new DataTableDTO();

		int mediaNewsTotalCount = adminPublicCenterMapper.selectMediaNewsTotalCount(paramMap);		

		if (mediaNewsTotalCount > 0) {
			
			  int start = Integer.parseInt(paramMap.get("start").toString()); 
			  int length = Integer.parseInt(paramMap.get("length").toString());
			  
			  paramMap.put("start", start);
			  paramMap.put("length", length);
			 
			  mediaNewsList = adminPublicCenterMapper.selectMediaNewsList(paramMap);			 
		}
		
		dataTableDto.setData(mediaNewsList);
		dataTableDto.setRecordsTotal(mediaNewsTotalCount);
		dataTableDto.setRecordsFiltered(mediaNewsTotalCount);
		dataTableDto.setDraw(Integer.parseInt(paramMap.get("draw").toString()));

		return dataTableDto;
	}
	

	//보도자료 수정
	@Override
	public boolean updateMediaNews(AdminMediaNewsDTO params) throws Exception {

		//DB에 저장된 파일 불러오기
		String storedImgOriginal = adminPublicCenterMapper.selectImgFileOriginal(params.getMIdx());
		String storedImg = adminPublicCenterMapper.selectImgFile(params.getMIdx());
		
		//전달된 파일
		String originalName = params.getFileName().getOriginalFilename();
		String uuid = UUID.randomUUID().toString();

		
		if(storedImgOriginal == null || storedImgOriginal.equals("") ) { // 1. DB에 첨부 파일 존재X
			System.out.println("=============DB에  파일이 없습니다.==============");
			if(originalName != null && !"".equals(originalName)) { //   1 -1 :전달되어온 파일이 존재
				System.out.println("=============새로 입력되는 파일이 있습니다.==============");
				String file = originalName.substring(originalName.lastIndexOf("\\")+1);			
				String savefileName = uuid + "_" +file;			
				
				//저장 - 실제경로
				Path savePath = Paths.get(uploadPath+ "/upload/MediaNews/" + savefileName);				
				//저장 - Test로컬경로
				//Path savePath = Paths.get("D:/upload/admin/" + savefileName);											
			
				params.getFileName().transferTo(savePath);				
				params.setMImg(savefileName);
				params.setMImgOriginal(originalName);
			}else {
				System.out.println("=============새로 입력되는 파일이 없습니다.==============");
			}
			
		}else {														 // 2. DB에  첨부 파일 존재
			System.out.println("=============DB에  파일이 있습니다.==============");
			if(originalName != null && !"".equals(originalName)) {  //전달된 파일이 존재
				System.out.println("=============새로 입력되는 파일이 있습니다.==============");
				System.out.println("기존 파일명:" + storedImgOriginal);
				System.out.println("새로 등록 파일명:" + originalName);
				if(!originalName.equals(storedImgOriginal)) { //전달된 파일과 기존 파일이 다르면 		
					System.out.println("=============전달될 파일과 기존 파일이 다릅니다..==============");
					
					String file = originalName.substring(originalName.lastIndexOf("\\")+1);			
					String savefileName = uuid + "_" +file;			
									
					//저장 - 실제경로
					Path savePath = Paths.get(uploadPath+ "/upload/MediaNews/" + savefileName);					
					//저장 - Test로컬경로					
					//Path savePath = Paths.get("D:/upload/admin/" + savefileName);											
				
					params.getFileName().transferTo(savePath);				
					params.setMImg(savefileName);
					params.setMImgOriginal(originalName);
										
					//삭제 - 실제경로
					String storedfilePath = uploadPath+ "/upload/MediaNews/" + storedImg;
					//삭제 - Test로컬경로						
					//String storedfilePath = "D:/upload/admin/" + storedImg;
					
			        File deleteFile = new File(storedfilePath);
			        if(deleteFile.exists()) {			            
			            deleteFile.delete(); 			            
			            System.out.println("파일을 삭제하였습니다.");			            
			        } else {
			            System.out.println("파일이 존재하지 않습니다.");
			        }
				}				
			}else {
				System.out.println("=============새로 입력되는 파일이 없습니다.==============");
			}			
		}

		int queryResult = 0;		
		queryResult = adminPublicCenterMapper.updateMediaNews(params);		
		return (queryResult == 1)? true : false;		
	}
		
	//보도자료 삭제
	@Override
	public boolean deleteMediaNews(Map<String, Object> paramMap) {
		
		ArrayList<String> newsList = (ArrayList<String>) paramMap.get("list");
		
		for(int i = 0; i< newsList.size(); i++) {
			Long idx = Long.parseLong(newsList.get(i));
			String storedImg = adminPublicCenterMapper.selectImgFile(idx);
			
			if(storedImg == null || storedImg.equals("") ) { // DB에 첨부 파일 존재			
				System.out.println("DB에  파일이 없습니다");
			}else {
				//삭제 - 실제경로
				String storedfilePath = uploadPath+ "/upload/MediaNews/" + storedImg;			
				//삭제 - Test로컬경로						
				//String storedfilePath = "D:/upload/admin/" + storedImg;
				
		        File deleteFile = new File(storedfilePath);
		        if(deleteFile.exists()) {			            
		            deleteFile.delete(); 			            
		            System.out.println("파일을 삭제하였습니다.");			            
		        } else {
		            System.out.println("파일이 존재하지 않습니다.");
		        }
			}	
		}	
		int queryResult = 0;		
		queryResult = adminPublicCenterMapper.deleteMediaNews(paramMap);		
		return (queryResult > 0)?  true : false;
	
	}

	
	//보도자료 조회
	@Override
	public AdminMediaNewsDTO getMediaNewsDetail(Long mIdx) {
		return adminPublicCenterMapper.selectMediaNews(mIdx);
	}
}
