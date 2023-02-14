package com.vegemil.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.vegemil.domain.AdminAviCFDTO;
import com.vegemil.domain.AdminVideoContestDTO;

public interface AdminAviCFService {
	public List<AdminAviCFDTO> getAdminAviCFList(AdminAviCFDTO params);
	public boolean saveAviCF(AdminAviCFDTO params, MultipartFile uploadFile);
	public boolean changeOnairStatus(AdminAviCFDTO params);
	public AdminAviCFDTO getAdminAviCFData(String tIdx);
	
}
