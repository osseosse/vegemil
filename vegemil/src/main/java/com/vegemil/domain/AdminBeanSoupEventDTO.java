package com.vegemil.domain;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminBeanSoupEventDTO extends AdminCommonDTO {
	private Long mIdx;
	private String mTitle;
	private String mStartDate;
	private String mEndDate;
	private Long mIng;
	private Long mDisplay;
	private String mThum;
	private String mEdayId;
	private String mContent;
	private Long mType;
	private String mProduct;
	private MultipartFile fileName;
}
