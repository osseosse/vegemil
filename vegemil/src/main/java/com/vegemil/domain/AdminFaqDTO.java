package com.vegemil.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminFaqDTO extends AdminCommonDTO {

	/** 번호 (PK) */
	private Long fIdx;

	/** 구분 */
	private String fCate;

	/** 질문 */
	private String fQuestion;

	/** 답변 */
	private String fAnswer;

	/** 조회 수 */
	private int fView;
	
	/** 히트 */
	private int fHit;

	/** 작성일 */
	private String fEditdate;
	
	/** 카테고리 */
	private String fGory;

	/** 이미지 경로 */
	private String fImg;

}
