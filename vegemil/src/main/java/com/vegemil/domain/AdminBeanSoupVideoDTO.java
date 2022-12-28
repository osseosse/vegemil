package com.vegemil.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminBeanSoupVideoDTO extends AdminCommonDTO {

	private Long mIdx;
	private String mTitle;
	private Long mDisplay;
	private String mWriteDate;
	private String mSrc;
}
