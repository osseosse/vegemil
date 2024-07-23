package com.vegemil.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.vegemil.domain.AdminCalendarTitleDTO;
import com.vegemil.domain.AdminCfDTO;
import com.vegemil.domain.SearchDTO;
import com.vegemil.domain.vegemilBaby.VBSampleRequestMonthDTO;
import com.vegemil.domain.vegemilBaby.VegemilBabyBestReviewDTO;
import com.vegemil.domain.vegemilBaby.VegemilBabyCalendarModelDTO;
import com.vegemil.domain.vegemilBaby.VegemilBabyCategoryDTO;
import com.vegemil.domain.vegemilBaby.VegemilBabyCommunityDTO;
import com.vegemil.domain.vegemilBaby.VegemilBabyEventDTO;
import com.vegemil.domain.vegemilBaby.VegemilBabyQnADTO;
import com.vegemil.domain.vegemilBaby.VegemilBabyRecipeDTO;
import com.vegemil.domain.vegemilBaby.VegemilBabySampleDTO;
import com.vegemil.domain.vegemilBaby.VegemilBabySearchDTO;
import com.vegemil.paging.BoardListSearchDTO;
import com.vegemil.paging.BoardResponseVO;

@Mapper
public interface VegemilBabyMapper {
	
	//======[Index]======
	//육아정보
	public List<VegemilBabyCommunityDTO> selectMagazineIndex();
	//육아상담 QnA
	public List<VegemilBabyQnADTO> selectQnAIndex();
	
    // 메인 > 베스트리튜 
	public List<VegemilBabyBestReviewDTO> selectBestReviewIndex();
	// 메인 > 육아정보
	public List<VegemilBabyCommunityDTO> selectBabyInfoIndex();

	
	//======[Brand]======	
	//TvCf 조회	
	public List<AdminCfDTO> selectTvCf();
	
	
	//======[Community]======	
	// 육아정보 - 카테고리별 숫자
	public VegemilBabyCategoryDTO selectCategoryCount(); 	
	//육아정보 - 전체
	public int allMagazineCount();
	public List<VegemilBabyCommunityDTO> selectAllMagazine(SearchDTO params);		
	//육아정보 - 임신출산
	public int pbMagazineCount(String subCategory);	
	public List<VegemilBabyCommunityDTO> selectPbMagazine(SearchDTO params);
    //육아정보 - 성장/건강
	public int ghMagazineCount(String subCategory);
	public List<VegemilBabyCommunityDTO> selectGhMagazine(SearchDTO params);
    //육아정보 - 놀이/교육
	public int peMagazineCount(String subCategory);
	public List<VegemilBabyCommunityDTO> selectPeMagazine(SearchDTO params);	
	//육아정보 -리빙/헬스/트랜드
	public int lhMagazineCount(String subCategory);
	public List<VegemilBabyCommunityDTO> selectLhMagazine(SearchDTO params);
	//육아정보 상세
	public VegemilBabyCommunityDTO selectMagazineDetail(Long idx);
	
	//육아상담 Q&A - 카테고리별 게시글수
	public int qnaCount(String category);	
	//육아상담 Q&A 상세
	public VegemilBabyCommunityDTO selectQnaDetail(Long idx);	
	//육아상담 Q&A - 카테고리별 조회
	public List<VegemilBabyCommunityDTO> selectQna(VegemilBabySearchDTO params);	
    // 육아상담 Q&A 추천리스트
	public List<VegemilBabyCommunityDTO> selectQnaList();
		
	//영유아식 레시피
	public List<VegemilBabyRecipeDTO> selectRecipeList();	
	//영유아식 레시피 상세
	public VegemilBabyRecipeDTO selectRecipeDetail(Long idx);

		
	//======[Event]======
    //진행중 이벤트
	public List<VegemilBabyEventDTO> selectEventList();	
	//사랑의 온도계 카운트
	public int selectTemperature();
	
	//후기이벤트 - 이벤트 참여내역 조회
	public List<VegemilBabyBestReviewDTO> selectReviewList(String loggedId);
	//후기이벤트 - 이벤트 등록
	public int insertReviewEvent(VegemilBabyBestReviewDTO review);
	//후기이벤트 - 베스트 후기 조회
	public List<VegemilBabyBestReviewDTO> selectBestReviewList();
	
	//아기모델센발대회 - 이달의모델 조회
	public List<BoardResponseVO> selectModelList(BoardListSearchDTO boardListSearchDTO);
//	public List<VegemilBabyCalendarModelDTO> selectModelList(BoardListSearchDTO boardListSearchDTO);
	public List<VegemilBabyCalendarModelDTO> selectModelList2();
	//아기모델센발대회 - 타이틀 조회
	public List<AdminCalendarTitleDTO> selectCalenderModelTitle();
	public AdminCalendarTitleDTO selectCalenderModelTitlebyRownum(String rownum);

	
	//샘플신청
	public int insertSampleForm(VegemilBabySampleDTO sample);
	
	public int insertCalendarModel(VegemilBabyCalendarModelDTO sample);
	public int selectCountRecentPosting(VegemilBabyCalendarModelDTO sample);
	
	public int sampleFormCountBySample(VegemilBabySampleDTO sample);
	
	//월별 샘플 신청 현황
	public VBSampleRequestMonthDTO selectVegemilBabySampleRequsetByMonth(String sItem);
	
	
	
	public List<VegemilBabyBestReviewDTO> bestReviewList();
	
	
    
 

	
	

	
	



	
	

	

}
