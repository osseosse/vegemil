package com.vegemil.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.vegemil.domain.AdminRadioCMDTO;

public interface AdminAviRadioCMService {
	
	public List<AdminRadioCMDTO> getRadioCMList(AdminRadioCMDTO params);
	public boolean saveRadioCM(AdminRadioCMDTO params, MultipartFile uploadFile);
	public boolean changeOnairStatus(AdminRadioCMDTO params);
	public AdminRadioCMDTO getRadioCMData(String tIdx);
	
}
