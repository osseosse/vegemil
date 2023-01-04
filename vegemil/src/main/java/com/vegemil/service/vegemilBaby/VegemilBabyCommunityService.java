package com.vegemil.service.vegemilBaby;

import java.util.List;

import com.vegemil.domain.vegemilBaby.VegemilBabyBestReviewDTO;
import com.vegemil.domain.vegemilBaby.VegemilBabyCalendarModelDTO;
import com.vegemil.domain.vegemilBaby.VegemilBabyCategoryDTO;
import com.vegemil.domain.vegemilBaby.VegemilBabyEventDTO;
import com.vegemil.domain.vegemilBaby.VegemilBabyCommunityDTO;
import com.vegemil.domain.vegemilBaby.VegemilBabyQnADTO;
import com.vegemil.domain.vegemilBaby.VegemilBabyRecipeDTO;
import com.vegemil.domain.vegemilBaby.VegemilBabySampleDTO;
import com.vegemil.domain.vegemilBaby.VegemilBabySearchDTO;

public interface VegemilBabyCommunityService {
	
	//======[Index]======	
	//육아정보
	public List<VegemilBabyCommunityDTO> selectMagazineIndex();
	//육아상담 QnA
	public List<VegemilBabyQnADTO> selectQnAIndex();
	
	
	//======[Community]======	
	// 육아정보 
	public List<VegemilBabyCommunityDTO> selectMagazine(VegemilBabySearchDTO params);  
	// 육아정보 - 카테고리별 숫자
	public VegemilBabyCategoryDTO selectCategoryCount();
    //육아정보 상세
    public VegemilBabyCommunityDTO selectMagazineDetail(Long idx);
    
    // 육아상담 Q&A
   	public List<VegemilBabyCommunityDTO> selectQna(VegemilBabySearchDTO params);  
    // 육아상담 Q&A 상세
    public VegemilBabyCommunityDTO selectQnaDetail(Long idx);
    // 육아상담 Q&A 추천리스트
    public List<VegemilBabyCommunityDTO> selectQnaList();
        

    //영유아식 레시피 
    public List<VegemilBabyRecipeDTO> selectRecipeList();
	//영유아식 레시피 상세
    public VegemilBabyRecipeDTO selectRecipeDetail(Long idx);
    
   
	

	
    
    //======[Event]======
    //진행중 이벤트
  	public List<VegemilBabyEventDTO> selectEventList();
  	
  	
	//샘플 신청 등록
    public boolean insertSampleForm(VegemilBabySampleDTO sample);
    
    public boolean insertModelForm(VegemilBabyCalendarModelDTO calModel);
    
    public boolean isSampleForm(VegemilBabySampleDTO params);
    
	
  

	public List<VegemilBabyBestReviewDTO> bestReviewList();

	public int selectTemperature();

	
	
	




   
    
    
    
}