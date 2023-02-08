package com.vegemil.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.vegemil.domain.AdminAdEctDTO;

public interface AdminAdEtcService {
	public List<AdminAdEctDTO> getAdminAdEtcList(AdminAdEctDTO params);
	public AdminAdEctDTO getAdminEtcData(String tIdx);
	public boolean saveAdEtc(AdminAdEctDTO params, MultipartFile uploadFile);
	public boolean changeOnairStatus(AdminAdEctDTO params);
}
