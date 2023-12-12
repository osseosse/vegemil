package com.vegemil.domain.global;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductEnDTO {

	private Long pIdx;
	private String categoryCode;
	private String categoryName;
	private String sub_categoryCode;
	private String sub_categoryName;
	private String sub_categoryIco;
	private String pCode; 
	private String thumbnail1; 
	private String thumbnail2; 
	private String thumbnail3; 
	private String pName; 
	private String koName; 
	private String count; 
	private String displayPriority; 
	private String newProduct; 
	private String bestProduct; 
	private String newProductAll; 
	private String repProductAll; 
	private String newProductCate; 
	private String repProductCate; 
	private String pActive; 
	private String pOnactive; 
	private String pOnuri; 
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
	private String brandDisplay;
}
