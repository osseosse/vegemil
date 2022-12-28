package com.vegemil.service.vegemilBaby;

import java.util.List;

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
import com.vegemil.domain.vegemilBaby.VegemilBabySearchDTO;

public interface VegemilBabyCommunityService {
	
	//===[Index]===
	//육아정보
	public List<VegemilBabyMagazineDTO> selectMagazineIndex();

	//육아상담 QnA
	public List<VegemilBabyQnADTO> selectQnAIndex();
	
	//===[Product]===
	//육아정보	
    public List<VegemilBabyMagazineDTO> selectAllMagazine(String cate);
    public VegemilBabyCategoryDTO selectCategoryCount();

    //육아정보 상세
    public VegemilBabyMagazineDetailDTO selectMagazineDetail(Long mbsIdx);

    //영유아식 레시피 리스트
    public List<VegemilBabyRecipeDTO> selectRecipeList();
	//영유아식 레시피 상세
    public VegemilBabyRecipeDTO selectRecipeDetail(Long idx);
	
    
    //===[Event]===
    //진행중 이벤트
  	public List<VegemilBabyEventDTO> selectEventList();
  	
  	
	//샘플 신청 등록
    public boolean insertSampleForm(VegemilBabySampleDTO sample);
    
    public boolean insertModelForm(VegemilBabyCalendarModelDTO calModel);
    
    public boolean isSampleForm(VegemilBabySampleDTO params);
    
	public List<VegemilBabyMagazineDTO> findMagazine(SearchDTO params);

    //육아정보 -임신출산
    public List<VegemilBabyMagazineDTO> findPbMagazine(SearchDTO params);
    //육아정보 - 성장/건강
    public List<VegemilBabyMagazineDTO> findGhMagazine(SearchDTO params);
    //육아정보 - 놀이/교육
    public List<VegemilBabyMagazineDTO> findPeMagazine(SearchDTO params);

	/* public List<VegemilBabyMagazineDTO> findAllMagazine(); */
    public List<VegemilBabyMagazineDTO> findLhMagazine();
	
    public List<VegemilBabyMagazineDTO> findAllMagazine2(SearchDTO params);

    public List<Integer> magazineCountList(String cate);
    
  

	public List<VegemilBabyBestReviewDTO> bestReviewList();
	
	




   
    
    
    
}