package com.vegemil.service;

import java.util.Collections;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vegemil.paging.PaginationInfo;
import com.vegemil.domain.FaqDTO;
import com.vegemil.domain.FaqFeedbackDTO;
import com.vegemil.domain.SearchDTO;
import com.vegemil.mapper.CustomerMapper;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
    private CustomerMapper customerMapper;

    /**
     * 게시글 저장
     * @param params - 게시글 정보
     * @return Generated PK
     */
	
	@Override
    @Transactional
    public Long saveFaq(FaqDTO params) {
    	customerMapper.faqSave(params);
        return params.getFIdx();
    }

	@Override
	@Transactional
	public boolean saveFaqFeedback(FaqFeedbackDTO params) {
		
		int queryResult = 0;
		
		int applicationCount = customerMapper.faqCountByIdx(params.getFaqIdx());

		if (applicationCount != 0) {
			queryResult = customerMapper.faqFeedbackSave(params);
		}

		return (queryResult == 1) ? true : false;
	}
	
    /**
     * 게시글 상세정보 조회
     * @param id - PK
     * @return 게시글 상세정보
     */
	@Override
    public FaqDTO findFaqById(Long fIdx) {
        return customerMapper.faqFindByIdx(fIdx);
    }

    /**
     * 게시글 수정
     * @param params - 게시글 정보
     * @return PK
     */
	@Override
    @Transactional
    public Long updateFaq(FaqDTO params) {
    	customerMapper.faqUpdate(params);
        return params.getFIdx();
    }

    /**
     * 게시글 삭제
     * @param id - PK
     * @return PK
     */
	@Override
    public Long deleteFaq(Long fIdx) {
    	customerMapper.deleteFaqByIdx(fIdx);
        return fIdx;
    }

    /**
     * 게시글 리스트 조회
     * @param params - search conditions
     * @return list & pagination information
     */
	@Override
    public List<FaqDTO> findAllFaq(SearchDTO params) {
		
		List<FaqDTO> faqList = Collections.emptyList();
		
        int count = customerMapper.faqCount(params);

        PaginationInfo paginationInfo = new PaginationInfo(params);
		paginationInfo.setTotalRecordCount(count);

		params.setPaginationInfo(paginationInfo);
		
		if(count > 0) {
			faqList = customerMapper.faqFindAll(params);
		}
        return faqList;
    }

}
