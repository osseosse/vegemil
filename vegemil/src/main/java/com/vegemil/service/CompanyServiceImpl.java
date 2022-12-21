package com.vegemil.service;

import java.util.Collections;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vegemil.paging.PaginationInfo;
import com.vegemil.domain.MediaNewsDTO;
import com.vegemil.domain.SearchDTO;
import com.vegemil.mapper.CompanyMapper;

@Service
public class CompanyServiceImpl implements CompanyService {

	@Autowired
    private CompanyMapper companyMapper;

	@Override
	@Transactional
	public boolean saveMediaNews(MediaNewsDTO params) {
		
		int queryResult = 0;
		
		int applicationCount = companyMapper.mediaNewsCountByIdx(params.getMIdx());

		if (applicationCount != 0) {
			queryResult = companyMapper.mediaNewsSave(params);
		}

		return (queryResult == 1) ? true : false;
	}
	
	@Override
    public MediaNewsDTO findMediaNewsByIdx(Long fIdx) {
        return companyMapper.mediaNewsFindByIdx(fIdx);
    }

	@Override
    @Transactional
    public Long updateMediaNews(MediaNewsDTO params) {
		companyMapper.mediaNewsUpdate(params);
        return params.getMIdx();
    }

	@Override
    public Long deleteMediaNews(Long fIdx) {
		companyMapper.deleteMediaNewsByIdx(fIdx);
        return fIdx;
    }

	@Override
    public List<MediaNewsDTO> findAllMediaNews(SearchDTO params) {
		
		List<MediaNewsDTO> faqList = Collections.emptyList();
		
        int count = companyMapper.mediaNewsCount(params);

        PaginationInfo paginationInfo = new PaginationInfo(params);
		paginationInfo.setTotalRecordCount(count);

		params.setPaginationInfo(paginationInfo);
		
		if(count > 0) {
			faqList = companyMapper.mediaNewsFindAll(params);
		}
        return faqList;
    }
	
	@Override
    public List<MediaNewsDTO> getMediaNewsTop3() {
		
		List<MediaNewsDTO> faqList = Collections.emptyList();
		faqList = companyMapper.selectMediaNewsTop3();
        return faqList;
    }

}
