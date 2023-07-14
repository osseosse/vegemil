package com.vegemil.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BannerDTO {
	
	public BannerDTO(String qrtYear, String article) {
		this.qrtYear = qrtYear;
		this.article = article;
	}
	
	private String idx;
	private String domainName; 
	private String linkAddr; 
	private String linkAddr2; 
	private String imgName;
	private String imgName2;
	private String qrtYear;
	private String article;
	private String imgCount; 

}
