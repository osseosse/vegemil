package com.vegemil.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminSampleBabyDTO extends AdminCommonDTO {

	private Long 	sIdx;
	private String 	sId;
	private String 	sMname;
	private String 	sYear;
	private String 	sHp;
	private String 	sTel;
	private String 	sZipcode;
	private String 	sAddr1;
	private String 	sAddr2;
	private String 	sEmail;
	private String 	sWritedate;
	private String 	sName;
	private String 	sSex;
	private String 	sMaterdate;
	private String 	sFoodplan;
	private String 	sSabo;
	private String 	sBefore;
	private String 	sDeliver;
	private String 	sDeliverdate;
	private String 	sItem;
	private String 	sFeedmethod;
	private String 	sBabycnt;
	private String 	sMaterdate2;
	private String 	sName2;
	private String 	sSex2;
	private String 	sFeedmethod2;
	private String 	sMaterdate3;
	private String 	sName3;
	private String 	sSex3;
	private String 	sFeedmethod3;
	private String 	mSmssend;
	private String 	mEmailsend;
	
	private String gubun;
	//paging
	private int length;
	//? 
	private String sEnddate;
	private String sStartdate;
	
	//excel extract , 1 이면 프린트용 조회  
	private int isPrint = 0;

}
