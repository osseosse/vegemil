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

    //영유아식 레시피
	public List<VegemilBabyRecipeDTO> selectRecipeList();	
	//영유아식 레시피 상세
	public VegemilBabyRecipeDTO selectRecipeDetail(Long idx);

	
	//육아정보 - 임신출산
	public int pbMagazineCount();	
	public List<VegemilBabyMagazineDTO> selectPbMagazine(SearchDTO params);

    //육아정보 - 성장/건강
	public int ghMagazineCount();
	public List<VegemilBabyMagazineDTO> selectGhMagazine(SearchDTO params);

    //육아정보 - 놀이/교육
	public int peMagazineCount();
	public List<VegemilBabyMagazineDTO> selectPeMagazine(SearchDTO params);


	//===[Event]===
    //진행중 이벤트
	public List<VegemilBabyEventDTO> selectEventList();
	
	//샘플신청
	public int insertSampleForm(VegemilBabySampleDTO sample);
	
	public int insertCalendarModel(VegemilBabyCalendarModelDTO sample);
	
	public int sampleFormCountBySample(VegemilBabySampleDTO sample);
	
	//public List<VegemilBabyMagazineDTO> selectAllMagazine();
	public List<VegemilBabyMagazineDTO> selectLhMagazine();

	public List<VegemilBabyMagazineDTO> selectMagazine(VegemilBabySearchDTO params);
	public int magazineCount(VegemilBabySearchDTO params); 
	
	public int magazineCountPb(String cate); 
	public int magazineCountGh(String cate); 
	public int magazineCountPe(String cate); 
	public int magazineCountLh(String cate); 
	


	
	
	
	

	public List<VegemilBabyBestReviewDTO> bestReviewList();





	
	

	

}
