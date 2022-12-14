package com.vegemil.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vegemil.domain.BeansoupDTO;
import com.vegemil.domain.BeansoupEventDTO;
import com.vegemil.domain.BeansoupNewsDTO;
import com.vegemil.domain.BeansoupVideoDTO;
import com.vegemil.mapper.BeansoupMapper;
import com.vegemil.paging.PaginationInfo;

@Service
public class BeansoupServiceImpl implements BeansoupService{
	
	@Autowired
	private BeansoupMapper beansoupMapper;

	//=======================BeansoupDTO=========================
	@Override
	public List<BeansoupDTO> selectBeansoupList() {
		List<BeansoupDTO> beansoupList = this.mappingfilter(beansoupMapper.selectBeansoupList());
		return beansoupList;
	}

	@Override
	public int selectBeansoupListCount(BeansoupDTO beansoupDto) {
		return beansoupMapper.selectBeansoupListCount(beansoupDto);
	}

	@Override
	public List<BeansoupDTO> selectBeanListWithKeyword(String searchKeyword) {
		List<BeansoupDTO> beansoupList = this.mappingfilter(beansoupMapper.selectBeanListWithKeyword(searchKeyword));
		return beansoupList;
	}

	@Override
	public List<BeansoupDTO> selectBeansoupProposalList(String mCate) {
		List<BeansoupDTO> beansoupList = this.mappingfilter(beansoupMapper.selectBeansoupProposalList(mCate));
		return beansoupList;
	}

	@Override
	public BeansoupDTO selectBeansoupDetail(String fileNo) {
		return beansoupMapper.selectBeansoupDetail(fileNo);
	}
	
	private List<BeansoupDTO> mappingfilter(List<BeansoupDTO> beansoupList){
		
		// 필터값 매핑 익숙한 일상요리 = recType01/ 특별한 날 메인 요리 = recType02/ 부어서 세계속으로 = recType03
		String filter = "";
		
		for(BeansoupDTO dto:beansoupList) {
			if(dto.getLCate().equals("익숙한 일상요리")) {
				filter = "recType01";
			} else if(dto.getLCate().equals("특별한 날 메인요리")) {
				filter = "recType02";
			} else if(dto.getLCate().equals("부어서 세계속으로")) {
				filter = "recType03";
			}
			dto.setFilter(filter);
		}
		return beansoupList;
	}
	
	@Override
	public Map<String, Integer> mappingCount(List<BeansoupDTO> beansoupList){
		
		Map<String,Integer> countMap = new HashMap<>();
		countMap.put("allCount", 0); countMap.put("recType01", 0); countMap.put("recType02", 0); countMap.put("recType03", 0);
		
		for(BeansoupDTO item : beansoupList) {
			// 필터값 매핑 익숙한 일상요리 = recType01/ 특별한 날 메인 요리 = recType02/ 부어서 세계속으로 = recType03
			if(item.getFilter().equals("recType01")) {
				countMap.put("recType01", countMap.get("recType01") + 1);
			}
			if(item.getFilter().equals("recType02")) {
				countMap.put("recType02", countMap.get("recType02") + 1);
			}
			if(item.getFilter().equals("recType03")) {
				countMap.put("recType03", countMap.get("recType03") + 1);
			}
			countMap.put("allCount", countMap.get("allCount") + 1);
		}
		return countMap;
	}
	
	// =======================BeansoupEventDTO=========================
	@Override
	public List<BeansoupEventDTO> selectBeansoupEventList(BeansoupEventDTO params) {

		List<BeansoupEventDTO> beansoupEventList = Collections.emptyList();
		int evnetTotalCount = this.selectBeansoupEventCount(params);
		PaginationInfo paginationInfo = new PaginationInfo(params);
		paginationInfo.setTotalRecordCount(evnetTotalCount);

		params.setPaginationInfo(paginationInfo);

		if (evnetTotalCount > 0) {
			beansoupEventList = beansoupMapper.selectBeansoupEventList(params);
		}

		return beansoupEventList;
	}

	@Override
	public int selectBeansoupEventCount(BeansoupEventDTO params) {
		
		return beansoupMapper.selectBeansoupEventCount(params);
	}
	
	//=======================BeansoupNewsDTO=========================
	@Override
	public List<BeansoupNewsDTO> selectBeansoupNewsList() {
		
		List<BeansoupNewsDTO> beansoupNewsList = beansoupMapper.selectBeansoupNewsList();
		for(BeansoupNewsDTO news : beansoupNewsList) {
			if(news.getMIng() == 1) {
				news.setIngStr("진행중");
			} else {
				news.setIngStr("진행마감");
			}
			news.setThumPath("https://image.edaymall.com/images/vegemil/beansoup/news/"+news.getMThum());
		}
		
		return beansoupNewsList;
	}

	@Override
	public int selectBeansoupNewsListCount(BeansoupNewsDTO beansoupDto) {
		return beansoupMapper.selectBeansoupNewsListCount(beansoupDto);
	}
	
	//=======================BeansoupVideoDTO=========================
	@Override
	public List<BeansoupVideoDTO> selectBeansoupVideoList(BeansoupVideoDTO params) {
		
		List<BeansoupVideoDTO> beansoupVideoList = Collections.emptyList();
		int evnetTotalCount = this.selectBeansoupVideoCount(params);
		PaginationInfo paginationInfo = new PaginationInfo(params);
		paginationInfo.setTotalRecordCount(evnetTotalCount);
		
		params.setPaginationInfo(paginationInfo);

		if (evnetTotalCount > 0) {
			beansoupVideoList = beansoupMapper.selectBeansoupVideoList(params);
		}
		return beansoupVideoList;
	}

	@Override
	public int selectBeansoupVideoCount(BeansoupVideoDTO param) {
		return beansoupMapper.selectBeansoupVideoCount(param);
	}
	
}
