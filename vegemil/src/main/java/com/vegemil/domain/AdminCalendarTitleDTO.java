package com.vegemil.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class AdminCalendarTitleDTO extends AdminCommonDTO {

	/** 번호 (PK) */
	private Long tIdx;

	/** 년도 */
	private int tYear;
	
	/** 월1*/
	private int tMonth1;

	/** 월2*/
	private int tMonth2;

	/** 기준 아기모델 번호*/
	private int tRownum;

}
