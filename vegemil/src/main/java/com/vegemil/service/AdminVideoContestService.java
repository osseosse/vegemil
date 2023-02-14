package com.vegemil.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.vegemil.domain.AdminVideoContestDTO;

public interface AdminVideoContestService {
	public List<AdminVideoContestDTO> getAdminVideoContestList(AdminVideoContestDTO params);
	public boolean saveVideoContest(AdminVideoContestDTO params, MultipartFile uploadFile);
	public boolean changeOnairStatus(AdminVideoContestDTO params);
	public AdminVideoContestDTO getAdminContestVideoData(String tIdx);
	
}
