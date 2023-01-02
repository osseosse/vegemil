package com.vegemil.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

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

@Mapper
public interface VegemilBabyMapper {
	
	//======[Index]======
	//육아정보
	public List<VegemilBabyMagazineDTO> selectMagazineIndex();
	//육아상담 QnA
	public List<VegemilBabyQnADTO> selectQnAIndex();

	//======[Community]======	
	//육아정보
	// 육아정보 - 카테고리별 숫자
	public VegemilBabyCategoryDTO selectCategoryCount(); 
	
	//육아정보 - 전체
	public int allMagazineCount();
	public List<VegemilBabyMagazineDTO> selectAllMagazine(VegemilBabySearchDTO params);	
	
	//육아정보 - 임신출산
	public int pbMagazineCount(String subCategory);	
	public List<VegemilBabyMagazineDTO> selectPbMagazine(VegemilBabySearchDTO params);

    //육아정보 - 성장/건강
	public int ghMagazineCount(String subCategory);
	public List<VegemilBabyMagazineDTO> selectGhMagazine(VegemilBabySearchDTO params);

    //육아정보 - 놀이/교육
	public int peMagazineCount(String subCategory);
	public List<VegemilBabyMagazineDTO> selectPeMagazine(VegemilBabySearchDTO params);
	
	//육아정보 -리빙/헬스/트랜드
	public int lhMagazineCount(String subCategory);
	public List<VegemilBabyMagazineDTO> selectLhMagazine(VegemilBabySearchDTO params);

		

	//육아정보 상세
	public VegemilBabyMagazineDetailDTO selectMagazineDetail(Long mbsIdx);
	
	//영유아식 레시피
	public List<VegemilBabyRecipeDTO> selectRecipeList();	
	//영유아식 레시피 상세
	public VegemilBabyRecipeDTO selectRecipeDetail(Long idx);

	
	
	
	
	//======[Event]======
    //진행중 이벤트
	public List<VegemilBabyEventDTO> selectEventList();
	
	//사랑의 온도계 카운트
	public int selectTemperature();

	//샘플신청
	public int insertSampleForm(VegemilBabySampleDTO sample);
	
	public int insertCalendarModel(VegemilBabyCalendarModelDTO sample);
	
	public int sampleFormCountBySample(VegemilBabySampleDTO sample);
	

	public List<VegemilBabyBestReviewDTO> bestReviewList();



	
	//======삭제예정======

	public List<VegemilBabyMagazineDTO> selectMagazine(VegemilBabySearchDTO params);
	public int magazineCount(VegemilBabySearchDTO params); 
	
	public int magazineCountPb(String cate); 
	public int magazineCountGh(String cate); 
	public int magazineCountPe(String cate); 
	public int magazineCountLh(String cate); 
	
	//======삭제예정======

	
	



	
	

	

}
