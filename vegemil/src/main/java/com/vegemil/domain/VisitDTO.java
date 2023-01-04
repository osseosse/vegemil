package com.vegemil.domain;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter	
@Setter
@ToString
public class VisitDTO {
	
	//신청 영역
	private String vIdx;
	private String vWritedate;
	private String vName;
	private String vTel;
	private String vHp;
	private String vEmail;
	private String vArea;
	private String vAddr;
	private String vOrg;
	private String vPcount;
	private String vAppdate;
	private String vApptime;
	private String vMemo;

	// 관리자 영역 
	private String vConfstat;
	private String vConfdate;
	private String vConftime;
	private String vAdminmemo;
	
	/*
	private String hp01;
	private String hp02;
	private String hp03;
	
	private String tel01;
	private String tel02;
	private String tel03;
	
	private String email01;
	private String email02;
	
	private String vZipcode;
	private String vAddr1;
	private String vAddr2;
	*/
	
}
