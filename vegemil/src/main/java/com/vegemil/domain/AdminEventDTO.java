package com.vegemil.domain;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminEventDTO extends AdminCommonDTO {

	private MultipartFile fileName;
	
	private String category;
	
	/** 번호 (PK) */
	private Long eIdx;

	/** 제목 */
	private String eSubject;

	/** 내용 */
	private String eContent;

	/** 이미지*/
	private String eImg;

	/** 이미지 - 이미지 원본*/
	private String eImgOriginal;
	
	/** uri*/
	private String eUri;
	
	/** 시작일 */
	private String eStart;
	
	/** 마감일 */
	private String eEnd;

	/** 게시여부 - 메인 */
	private int eActive;
	
	/** 게시여부 - 영유아식*/
	private int eBvactive;

	@Override
	public String toString() {
		return "AdminEventDTO [fileName=" + fileName + ", category=" + category + ", eIdx=" + eIdx + ", eSubject="
				+ eSubject + ", eContent=" + eContent + ", eImg=" + eImg + ", eImgOriginal=" + eImgOriginal + ", eUri="
				+ eUri + ", eStart=" + eStart + ", eEnd=" + eEnd + ", eActive=" + eActive + ", eBvactive=" + eBvactive
				+ "]";
	}

	


}
