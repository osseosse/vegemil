package com.vegemil.service;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.vegemil.domain.AdminBabyDTO;
import com.vegemil.domain.AdminBestReviewDTO;
import com.vegemil.domain.AdminCalendarModelDTO;
import com.vegemil.domain.AdminCalendarTitleDTO;
import com.vegemil.domain.AdminCfDTO;
import com.vegemil.domain.AdminSampleBabyDTO;
import com.vegemil.domain.DataTableDTO;
import com.vegemil.mapper.AdminBabyMapper;
import com.vegemil.paging.PaginationInfo;

@Service
@Transactional
public class AdminBabyServiceImpl implements AdminBabyService {
	
	@Value("${spring.servlet.multipart.location}")
    private String uploadPath;
	
	@Autowired
	private AdminBabyMapper adminBabyMapper;
	
	@Override
	public DataTableDTO getBabyInfoList(Map<String, Object> paramMap) {
		List<AdminBabyDTO> babyInfoList = Collections.emptyList();
		DataTableDTO dataTableDto = new DataTableDTO();
		
		int babyInfoTotalCount = adminBabyMapper.selectBabyInfoTotalCount(paramMap);

		if (babyInfoTotalCount > 0) {
			int start = Integer.parseInt(paramMap.get("start").toString());
			int length = Integer.parseInt(paramMap.get("length").toString());
			
			paramMap.put("start", start);
			paramMap.put("length", length);
			babyInfoList = adminBabyMapper.selectBabyInfoList(paramMap);
		}
		
		dataTableDto.setData(babyInfoList);
		dataTableDto.setRecordsTotal(babyInfoTotalCount);
		dataTableDto.setRecordsFiltered(babyInfoTotalCount);
		dataTableDto.setDraw(Integer.parseInt(paramMap.get("draw").toString()));

		return dataTableDto;
	}
	
	@Override
	public boolean registerBabyInfo(AdminBabyDTO params, MultipartFile uploadFile) throws Exception {
		System.out.println(params.toString());

		if (params.getMbsIdx() == null) { //신규등록이라면
			
			String originalName = uploadFile.getOriginalFilename();			
			
			if(originalName != null && !"".equals(originalName)) {
				
				String file = originalName.substring(originalName.lastIndexOf("\\") + 1).replaceAll("\\s", "");
				String uuid = UUID.randomUUID().toString();
				String savefileName = uuid + "_" + file;
				
				//Test 로컬경로
				//File destinationFile = new File("D:/upload/admin/vegemilbaby/" + savefileName);
				//실제 경로
				File destinationFile = new File(uploadPath + "/upload/vegemilBaby/babyInfo/thumbnail/" + savefileName);				 

				uploadFile.transferTo(destinationFile);  // 이 메소드에 의해 저장 경로에 실질적으로 File이 생성됨
				params.setMbsImage(savefileName);	
				//params.setEImgOriginal(originalName);				
			}	
		}
		
		int queryResult = 0;		

		if (params.getMbsIdx() == null) {
			queryResult = adminBabyMapper.insertBabyInfo(params);
		} else {
			queryResult = adminBabyMapper.updateBabyInfo(params);
		}

		return (queryResult == 1) ? true : false;
	}
	
	@Override
	public AdminBabyDTO getBabyInfoDetail(Long mbsIdx) {
		return adminBabyMapper.selectBabyInfoDetail(mbsIdx);
	}
	
	@Override
	public boolean deleteBabyInfo(Map<String, Object> paramMap) {
		int queryResult = 0;


		queryResult = adminBabyMapper.deleteBabyInfo(paramMap);

		return (queryResult > 0) ? true : false;
	}
	
	@Override
	public List<AdminBabyDTO> getBabyQnaList(AdminBabyDTO params) {
		List<AdminBabyDTO> babyQnaList = Collections.emptyList();

		int babyQnaTotalCount = adminBabyMapper.selectBabyQnaTotalCount(params);

		PaginationInfo paginationInfo = new PaginationInfo(params);
		paginationInfo.setTotalRecordCount(babyQnaTotalCount);

		params.setPaginationInfo(paginationInfo);

		if (babyQnaTotalCount > 0) {
			babyQnaList = adminBabyMapper.selectBabyQnaList(params);
		}

		return babyQnaList;
	}
	
	@Override
	public boolean registerBabyQna(AdminBabyDTO params) {
		int queryResult = 0;

		if (params.getMbsIdx() == null) {
			queryResult = adminBabyMapper.insertBabyQna(params);
		} else {
			queryResult = adminBabyMapper.updateBabyQna(params);
		}

		return (queryResult == 1) ? true : false;
	}
	
	@Override
	public AdminBabyDTO getBabyQnaDetail(Long mbsIdx) {
		return adminBabyMapper.selectBabyQnaDetail(mbsIdx);
	}
	
	//이미지 회전 로직
	public static BufferedImage rotateImage(BufferedImage imageToRotate, int angle) {
        int widthOfImage = imageToRotate.getWidth();
        int heightOfImage = imageToRotate.getHeight();
        int typeOfImage = imageToRotate.getType();

        BufferedImage newImageFromBuffer = new BufferedImage(widthOfImage, heightOfImage, typeOfImage);

        Graphics2D graphics2D = newImageFromBuffer.createGraphics();

        graphics2D.rotate(Math.toRadians(angle), widthOfImage / 2, heightOfImage / 2);
        graphics2D.drawImage(imageToRotate, null, 0, 0);

        return newImageFromBuffer;
    }
	
	
	@Override
	public boolean registerCalendarModel(AdminCalendarModelDTO params) {
		int queryResult = 0;

		if (params.getCIdx() == null) {
			queryResult = adminBabyMapper.insertCalendarModel(params);
		} else {
			int angle = params.getCAngle();	
			int angle2 = params.getCAngle2();	
			System.out.println("사진1 회전각도: "+ angle );
			System.out.println("사진2 회전각도: "+ angle2 );
			
			if(angle != 0 || angle2 != 0) {				
				System.out.println("사진이 회전됐습니다.");
				String storedImgName = null;
				String storedImgName2 = null;
				
				//저장된 이미지이름  조회
				if(angle != 0) {
					storedImgName = adminBabyMapper.selectBabyImg(params);					
				}
				if(angle2 != 0) {
					storedImgName2 = adminBabyMapper.selectBabyImg2(params);
				}				
				
				try {						
					BufferedImage rotatedImage = null;
					BufferedImage rotatedImage2 = null;					
					
					if(angle != 0) {
						//이미지조회 - 실제경로
						BufferedImage originalImage = ImageIO.read(new File(uploadPath +"/upload/vegemilBaby/" +storedImgName));
						//이미지조회- Test로컬경로
						//BufferedImage originalImage = ImageIO.read(new File("D:/upload/admin/vegemilbaby/"+storedImgName));
						
						rotatedImage = rotateImage(originalImage, angle);	
						
						//회전된 이미지 저장 - 실제경로
						File rotatedImageFile = new File(uploadPath +"/upload/vegemilBaby/" +storedImgName);						
						//회전된 이미지 저장 - Test로컬경로
			            //File rotatedImageFile = new File("D:/upload/admin/vegemilbaby/"+storedImgName);
			            
						ImageIO.write(rotatedImage, "jpg", rotatedImageFile);
					}
					if(angle2 != 0) {
						//이미지조회 - 실제경로
						BufferedImage originalImage2 = ImageIO.read(new File(uploadPath +"/upload/vegemilBaby/" +storedImgName2));
						//이미지조회- Test로컬경로
						//BufferedImage originalImage2 = ImageIO.read(new File("D:/upload/admin/vegemilbaby/"+storedImgName2));
						
						rotatedImage2 = rotateImage(originalImage2, angle2);
						
						//회전된 이미지 저장 - 실제경로
						File rotatedImageFile2 = new File(uploadPath +"/upload/vegemilBaby/" +storedImgName);						
						//회전된 이미지 저장 - Test로컬경로
						//File rotatedImageFile2 = new File("D:/upload/admin/vegemilbaby/"+storedImgName2);
			           
						ImageIO.write(rotatedImage2, "jpg", rotatedImageFile2);
					}
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			queryResult = adminBabyMapper.updateCalendarModel(params);
		}
		return (queryResult == 1) ? true : false;
	}

	@Override
	public AdminCalendarModelDTO getCalendarModelDetail(Long idx) {
		return adminBabyMapper.selectCalendarModelDetail(idx);
	}

	@Override
	public boolean deleteCalendarModel(Map<String, Object> paramMap) {
		int queryResult = 0;

		queryResult = adminBabyMapper.deleteCalendarModel(paramMap);

		return (queryResult > 0) ? true : false;
	}

	@Override
	public DataTableDTO getCalendarModelList(Map<String, Object> paramMap) {
		List<AdminCalendarModelDTO> calendarModelList = Collections.emptyList();
		DataTableDTO dataTableDto = new DataTableDTO();

		int calendarModelTotalCount = adminBabyMapper.selectCalendarModelTotalCount(paramMap);

		System.out.println("paramMap: "+ paramMap);
		if (calendarModelTotalCount > 0) {
			int start = Integer.parseInt(paramMap.get("start").toString());
			int length = Integer.parseInt(paramMap.get("length").toString());
			
			
			paramMap.put("start", start);
			paramMap.put("length", length);
			calendarModelList = adminBabyMapper.selectCalendarModelList(paramMap);
		}
		
		dataTableDto.setData(calendarModelList);
		dataTableDto.setRecordsTotal(calendarModelTotalCount);
		dataTableDto.setRecordsFiltered(calendarModelTotalCount);
		dataTableDto.setDraw(Integer.parseInt(paramMap.get("draw").toString()));

		return dataTableDto;
	}
	
	
	@Override
	public List<AdminCalendarModelDTO> selectModelRank1() {
		return adminBabyMapper.selectModelRank1();		
	}
	
	
	
	@Override
	public boolean insertCalenderModelTitle(AdminCalendarTitleDTO params) {
		
		int queryResult = 0;
		queryResult= adminBabyMapper.insertCalenderModelTitle(params);		
		return (queryResult == 1)? true : false;		
	}

	@Override
	public boolean registerBestReview(AdminBestReviewDTO params) {
		int queryResult = 0;

		if (params.getSIdx() == null) {
			queryResult = adminBabyMapper.insertBestReview(params);
		} else {
			queryResult = adminBabyMapper.updateBestReview(params);
		}

		return (queryResult == 1) ? true : false;
	}

	@Override
	public AdminBestReviewDTO getBestReviewDetail(Long idx) {
		return adminBabyMapper.selectBestReviewDetail(idx);
	}

	@Override
	public DataTableDTO getBestReviewList(Map<String, Object> paramMap) {
		List<AdminBestReviewDTO> BestReviewList = Collections.emptyList();
		DataTableDTO dataTableDto = new DataTableDTO();

		int bestReviewTotalCount = adminBabyMapper.selectBestReviewTotalCount(paramMap);

		if (bestReviewTotalCount > 0) {
			int start = Integer.parseInt(paramMap.get("start").toString());
			int length = Integer.parseInt(paramMap.get("length").toString());
			
			paramMap.put("start", start);
			paramMap.put("length", length);
			BestReviewList = adminBabyMapper.selectBestReviewList(paramMap);
		}
		
		dataTableDto.setData(BestReviewList);
		dataTableDto.setRecordsTotal(bestReviewTotalCount);
		dataTableDto.setRecordsFiltered(bestReviewTotalCount);
		dataTableDto.setDraw(Integer.parseInt(paramMap.get("draw").toString()));

		return dataTableDto;
	}
	
	@Override
	public boolean deleteBestReview(Map<String, Object> paramMap) { 
        int queryResult = 0;
        queryResult = adminBabyMapper.deleteBestReview(paramMap);
		if(queryResult > 0) {
			return true;
		} else {
			return false;
		}
               
    }
	
	@Override
	public boolean registerSampleBaby(AdminSampleBabyDTO params) {
		int queryResult = 0;

		if (params.getSIdx() == null) {
			queryResult = adminBabyMapper.insertSampleBaby(params);
		} else {
			queryResult = adminBabyMapper.updateSampleBaby(params);
		}

		return (queryResult == 1) ? true : false;
	}

	@Override
	public AdminSampleBabyDTO getSampleBabyDetail(Long idx) {
		return adminBabyMapper.selectSampleBabyDetail(idx);
	}

	@Override
	public boolean deleteSampleBaby(Map<String, Object> paramMap) {
		int queryResult = 0;

		queryResult = adminBabyMapper.deleteSampleBaby(paramMap);

		return (queryResult > 0) ? true : false;
	}

	@Override
	public List<AdminSampleBabyDTO> getSampleBabyList(Map<String, Object> paramMap) {
		List<AdminSampleBabyDTO> sampleBabyList = Collections.emptyList();

		int SampleBabyTotalCount = adminBabyMapper.selectSampleBabyTotalCount(paramMap);

		if (SampleBabyTotalCount > 0) {
			sampleBabyList = adminBabyMapper.selectSampleBabyList(paramMap);
		}

		return sampleBabyList;
	}
	
	//=================== TV CF관리 =========================

	//TVCF 조회
	@Override
	public DataTableDTO getBabyTvcfList(Map<String, Object> commandMap) {
		
		List<AdminCfDTO> babyTvcfList = Collections.emptyList();
		DataTableDTO dataTableDto = new DataTableDTO();
		
		int babyTvcfTotalCount = adminBabyMapper.selectBabyTvcfTotalCount(commandMap);

		if (babyTvcfTotalCount > 0) {
			int start = Integer.parseInt(commandMap.get("start").toString());
			int length = Integer.parseInt(commandMap.get("length").toString());
			
			commandMap.put("start", start);
			commandMap.put("length", length);
			babyTvcfList = adminBabyMapper.selectBabyTvcfList(commandMap);
		}
		
		dataTableDto.setData(babyTvcfList);
		dataTableDto.setRecordsTotal(babyTvcfTotalCount);
		dataTableDto.setRecordsFiltered(babyTvcfTotalCount);
		dataTableDto.setDraw(Integer.parseInt(commandMap.get("draw").toString()));

		return dataTableDto;
	}

	@Override
	public boolean saveBabyTvcf(AdminCfDTO adminCfDTO) throws Exception{
		int queryResult = 0;
		
		if("I".equals(adminCfDTO.getAction())) {
			if(adminCfDTO.getCIdx() == null) {
				System.out.println("이미지 등록로직 시작");
				
				String uuid = UUID.randomUUID().toString();
				String originalName = adminCfDTO.getFileName().getOriginalFilename();
				
				if(!"".equals(originalName)) {
					String file = originalName.substring(originalName.lastIndexOf("\\") + 1);						
					String savefileName = uuid + "_" + file;
					//저장 - 실제경로
					//Path savePath = Paths.get(uploadPath + "/upload/vegemilBaby/tvcf/" + savefileName);
					//저장 - Test로컬경로
					Path savePath = Paths.get("D:/upload/admin/vegemilbaby/" + savefileName);		
					adminCfDTO.getFileName().transferTo(savePath);
					adminCfDTO.setCImg(savefileName);
					adminCfDTO.setCImgOriginal(originalName);
				}					
			}
			queryResult = adminBabyMapper.insertBabyTvcf(adminCfDTO);

		}
		else if ("U".equals(adminCfDTO.getAction())) {
			System.out.println("=======수정 시작========");
			
			if(adminCfDTO.getFileName() != null) {
				System.out.println("이미지 등록로직 시작");
				
				String uuid = UUID.randomUUID().toString();
				String originalName = adminCfDTO.getFileName().getOriginalFilename();
				
				if(!"".equals(originalName)) {
					String file = originalName.substring(originalName.lastIndexOf("\\") + 1);						
					String savefileName = uuid + "_" + file;
					//저장 - 실제경로
					//Path savePath = Paths.get(uploadPath + "/upload/vegemilBaby/tvcf/" + savefileName);
					//저장 - Test로컬경로
					Path savePath = Paths.get("D:/upload/admin/vegemilbaby/" + savefileName);		
					adminCfDTO.getFileName().transferTo(savePath);
					adminCfDTO.setCImg(savefileName);
					adminCfDTO.setCImgOriginal(originalName);							
				}
			}
			
			queryResult = adminBabyMapper.updateBabyTvcf(adminCfDTO);
		}
		else if("DI".equals(adminCfDTO.getAction())) {
			System.out.println("=======썸네일 삭제 시작========");
					
			String storedImg = adminBabyMapper.selectImgFile(adminCfDTO.getCIdx());
			
			//삭제 - 실제경로
			//String storedfilePath = uploadPath+ "/upload/vegemilBaby/tvcf/" + storedImg;
			//삭제 - Test로컬경로						
			String storedfilePath = "D:/upload/admin/vegemilbaby/" + storedImg;
			
			File deleteFile = new File(storedfilePath);
			
			if(deleteFile.exists()) {			            
	            deleteFile.delete(); 			            
	            System.out.println("파일을 삭제하였습니다.");			            
	        } else {
	            System.out.println("파일이 존재하지 않습니다.");
	        }
			
			queryResult = adminBabyMapper.updateBabyTvcfFileInfo(adminCfDTO);
			
			
		} 
		else if("D".equals(adminCfDTO.getAction())) {
			System.out.println("=======삭제 시작========");
			
			System.out.println("=======썸네일 삭제 시작========");			
			String storedImg = adminBabyMapper.selectImgFile(adminCfDTO.getCIdx());
			
			//삭제 - 실제경로
			//String storedfilePath = uploadPath+ "/upload/vegemilBaby/tvcf/" + storedImg;
			//삭제 - Test로컬경로						
			String storedfilePath = "D:/upload/admin/vegemilbaby/" + storedImg;
			
			File deleteFile = new File(storedfilePath);

			if(deleteFile.exists()) {			            
	            deleteFile.delete(); 			            
	            System.out.println("파일을 삭제하였습니다.");			            
	        } else {
	            System.out.println("파일이 존재하지 않습니다.");
	        }
			queryResult = adminBabyMapper.deleteBabyTvcf(adminCfDTO);
		}
		
		return (queryResult == 1) ? true : false;
	}

	
	
}
