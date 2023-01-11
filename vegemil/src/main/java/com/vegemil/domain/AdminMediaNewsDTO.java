package com.vegemil.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminMediaNewsDTO extends AdminCommonDTO {

	/** 번호 (PK) */
	private Long mIdx;

	/** 제목 */
	private String mSubject;

	/** 내용 */
	private String mContent;

	/** 이미지*/
	private String mImg;
	
	/** 날짜 */
	private String mDate;
	
	/** 조회수*/
	private int mHit;
	
	/** 게시여부*/
	private int mDisplay;
	
	/** 삭제여부*/
	private int dleteYn;
	
	/** 등록일 */
	private String insertDate;
	
	/** 수정일 */
	private String modifiedDate;
	
	private String mAdname; 	

	
	private String mAdname1; /** 매체 1*/	
	private String mAduri1; /** 매체 1 uri*/		
	private String mAdname2; /** 매체 2*/		
	private String mAduri2; /** 매체 2 uri*/
	private String mAdname3; /** 매체 3*/		
	private String mAduri3; /** 매체 3 uri*/
	private String mAdname4; /** 매체 4*/		
	private String mAduri4; /** 매체 4 uri*/
	private String mAdname5; /** 매체 5*/		
	private String mAduri5; /** 매체 5 uri*/
	private String mAdname6; /** 매체 6*/		
	private String mAduri6; /** 매체 6 uri*/
	private String mAdname7; /** 매체 7*/		
	private String mAduri7; /** 매체 7 uri*/
	private String mAdname8; /** 매체 8*/		
	private String mAduri8; /** 매체 8 uri*/
	private String mAdname9; /** 매체 9*/		
	private String mAduri9; /** 매체 9 uri*/
	private String mAdname10; /** 매체 10*/		
	private String mAduri10; /** 매체 10 uri*/

	

	

}
