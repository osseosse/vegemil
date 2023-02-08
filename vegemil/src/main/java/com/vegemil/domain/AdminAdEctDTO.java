package com.vegemil.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AdminAdEctDTO extends AdminCommonDTO {
	
	private String tIdx;
	private String tCate;
	private String tSubject;
	private String tContent;
	private String tFlv;
	private String tOnair;
	private String tImgNew;
	
	private String cStartdate;
	private String cEnddate;
	
	
}
