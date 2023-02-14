package com.vegemil.domain;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AdminVideoContestDTO extends AdminCommonDTO{
	
	private String tIdx;
	private String tSubject;
	private String tDate;
	private String tImg;
	private String tFlv;
	private String tOnair;
	private String tYoutube;
	private String tYoutubeImg;
	private String tDispOrder;
	
	private String tStartdate;
	private String tEnddate;
}
