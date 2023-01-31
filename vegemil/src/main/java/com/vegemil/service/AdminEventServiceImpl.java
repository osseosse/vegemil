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

import com.vegemil.domain.AdminEventDTO;
import com.vegemil.domain.DataTableDTO;
import com.vegemil.mapper.AdminEventMapper;

@Service
@Transactional
public class AdminEventServiceImpl implements AdminEventService {
	
	@Value("${spring.servlet.multipart.location}")
    private String uploadPath;

	@Autowired
	private AdminEventMapper adminEventMapper;
	
	//이벤트 조회 - 베지밀
	@Override
	public DataTableDTO getVegemilEventList(Map<String, Object> paramMap) {
		List<AdminEventDTO> vegemilEventList = Collections.emptyList();
		DataTableDTO dataTableDto = new DataTableDTO();

		int vegemilEventTotalCount = adminEventMapper.selectVegemilEventTotalCount(paramMap);		

		if (vegemilEventTotalCount > 0) {
			
			  int start = Integer.parseInt(paramMap.get("start").toString()); 
			  int length = Integer.parseInt(paramMap.get("length").toString());
			  
			  paramMap.put("start", start);
			  paramMap.put("length", length);
			 
			  vegemilEventList = adminEventMapper.selectVegemilEventList(paramMap);			 
		}
		
		dataTableDto.setData(vegemilEventList);
		dataTableDto.setRecordsTotal(vegemilEventTotalCount);
		dataTableDto.setRecordsFiltered(vegemilEventTotalCount);
		dataTableDto.setDraw(Integer.parseInt(paramMap.get("draw").toString()));

		return dataTableDto;
	}

	//이벤트 등록 - 베지밀
	@Override
	public boolean registerEvent(AdminEventDTO params) throws Exception {
		
		String uuid = UUID.randomUUID().toString();
		String originalName = params.getFileName().getOriginalFilename();
		
		if(!"".equals(originalName)) {
			
			String file = originalName.substring(originalName.lastIndexOf("\\")+1);			
			String savefileName = uuid + "_" +file;			
						
			//저장 - 실제경로
			Path savePath = Paths.get(uploadPath+ "/upload/EVENT/" + savefileName);
			
			//저장 - Test로컬경로
			//Path savePath = Paths.get("D:/upload/admin/" + savefileName);											
		
			params.getFileName().transferTo(savePath);				
			params.setEImg(savefileName);	
			params.setEImgOriginal(originalName);
			
		}	
		
		int queryResult = 0;
		queryResult = adminEventMapper.insertEvent(params);
		
		return (queryResult == 1)? true : false;

	}
	
	//이벤트 수정
	@Override
	public boolean updateEvent(AdminEventDTO params) throws Exception {

		//DB에 저장된 파일 불러오기
		String storedImgOriginal = adminEventMapper.selectImgFileOriginal(params.getEIdx());
		String storedImg = adminEventMapper.selectImgFile(params.getEIdx());
		
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
				Path savePath = Paths.get(uploadPath+ "/upload/EVENT/" + savefileName);				
				//저장 - Test로컬경로
				//Path savePath = Paths.get("D:/upload/admin/" + savefileName);											
			
				params.getFileName().transferTo(savePath);				
				params.setEImg(savefileName);
				params.setEImgOriginal(originalName);
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
					Path savePath = Paths.get(uploadPath+ "/upload/EVENT/" + savefileName);					
					//저장 - Test로컬경로					
					//Path savePath = Paths.get("D:/upload/admin/" + savefileName);											
				
					params.getFileName().transferTo(savePath);				
					params.setEImg(savefileName);
					params.setEImgOriginal(originalName);
										
					//삭제 - 실제경로
					String storedfilePath = uploadPath+ "/upload/EVENT/" + storedImg;
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
		queryResult = adminEventMapper.updateEvent(params);		
		return (queryResult == 1)? true : false;		
		}
	
	
	
	//이벤트삭제 - 베지밀
	@Override
	public boolean deleteVegemilEvent(Map<String, Object> paramMap) {
				
		ArrayList<String> eventList = (ArrayList<String>) paramMap.get("list");
		
		for(int i = 0; i< eventList.size(); i++) {
			Long idx = Long.parseLong(eventList.get(i));
			String storedImg = adminEventMapper.selectImgFile(idx);
			
			if(storedImg == null || storedImg.equals("") ) { // DB에 첨부 파일 존재			
				System.out.println("DB에  파일이 없습니다");
			}else {
				//삭제 - 실제경로
				String storedfilePath = uploadPath+ "/upload/EVENT/" + storedImg;			
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
		queryResult = adminEventMapper.deleteVegemilEvent(paramMap);		
		return (queryResult > 0)?  true : false;
	}

	@Override
	public AdminEventDTO getEventDetail(Long eIdx) {
		return adminEventMapper.selectEventInfoDetail(eIdx);
	}
	
	

}
