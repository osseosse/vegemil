package com.vegemil.service.vegemilBaby;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.vegemil.domain.AdminCalendarTitleDTO;
import com.vegemil.domain.AdminCfDTO;
import com.vegemil.domain.vegemilBaby.VegemilBabyBestReviewDTO;
import com.vegemil.domain.vegemilBaby.VegemilBabyCalendarModelDTO;
import com.vegemil.domain.vegemilBaby.VegemilBabyCategoryDTO;
import com.vegemil.domain.vegemilBaby.VegemilBabyCommunityDTO;
import com.vegemil.domain.vegemilBaby.VegemilBabyEventDTO;
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
	
	//======[Brand]======	
	//TvCf 조회
	public List<AdminCfDTO> selectTvCf();
	
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
	//사랑의온도계
  	public int selectTemperature();

  	//후기이벤트- 이벤트 참여내역 조회
  	public List<VegemilBabyBestReviewDTO> selectReviewList(String loggedId);
  	//후기이벤트 - 이벤트 등록
  	public int insertReviewEvent (VegemilBabyBestReviewDTO review, HttpServletResponse response) throws Exception;
  	//후기이벤트 - 베스트후기 조회
	public List<VegemilBabyBestReviewDTO> selectBestReviewList();

	//아기모델 - 이달의모델 조회
	public List<VegemilBabyCalendarModelDTO> selectModelList();
	//아기모델 - 신청하기
	public boolean insertModelForm(VegemilBabyCalendarModelDTO calModel);	
	//아기모델 - 타이틀 조회
    public List<AdminCalendarTitleDTO> selectCalenderModelTitle();

	
	//샘플 신청 등록
    public boolean insertSampleForm(VegemilBabySampleDTO sample);    
    public boolean isSampleForm(VegemilBabySampleDTO params);
	
	
       
    
    
}