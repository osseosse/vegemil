package com.vegemil.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class VegemilMetaData {
	
	
	private String vMetaguideId;
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
	
	public VegemilMetaData defaultSetting() {
		
		url = "/product/list";
		mTitle = "베지밀 제품 페이지 - 정식품";
		mDescription = "베지밀 제품 리스트, 50여년 역사를 지닌 대한민국 정통 두유 브랜드 베지밀. 프리미엄 두유, 플레인 두유, 블랙 두유, 영·유아식 등 - 정식품";
		image = "https://image.edaymall.com/images/dcf/vegemil/img/ico/logo.png";
		
		ogTitle = "/product/list";
		ogDescription = "베지밀 제품 페이지 - 정식품";
		ogImage = "https://image.edaymall.com/images/dcf/vegemil/img/ico/logo.png";
		
		xTitle = "베지밀 제품 페이지 - 정식품";
		xDescription = "베지밀 제품 리스트, 50여년 역사를 지닌 대한민국 정통 두유 브랜드 베지밀. 프리미엄 두유, 플레인 두유, 블랙 두유, 영·유아식 등 - 정식품";
		xImage ="https://image.edaymall.com/images/dcf/vegemil/img/ico/logo.png";
		
		canonTag = "/product/list";
		
		return this;
	}

}
