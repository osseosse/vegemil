package com.vegemil.service.vegemilBaby;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vegemil.domain.SearchDTO;
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
import com.vegemil.mapper.VegemilBabyMapper;
import com.vegemil.paging.PaginationInfo;

@Service
public class VegemilBabyCommunityServiceImpl implements VegemilBabyCommunityService {

	@Autowired
	private VegemilBabyMapper vegemilBabyMapper;

	// ======[Index]======
	// 육아정보
	@Override
	public List<VegemilBabyMagazineDTO> selectMagazineIndex() {
		return vegemilBabyMapper.selectMagazineIndex();
	}
	// 육아상담 QnA
	@Override
	public List<VegemilBabyQnADTO> selectQnAIndex() {
		return vegemilBabyMapper.selectQnAIndex();
	}

	
	// ======[Community]======
	// 육아정보
	@Override
	public List<VegemilBabyMagazineDTO> NEWselectMagazine(VegemilBabySearchDTO params) {

		String category = params.getCategory();
		String subCategory = params.getSubCategory();

		List<VegemilBabyMagazineDTO> magazineList = Collections.emptyList();

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
	public VegemilBabyMagazineDetailDTO selectMagazineDetail(Long mbsIdx) {
		return vegemilBabyMapper.selectMagazineDetail(mbsIdx);
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

	

	@Override
	public List<Integer> magazineCountList(String cate) {

		System.out.println("서비스 임플 실행============");

		List<Integer> magazineCountList = new ArrayList<>();

		if (cate.equals("pb")) {
			System.out.println("서비스 임플 실행============");

			for (int i = 1; i <= 7; i++) {
				magazineCountList.add(vegemilBabyMapper.magazineCountPb(cate + "0" + i));
			}
		} else if (cate.equals("gh")) {
			for (int i = 1; i <= 8; i++) {
				magazineCountList.add(vegemilBabyMapper.magazineCountGh(cate + "0" + i));
			}
		} else if (cate.equals("pe")) {
			for (int i = 1; i <= 2; i++) {
				magazineCountList.add(vegemilBabyMapper.magazineCountPe(cate + "0" + i));
			}
		} else if (cate.equals("lh")) {
			for (int i = 1; i <= 2; i++) {
				magazineCountList.add(vegemilBabyMapper.magazineCountLh(cate + "0" + i));
			}
		}

		return magazineCountList;
	}

	

	@Override
	public List<VegemilBabyBestReviewDTO> bestReviewList() {
		return vegemilBabyMapper.bestReviewList();
	}

	


}
