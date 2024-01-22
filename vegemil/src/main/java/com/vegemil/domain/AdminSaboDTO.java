package com.vegemil.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminSaboDTO extends AdminCommonDTO {

	/** 번호 (PK) */
	private Long sIdx;

	/** 이름 */
	private String sName;

	/** 휴대전화 */
	private String sHp;

	/** 메모*/
	private String sMemo;
	
	/** 게시여부 */
	private int sActive;
	
	/** 날짜 */
	private String sWritedate;
	
	/** 이메일 */
	private String sEmail;

	/** ?? */
	private String sSabocol;
	
	//excel extract , 1 이면 프린트용 조회  
	private int isPrint = 0;

	

}
