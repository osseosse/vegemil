package com.vegemil.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QnaDTO extends CommonDTO {

	//질문
	private Long sIdx;
	private String sCate;
	private String sId;
	private String sName;
	private String sHp;
	private String sEmail;
	private String sAddr;
	private String sSubject;
	private String sContent;
	private String sFile;
	private String sWritedate;
	private String sUpdatedate;
	private String sActiveuser;
	private String sActive;
	private String sDeleteYn;
	private int sHit;
	private String sGory;
	//답변
	private String sAnswerYn;
	private String sAnswer;
	

}