package com.vegemil.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.vegemil.domain.AdminPrintAdDTO;

public interface AdminPrintADService {
	
	public List<AdminPrintAdDTO> getPrintADList(AdminPrintAdDTO params);
	public boolean savePrintAD(AdminPrintAdDTO params, MultipartFile uploadFile);
	public boolean changeOnairStatus(AdminPrintAdDTO params);
	public AdminPrintAdDTO getPrintADData(String tIdx);
	
}
