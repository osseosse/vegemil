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
	private String cUpdatetime;
	
	/** 아기이름 */
	private String cBabyName;
	
	/** 유입 경로 */
	private String cRoute;
	
	/** 파일명2 */
	private String cImage2;
	
	/** 개월 수 */
	private String cAlived;
	
	/** 회원ID */
	private String mId;

}
