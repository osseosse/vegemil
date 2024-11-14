package com.vegemil.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GreenbiaMetaData {
	
	private String gMetaguideId;
	private String pageSort;
	
	private String url;
	private String mTitle;
	private String mDescription;
	private String image;
	
	private String ogTitle;
	private String ogDescription;
	private String ogImage;
	
	private String xTitle;
	private String xDescription;
	private String xImage;
	
	private String canonTag;
	
	public void defaultSetting() {
		
		url = "/greenbia/index";
		mTitle = "그린비아 - 국내최초 경장영양식 선도 브랜드 | 정식품";
		mDescription = "그린비아, 그린비아는 1991년 국내기술력으로 탄생한 국내최초 경장영양식 선도 브랜드입니다. 제품 정보 확인 하기 | 정식품";
		image = "https://image.edaymall.com/images/dcf/vegemil/img/greenbia/brand_logo02_renewal.gif";
		
		ogTitle = "그린비아 - 국내최초 경장영양식 선도 브랜드 | 정식품";
		ogDescription = "그린비아, 그린비아는 1991년 국내기술력으로 탄생한 국내최초 경장영양식 선도 브랜드입니다. 제품 정보 확인 하기 | 정식품";
		ogImage = "https://image.edaymall.com/images/dcf/vegemil/img/greenbia/brand_logo02_renewal.gif";
		
		xTitle = "그린비아 - 국내최초 경장영양식 선도 브랜드 | 정식품";
		xDescription = "그린비아, 그린비아는 1991년 국내기술력으로 탄생한 국내최초 경장영양식 선도 브랜드입니다. 제품 정보 확인 하기 | 정식품";
		xImage = "https://image.edaymall.com/images/dcf/vegemil/img/greenbia/brand_logo02_renewal.gif";
		
		canonTag = "/greenbia/index";
	}
	

}
