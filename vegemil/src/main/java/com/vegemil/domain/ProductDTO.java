package com.vegemil.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO extends CommonDTO {

	private Long   pIdx; //bigint(10)
	private String pCate; //varchar(16)
	private String pName; //varchar(40)
	private String pCode; //varchar(10)
	private String pOnuri;
	private Long   pOnclick; //int(2)
	private Long   pActive; //int(2)
	private Long   pOnactive;
	private String categoryCode; //varchar(16)
	private String categoryName;
	private String subCategoryCode;
	private String subCategoryName;
	private String subCategoryIco;
	private String thumbnail1;
	private String thumbnail2;
	private String thumbnail3;
	private String newOrBest;
	private Long   displayPriority;
	private String linkUrl;
	private String capacity1;
	private String capacity2;
	private String capacity3;
	private String kcal1; //varchar(14)
	private String kcal2;
	private String kcal3;
	private String releaseYm;
	private String description;
	private String expirationDate;
	private String recommender;
	private String tip;
	private String tag;
	private String insertDate;
	private String modifyDate;

	

}
