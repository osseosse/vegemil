package com.vegemil.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AdminRecipeDTO extends AdminCommonDTO {
	private String cIdx;
	private String cWritedate;
	private String cCate;
	private String cCode;
	private String cSubject;
	private String cContent;
	private String cHit;
	private String cCur;
	private String cHasMvp;
	private String cWith;
	private String mbsCheck;
	private String cBvactive;
	private String cVactive;
	private String cYoutubeId;
	
	private String cStartdate;
	private String cEnddate;
	
	
}
