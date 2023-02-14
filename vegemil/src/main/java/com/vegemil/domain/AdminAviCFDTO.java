package com.vegemil.domain;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AdminAviCFDTO extends AdminCommonDTO{
	
	private String tIdx;
	private String tSubject;
	private String tDate;
	private String tOnair;
	private String tYoutube;
	private String tYoutubeImg;
	private String tDispOrder;
	
	private String tStartdate;
	private String tEnddate;
}
