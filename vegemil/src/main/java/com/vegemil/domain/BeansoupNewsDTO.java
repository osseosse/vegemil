package com.vegemil.domain;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BeansoupNewsDTO extends CommonDTO{
	
	private Long mIdx;
	private String mTitle;
	private String mUrl;
	private String mThum;
	private Long mDisplay;
	private Date mWritedate;
	private Date mStartdate; 
	private Date mEnddate;
	private Long mIng;
	
	// μΆκ° νλ
	private String ingStr;
	private String thumPath;

}
