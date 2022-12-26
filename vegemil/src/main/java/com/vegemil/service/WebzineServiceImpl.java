package com.vegemil.service;

import java.util.Collections;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vegemil.paging.PaginationInfo;
import com.vegemil.domain.WebzineDTO;
import com.vegemil.domain.WebzineEventDTO;
import com.vegemil.domain.SearchDTO;
import com.vegemil.domain.SubscribeDTO;
import com.vegemil.mapper.CustomerMapper;
import com.vegemil.mapper.WebzineMapper;

@Service
public class WebzineServiceImpl implements WebzineService {

	@Autowired
    private WebzineMapper webzineMapper;

	@Override
	@Transactional
	public boolean saveWebzine(WebzineDTO params) {
		
		int queryResult = 0;
		
		int webzineCount = webzineMapper.webzineCountByIdx(params.getIndexNo());

		if (webzineCount != 0) {
			queryResult = webzineMapper.webzineSave(params);
		}

		return (queryResult == 1) ? true : false;
	}
	
	@Override
	public boolean isWebzineEvent(WebzineEventDTO params) {
		
		int webzineCount = 0;
		webzineCount = webzineMapper.webzineEventCountByQrt(params);

		return (webzineCount >= 1) ? true : false;
	}
	
	@Override
	@Transactional
	public boolean saveWebzineEvent(WebzineEventDTO params) {
		
		int queryResult = webzineMapper.webzineEventSave(params);

		return (queryResult == 1) ? true : false;
	}
	
	@Override
	@Transactional
	public boolean saveSubscribe(SubscribeDTO params) {
		
		int queryResult = 0;
		
		int subscribeCount = webzineMapper.isSubscibeByEmail(params.getSEmail());

		if (subscribeCount == 0) {
			queryResult = webzineMapper.subscribeSave(params);
		}

		return (queryResult == 1) ? true : false;
	}
	
	@Override
    public WebzineDTO findWebzineById(Long fIdx) {
        return webzineMapper.webzineFindByIdx(fIdx);
    }

	@Override
    @Transactional
    public Long updateWebzine(WebzineDTO params) {
		webzineMapper.webzineUpdate(params);
        return params.getIndexNo();
    }

	@Override
    public Long deleteWebzine(Long fIdx) {
		webzineMapper.deleteWebzineByIdx(fIdx);
        return fIdx;
    }

	@Override
    public List<WebzineDTO> findAllWebzine(SearchDTO params) {
		
		List<WebzineDTO> webzineList = Collections.emptyList();
		
        int count = webzineMapper.webzineCount();

        /*
        PaginationInfo paginationInfo = new PaginationInfo(params);
		paginationInfo.setTotalRecordCount(count);

		params.setPaginationInfo(paginationInfo);
		*/
        
		if(count > 0) {
			webzineList = webzineMapper.webzineFindAll(params);
		}
        return webzineList;
    }
	
	@Override
    public List<SubscribeDTO> findAllWebzine() {
		
		List<SubscribeDTO> subscibeList = Collections.emptyList();
		
        int count = webzineMapper.webzineCount();

		if(count > 0) {
			subscibeList = webzineMapper.selectSubscibeAll();
		}
        return subscibeList;
    }
	
	@Override
    public List<WebzineDTO> getRecommandWebzine() {
		
		List<WebzineDTO> webzineList = Collections.emptyList();
		
        int count = webzineMapper.webzineCount();

		if(count > 0) {
			webzineList = webzineMapper.selectWebzineRandom();
		}
        return webzineList;
    }
	
	@Override
	public List<WebzineDTO> getWebzineQrtYear(String qrtYear) {
		
		List<WebzineDTO> webzineList = Collections.emptyList();
		webzineList = webzineMapper.selectWebzineByQrtYear(qrtYear);
		
        return webzineList;
    }
	
	@Override
    public WebzineDTO getWebzine(WebzineDTO param) {
        return webzineMapper.selectWebzine(param);
    }
	
	@Override
    public List<WebzineDTO> getWebzineYear() {
		List<WebzineDTO> webzineList = Collections.emptyList();
		webzineList = webzineMapper.selectWebzineYear();
		
        return webzineList;
    }
	
	@Override
    public List<WebzineDTO> getWebzineQrt() {
		List<WebzineDTO> webzineList = Collections.emptyList();
		webzineList = webzineMapper.selectWebzineQrt();
		
        return webzineList;
    }
	
	@Override
    public List<WebzineDTO> getWebzineLink() {
		List<WebzineDTO> webzineList = Collections.emptyList();
		webzineList = webzineMapper.selectWebzineLink();
        return webzineList;
    }
	
	@Override
    public List<WebzineDTO> getWebzineQ(String qrtYear) {
		List<WebzineDTO> webzineList = Collections.emptyList();
		webzineList = webzineMapper.selectWebzineQ(qrtYear);
		
        return webzineList;
    }
	
	@Override
    public List<WebzineDTO> getWebzine02() {
		List<WebzineDTO> webzineList = Collections.emptyList();
		webzineList = webzineMapper.selectWebzine02();
        return webzineList;
    }
	@Override
    public List<WebzineDTO> getWebzine03() {
		List<WebzineDTO> webzineList = Collections.emptyList();
		webzineList = webzineMapper.selectWebzine03();
        return webzineList;
    }
	@Override
    public List<WebzineDTO> getWebzine04() {
		List<WebzineDTO> webzineList = Collections.emptyList();
		webzineList = webzineMapper.selectWebzine04();
        return webzineList;
    }
	@Override
    public List<WebzineDTO> getWebzine05() {
		List<WebzineDTO> webzineList = Collections.emptyList();
		webzineList = webzineMapper.selectWebzine05();
        return webzineList;
    }
	@Override
    public List<WebzineDTO> getWebzine06() {
		List<WebzineDTO> webzineList = Collections.emptyList();
		webzineList = webzineMapper.selectWebzine06();
        return webzineList;
    }
	

}
