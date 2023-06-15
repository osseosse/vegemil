package com.vegemil.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class AdminCalendarTitleDTO extends AdminCommonDTO {

	/** 번호 (PK) */
	private Long tIdx;

	/** 년도 */
	private String tYear;
	
	/** 월1*/
	private String tMonth1;
	private String tMonth1String;

	/** 월2*/
	private String tMonth2;
	private String tMonth2String;
	
	/** 월3*/
	private String tMonth3;
	private String tMonth3String;

	/** 기준 아기모델 번호*/
	private String tRownum;
	/** 기준 아기이름*/
	private String tBabyname;

}
