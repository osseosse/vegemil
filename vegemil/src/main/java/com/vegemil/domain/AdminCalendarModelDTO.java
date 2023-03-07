package com.vegemil.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminCalendarModelDTO extends AdminCommonDTO {

	/** 번호 (PK) */
	private Long cIdx;

	/** 이름 */
	private String cName;

	/** 전화번호 */
	private String cHp;

	/** 주소*/
	private String cAddr;

	/** 이메일 */
	private String cEmail;

	/** 파일명1 여부 */
	private String cImage;

	/** 응모 날짜 */
	private String cWriteDate;
	
	/** 등수 */
	private String cRank;
	
	/** 업데이트 시간 */
	private String cUpdateTime;
	
	/** 아기이름 */
	private String cBabyName;
	
	/** 유입 경로 */
	private String cRoute;
	
	/** 파일명2 */
	private String cImage2;
	
	/** 메인이미지 */
	private String cMainImage;
	private String cMainImage2;
	
	/** 이미지 선택정보 */
	private String imgSelect;
		
	/** 개월 수 */
	private String cAlived;
	
	/** 회원ID */
	private String mId;
	
	private int cAngle;
	private int cAngle2;
	
	private String cMonth;
	private String rownum;
	
	/** 2개월치 1등 선정할 때 두 번째 1등인지 여부 
	 * 해당   :1
	 * 미해당:0
	 * */	
	private int cTitle2nd;

}
