package com.vegemil.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BeansoupVideoDTO extends CommonDTO {
	
	private Long mIdx;
	private String mTitle;
	private Long mDisplay;
	private String mWriteDate;
	private String mSrc;

}
