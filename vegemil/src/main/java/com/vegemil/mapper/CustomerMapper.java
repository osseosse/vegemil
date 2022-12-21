package com.vegemil.mapper;

import com.vegemil.domain.FaqDTO;
import com.vegemil.domain.FaqFeedbackDTO;
import com.vegemil.domain.SearchDTO;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CustomerMapper {

	public void faqSave(FaqDTO params);
    
	public int faqFeedbackSave(FaqFeedbackDTO params);

	public FaqDTO faqFindByIdx(Long fIdx);
	
	public int faqCountByIdx(Long fIdx);

	public void faqUpdate(FaqDTO params);

	public void deleteFaqByIdx(Long fIdx);

	public List<FaqDTO> faqFindAll(SearchDTO params);

	public int faqCount(SearchDTO params);

}
