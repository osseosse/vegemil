package com.vegemil.service;

import java.util.List;

import com.vegemil.domain.FaqDTO;
import com.vegemil.domain.FaqFeedbackDTO;
import com.vegemil.domain.SearchDTO;

public interface CustomerService {

    public Long saveFaq(FaqDTO params);
    
    public boolean saveFaqFeedback(FaqFeedbackDTO params);

    public FaqDTO findFaqById(Long id);

    public Long updateFaq(FaqDTO params);

    public Long deleteFaq(Long id);

    public List<FaqDTO> findAllFaq(SearchDTO params);

}
