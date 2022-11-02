package com.vegemil.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QnaDTO extends CommonDTO {

	//질문
	private Long qId;
	private Long memNo;
	private String rIdx;
	private String rCompany;
	private String rTitle;
	private String rContent;
	private String rInsertDate;
	private String rUpdateDate;
	private String rDeleteDate;
	private String rDeleteYn;
	
	private String rEmail;
	private String rPhoneNo;
	private String rAgree;
	
	//답변
	private String rAnswerYn;
	private String rAnswer;
	private String rAnswerEmail;
	private String rAnswerTel;
	private String rAnswerDate;

}
