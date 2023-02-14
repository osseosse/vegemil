package com.vegemil.service.vegemilBaby;

import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.vegemil.domain.AdminCfDTO;
import com.vegemil.domain.SearchDTO;
import com.vegemil.domain.vegemilBaby.VegemilBabyBestReviewDTO;
import com.vegemil.domain.vegemilBaby.VegemilBabyCalendarModelDTO;
import com.vegemil.domain.vegemilBaby.VegemilBabyCategoryDTO;
import com.vegemil.domain.vegemilBaby.VegemilBabyCommunityDTO;
import com.vegemil.domain.vegemilBaby.VegemilBabyEventDTO;
import com.vegemil.domain.vegemilBaby.VegemilBabyQnADTO;
import com.vegemil.domain.vegemilBaby.VegemilBabyRecipeDTO;
import com.vegemil.domain.vegemilBaby.VegemilBabySampleDTO;
import com.vegemil.domain.vegemilBaby.VegemilBabySearchDTO;
import com.vegemil.mapper.VegemilBabyMapper;
import com.vegemil.paging.PaginationInfo;

@Service
public class VegemilBabyCommunityServiceImpl implements VegemilBabyCommunityService {
	
	@Value("${spring.servlet.multipart.location}")
    private String uploadPath;

	@Autowired
	private VegemilBabyMapper vegemilBabyMapper;

	// ======[Index]======
	// 육아정보
	@Override
	public List<VegemilBabyCommunityDTO> selectMagazineIndex() {
		return vegemilBabyMapper.selectMagazineIndex();
	}
	// 육아상담 QnA
	@Override
	public List<VegemilBabyQnADTO> selectQnAIndex() {
		return vegemilBabyMapper.selectQnAIndex();
	}
	
	//======[Brand]======	
	//TvCf 조회
	@Override
	public List<AdminCfDTO> selectTvCf() {	
		return vegemilBabyMapper.selectTvCf();
	}
	

	
	// ======[Community]======
	// 육아정보
	@Override
	public List<VegemilBabyCommunityDTO> selectMagazine(VegemilBabySearchDTO params) {

		String category = params.getCategory();
		String subCategory = params.getSubCategory();

		List<VegemilBabyCommunityDTO> magazineList = Collections.emptyList();

		int count = 0;

		if (category.equals("pb")) {
			count = vegemilBabyMapper.pbMagazineCount(subCategory);
		} else if (category.equals("gh")) {
			count = vegemilBabyMapper.ghMagazineCount(subCategory);
		} else if (category.equals("pe")) {
			count = vegemilBabyMapper.peMagazineCount(subCategory);
		} else if (category.equals("lh")) {
			count = vegemilBabyMapper.lhMagazineCount(subCategory);
		} else {
			count = vegemilBabyMapper.allMagazineCount();
		}

		PaginationInfo paginationInfo = new PaginationInfo(params);
		paginationInfo.setTotalRecordCount(count);

		params.setPaginationInfo(paginationInfo);

		if (count > 0) {
			if (category.equals("pb")) {
				magazineList = vegemilBabyMapper.selectPbMagazine(params);
			} else if (category.equals("gh")) {
				magazineList = vegemilBabyMapper.selectGhMagazine(params);
			} else if (category.equals("pe")) {
				magazineList = vegemilBabyMapper.selectPeMagazine(params);
			} else if (category.equals("lh")) {
				magazineList = vegemilBabyMapper.selectLhMagazine(params);
			} else {
				magazineList = vegemilBabyMapper.selectAllMagazine(params);
			}
		}

		return magazineList;
	}
	
	// 육아정보 - 카테고리별 숫자
	@Override
	public VegemilBabyCategoryDTO selectCategoryCount() {
		VegemilBabyCategoryDTO categoryCount = vegemilBabyMapper.selectCategoryCount();
		return categoryCount;
	}
	// 육아정보 상세
	@Override
	public VegemilBabyCommunityDTO selectMagazineDetail(Long idx) {
		return vegemilBabyMapper.selectMagazineDetail(idx);
	}
	
	//육아상담 Q&A
	@Override
	public List<VegemilBabyCommunityDTO> selectQna(VegemilBabySearchDTO params) {
		
		String category = params.getCategory();
		
		List<VegemilBabyCommunityDTO> qnaList = Collections.emptyList();
		
		int count = vegemilBabyMapper.qnaCount(category);
		
		PaginationInfo paginationInfo = new PaginationInfo(params);
		paginationInfo.setTotalRecordCount(count);
		
		params.setPaginationInfo(paginationInfo);
		
		if (count > 0) {
			qnaList = vegemilBabyMapper.selectQna(params);
		}
		return qnaList;
	}
	
    // 육아상담 Q&A 상세	
	@Override
	public VegemilBabyCommunityDTO selectQnaDetail(Long idx) {
		return vegemilBabyMapper.selectQnaDetail(idx);
	}
	
	// 육아상담 Q&A 추천리스트
	public List<VegemilBabyCommunityDTO> selectQnaList() {
		return	vegemilBabyMapper.selectQnaList();
	}



	// 영유아식 레시피 리스트
	@Override
	public List<VegemilBabyRecipeDTO> selectRecipeList() {
		List<VegemilBabyRecipeDTO> recipeList = vegemilBabyMapper.selectRecipeList();
		return recipeList;
	}
	// 영유아식 레시피 상세
	@Override
	public VegemilBabyRecipeDTO selectRecipeDetail(Long idx) {
		VegemilBabyRecipeDTO recipe = vegemilBabyMapper.selectRecipeDetail(idx);
		return recipe;
	}

	// ======[Event]======
	// 진행중 이벤트
	@Override
	public List<VegemilBabyEventDTO> selectEventList() {
		return vegemilBabyMapper.selectEventList();
	}
	// 사랑의 온도계 카운트
	@Override
	public int selectTemperature() {
		return vegemilBabyMapper.selectTemperature();
	}
	
	//후기이벤트- 이벤트 참여내역 조회
	@Override
	public List<VegemilBabyBestReviewDTO> selectReviewList(String loggedId){
		return vegemilBabyMapper.selectReviewList(loggedId);
	}
  	//후기이벤트 - 이벤트 등록
	@Override
	@Transactional
	public int insertReviewEvent(@ModelAttribute("review")VegemilBabyBestReviewDTO review, HttpServletResponse response) throws Exception {
			
		  response.setContentType("text/html; charset=UTF-8"); PrintWriter out =
		  response.getWriter();
		  
			String uuid = UUID.randomUUID().toString();
			String originalName = review.getFileName().getOriginalFilename();	
					
			if(!"".equals(originalName)) {
				String file = originalName.substring(originalName.lastIndexOf("\\")+1);
				
				String savefileName = uuid + "_" +file.replaceAll("\\s", "");
				
				//저장 - 실제경로 
				Path savePath = Paths.get(uploadPath+ "/upload/vegemilBaby/" + savefileName);
				//저장 - Test로컬경로
				//Path savePath = Paths.get("D:/upload/vegemilBaby/" + savefileName);
												
				//저장
				review.getFileName().transferTo(savePath);				
				review.setSImage(savefileName);
				
				long filesize = Files.size(savePath); 			
				if(filesize > 3145728) {
					out.println("<script>alert('3M이하 이미지를 등록해주세요'); history.back();</script>");
					out.flush();	
					return 0;
				}			
			}
		
		return vegemilBabyMapper.insertReviewEvent(review);
	}
	
	//후기이벤트 - 베스트 후기 조회
	@Override
	public List<VegemilBabyBestReviewDTO> selectBestReviewList() {
		return vegemilBabyMapper.selectBestReviewList();
	}

	// 샘플신청 등록
	@Override
	@Transactional
	public boolean insertSampleForm(VegemilBabySampleDTO sample) {

		int queryResult = 0;
		queryResult = vegemilBabyMapper.insertSampleForm(sample);

		return (queryResult == 1) ? true : false;
	}

	@Override
	@Transactional
	public boolean insertModelForm(VegemilBabyCalendarModelDTO calModel) {

		int queryResult = 0;
		queryResult = vegemilBabyMapper.insertCalendarModel(calModel);

		return (queryResult == 1) ? true : false;
	}

	@Override
	public boolean isSampleForm(VegemilBabySampleDTO params) {

		int sampleCount = 0;
		sampleCount = vegemilBabyMapper.sampleFormCountBySample(params);

		return (sampleCount >= 1) ? true : false;
	}	
	
	//아기모델센발대회 - 이달의모델 조회
	@Override
	public List<VegemilBabyCalendarModelDTO> selectModelList() {
		return vegemilBabyMapper.selectModelList();
	}
	

}
