package com.vegemil.domain;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminBeanSoupNewsDTO extends AdminCommonDTO {
	private Long mIdx;
	private String mTitle;
	private String mUrl;
	private String mThum;
	private Long mDisplay;
	private String mWriteDate;
	private String mStartDate;
	private String mEndDate;
	private Long mIng;
	private MultipartFile fileName;
}
