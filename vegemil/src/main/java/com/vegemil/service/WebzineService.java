package com.vegemil.service;

import java.util.List;

import com.vegemil.domain.WebzineDTO;
import com.vegemil.domain.SearchDTO;
import com.vegemil.domain.SubscribeDTO;

public interface WebzineService {

    public boolean saveWebzine(WebzineDTO params);
    
    public boolean saveSubscribe(SubscribeDTO params);
    
    public WebzineDTO findWebzineById(Long indexNo);

    public Long updateWebzine(WebzineDTO params);

    public Long deleteWebzine(Long indexNo);

    public List<WebzineDTO> findAllWebzine(SearchDTO params);
    
    public List<SubscribeDTO> findAllWebzine();
    
    public List<WebzineDTO> getRecommandWebzine();
    
    public List<WebzineDTO> getWebzineQrtYear(String qrtYear);
    
    public List<WebzineDTO> getWebzineYear();
    
    public List<WebzineDTO> getWebzineQrt();
    
    public List<WebzineDTO> getWebzineLink();
    
    public List<WebzineDTO> getWebzineQ(String qrtYear);

    public WebzineDTO getWebzine(WebzineDTO params);
    
    public List<WebzineDTO> getWebzine02();
    public List<WebzineDTO> getWebzine03();
    public List<WebzineDTO> getWebzine04();
    public List<WebzineDTO> getWebzine05();
    public List<WebzineDTO> getWebzine06();
}
