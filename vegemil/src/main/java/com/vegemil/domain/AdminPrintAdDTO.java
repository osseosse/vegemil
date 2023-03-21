package com.vegemil.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AdminPrintAdDTO extends AdminCommonDTO{
	private String tIdx;
	private String tSubject;
	private String tDate;
	private String tImg;
	private String tOnair;
	
	private String tStartdate;
	private String tEnddate;
}
