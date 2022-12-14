package com.vegemil.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO extends CommonDTO {

	private Long   pIdx;
	private String pName;
	private String pCode;
	private String pOnuri;
	private Long   pActive;
	private Long   pOnactive;
	private String categoryCode;
	private String categoryName;
	private String subCategoryCode;
	private String subCategoryName;
	private String subCategoryIco;
	private String thumbnail1;
	private String thumbnail2;
	private String thumbnail3;
	private String productClass1;
	private String productClass2;
	private String productClass3;
	private Long   newProduct;
	private Long   bestProduct;
	private Long   newProductAll;
	private Long   repProductAll;
	private Long   newProductCate;
	private Long   repProductCate;
	private Long   displayPriority;
	private String capacity1;
	private String capacity2;
	private String capacity3;
	private String kcal1;
	private String kcal2;
	private String kcal3;
	private String releaseYm;
	private String description;
	private String expirationMonth;
	private String recommender;
	private String tip;
	private String tag1;
	private String tag2;
	private String tag3;
	private String insertDate;

}
