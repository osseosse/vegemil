package com.vegemil.domain;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class AdminCfDTO extends AdminCommonDTO {
	
	private String cIdx;
	private String cTitle;
	private String cImg;
	private String cImgOriginal;
	private String cYoutube;
	private String cYoutubeId;
	private String cDate;
	private String cDisplay;
	private MultipartFile fileName;
}
