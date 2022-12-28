package com.vegemil.service.vegemilBaby;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vegemil.domain.SearchDTO;
import com.vegemil.domain.WebzineEventDTO;
import com.vegemil.domain.vegemilBaby.VegemilBabyBestReviewDTO;
import com.vegemil.domain.vegemilBaby.VegemilBabyCalendarModelDTO;
import com.vegemil.domain.vegemilBaby.VegemilBabyCategoryDTO;
import com.vegemil.domain.vegemilBaby.VegemilBabyEventDTO;
import com.vegemil.domain.vegemilBaby.VegemilBabyMagazineDTO;
import com.vegemil.domain.vegemilBaby.VegemilBabyMagazineDetailDTO;
import com.vegemil.domain.vegemilBaby.VegemilBabyQnADTO;
import com.vegemil.domain.vegemilBaby.VegemilBabyRecipeDTO;
import com.vegemil.domain.vegemilBaby.VegemilBabySampleDTO;
import com.vegemil.mapper.VegemilBabyMapper;
import com.vegemil.paging.PaginationInfo;

@Service
public class VegemilBabyCommunityServiceImpl implements VegemilBabyCommunityService {
	
	@Autowired
	private VegemilBabyMapper vegemilBabyMapper;
	
	//===[Index]===
	//육아정보
	@Override
	public List<VegemilBabyMagazineDTO> selectMagazineIndex() {
		return vegemilBabyMapper.selectMagazineIndex();
	}
	//육아상담 QnA
	@Override
	public List<VegemilBabyQnADTO> selectQnAIndex() {
		return vegemilBabyMapper.selectQnAIndex();
	}
	
	//===[Product]===	
	//육아정보	
	@Override
	public List<VegemilBabyMagazineDTO> selectAllMagazine(String cate) {	  
		  //1. 카테고리별 게시글 조회
		  List<VegemilBabyMagazineDTO> magazineList = Collections.emptyList();	  
		  magazineList= vegemilBabyMapper.selectAllMagazine(cate);	  
		  //2. 카테고리별 게시글 수 조회	  
	  	return magazineList;
	}	
	
	@Override
	public VegemilBabyCategoryDTO selectCategoryCount() {
		VegemilBabyCategoryDTO categoryCount = vegemilBabyMapper.selectCategoryCount();
		return categoryCount;
	}
	
	//육아정보 상세
	@Override
	public VegemilBabyMagazineDetailDTO selectMagazineDetail(Long mbsIdx) {	
		return vegemilBabyMapper.selectMagazineDetail(mbsIdx);
	}
	//영유아식 레시피
	@Override
	public List<VegemilBabyRecipeDTO> selectRecipeList() {
		List<VegemilBabyRecipeDTO> recipeList = vegemilBabyMapper.selectRecipeList();
		return recipeList;
	}
	//영유아식 레시피 상세
	@Override
	public VegemilBabyRecipeDTO selectRecipeDetail(Long idx) {
		VegemilBabyRecipeDTO recipe = vegemilBabyMapper.selectRecipeDetail(idx);
		return recipe;
	}
	
    //===[Event]===
    //진행중 이벤트
	@Override
	public List<VegemilBabyEventDTO> selectEventList() {
		return vegemilBabyMapper.selectEventList();
	}
	//사랑의 온도계 카운트
	@Override
	public int selectTemperature() {
		return vegemilBabyMapper.selectTemperature();
	}
	

	
	//샘플신청 등록
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

    //육아정보 -임신출산
	@Override
	public List<VegemilBabyMagazineDTO> findPbMagazine(SearchDTO params) {
		
		List<VegemilBabyMagazineDTO> pbMagazineList = Collections.emptyList();
       
		int count = vegemilBabyMapper.pbMagazineCount();
		
      
		PaginationInfo paginationInfo = new PaginationInfo(params);
        paginationInfo.setTotalRecordCount(count);
        
        params.setPaginationInfo(paginationInfo);
        
        if(count > 0) {
        	pbMagazineList = vegemilBabyMapper.selectPbMagazine(params);
		}
		
		return pbMagazineList;
	}
    //육아정보 - 성장/건강
	@Override
	public List<VegemilBabyMagazineDTO> findGhMagazine(SearchDTO params) {
		
		List<VegemilBabyMagazineDTO> ghMagazineList = Collections.emptyList();
	       
		int count = vegemilBabyMapper.ghMagazineCount();
		      
		PaginationInfo paginationInfo = new PaginationInfo(params);
        paginationInfo.setTotalRecordCount(count);
        
        params.setPaginationInfo(paginationInfo);
        
        if(count > 0) {
        	ghMagazineList = vegemilBabyMapper.selectGhMagazine(params);
		}
		
		return ghMagazineList;		
	}
	
	@Override
	public List<VegemilBabyMagazineDTO> findPeMagazine(SearchDTO params) {
		List<VegemilBabyMagazineDTO> peMagazineList = Collections.emptyList();
	       
		int count = vegemilBabyMapper.peMagazineCount();
		      
		PaginationInfo paginationInfo = new PaginationInfo(params);
        paginationInfo.setTotalRecordCount(count);
        
        params.setPaginationInfo(paginationInfo);
        
        if(count > 0) {
        	peMagazineList = vegemilBabyMapper.selectPeMagazine(params);
		}
		
		return peMagazineList;	
	}
	
	
	
	
	
	

	
	/*
	 * @Override public List<VegemilBabyMagazineDTO> findAllMagazine() { return
	 * vegemilBabyMapper.selectAllMagazine(); }
	 */

	

	

	
	@Override
	public List<VegemilBabyMagazineDTO> findLhMagazine() {
		return vegemilBabyMapper.selectLhMagazine();
	}

	
	@Override 
	public List<VegemilBabyMagazineDTO> findAllMagazine2(SearchDTO params) {
	  
		System.out.println(params);
	  List<VegemilBabyMagazineDTO> magazineList = Collections.emptyList();
	  
		/*
		 * int count = vegemilBabyMapper.magazineCount(params); //세부 메뉴별 게시글 수 카운트
		 * System.out.println("=====================게시글카운트======================");
		 * System.out.println(count);
		 * 
		 * PaginationInfo paginationInfo = new PaginationInfo(params);
		 * paginationInfo.setTotalRecordCount(count);
		 * 
		 * params.setPaginationInfo(paginationInfo);
		 * 
		 * 
		 * if(count > 0 ) { magazineList= vegemilBabyMapper.selectMagazine(params); }
		 */
	  
	  return null; 
	}
	

	
	@Override
	public List<Integer> magazineCountList(String cate) {
		
		System.out.println("서비스 임플 실행============");
		
		List<Integer>magazineCountList = new ArrayList<>();	

		if(cate.equals("pb")) {		
			System.out.println("서비스 임플 실행============");

			for(int i = 1; i<= 7; i++) {						   
				magazineCountList.add( vegemilBabyMapper.magazineCountPb(cate+"0"+i));				 				
			}
		}else if(cate.equals("gh")) {
			for(int i = 1; i<= 8; i++) {						   
				magazineCountList.add( vegemilBabyMapper.magazineCountGh(cate+"0"+i));				 				
			}
		}else if(cate.equals("pe")) {
			for(int i = 1; i<= 2; i++) {						   
				magazineCountList.add( vegemilBabyMapper.magazineCountPe(cate+"0"+i));				 				
			}
		}else if(cate.equals("lh")) {
			for(int i = 1; i<= 2; i++) {						   
				magazineCountList.add( vegemilBabyMapper.magazineCountLh(cate+"0"+i));				 				
			}
		}
		
		return magazineCountList;
	}

	
	
	
	
	

	

	
	
	
	

	


	@Override
	public List<VegemilBabyMagazineDTO> findMagazine(SearchDTO params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<VegemilBabyBestReviewDTO> bestReviewList() {
		return vegemilBabyMapper.bestReviewList();
	}
	
	




	
	
	
}
