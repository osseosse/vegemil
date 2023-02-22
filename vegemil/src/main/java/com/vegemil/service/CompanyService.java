package com.vegemil.service;

import java.util.List;

import com.vegemil.domain.MediaNewsDTO;
import com.vegemil.domain.SearchDTO;

public interface CompanyService {

    public boolean saveMediaNews(MediaNewsDTO params);

    public MediaNewsDTO findMediaNewsByIdx(Long mIdx);

    public Long updateMediaNews(MediaNewsDTO params);

    public Long deleteMediaNews(Long mIdx);

    public List<MediaNewsDTO> findAllMediaNews(SearchDTO params);
    
    public List<MediaNewsDTO> getMediaNewsTop3();

    public boolean updateMediaNewsCount(Long mIdx);

}
