package com.vegemil.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BabyMetaData {

	private String bMetaguideId;
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

	public BabyMetaData defaultSetting() {
		url = "";
		mTitle = "";
		mDescription = "";
		image = "";

		ogTitle = "";
		ogDescription = "";
		ogImage = "";
		;
		xTitle = "";
		xDescription = "";
		xImage = "";

		canonTag = "";
		
		return this;
	}
}
