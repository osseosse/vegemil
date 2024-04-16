package com.vegemil.domain;

import com.github.pagehelper.util.StringUtil;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductDTO extends CommonDTO {

	private Long   pIdx;
	private String pName;
	private String pCode;
	private String pOnuri;
	private Long   pActive;
	private Long   pOnactive;
	private Long   count;
	private String categoryCode;
	private String categoryName;
	private String mCategoryCode;
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

	// 추가
	
	private String bottleImg;
	private String packaging16Img;
	private String packaging24Img;
	private String packaging15Img;
	private String swiperClass;
	
	private String edayLinkBottle;
	private String edayLink16;
	private String edayLink24;
	private String edayLink15;
	
	private String productClassBottle;
	private String productClass16;
	private String productClass24;
	private String productClass15;
	
	private int brandDisplay;
	private int brandDisPriority;
	
	private int brandPreSection;
	private int brandPostSection;
	private int brandEntire;
	
	public void detailImgClassSet() {
		if(!StringUtil.isEmpty(bottleImg)) {
			this.productClassBottle = bottleImg.substring(0,bottleImg.indexOf('.'));
			System.out.println("===========> " + this.productClassBottle);
		}
		
		if(!StringUtil.isEmpty(packaging16Img)) {
			this.productClass16 = packaging16Img.substring(0, packaging16Img.indexOf('.'));
			System.out.println("===========> " + this.productClass16);
		}
		
		if(!StringUtil.isEmpty(packaging24Img)) {
			this.productClass24 = packaging24Img.substring(0, packaging24Img.indexOf('.'));
			System.out.println("===========> " + this.productClass24);
		}
		
		if(!StringUtil.isEmpty(packaging15Img)) {
			this.productClass15 = packaging15Img.substring(0,packaging15Img.indexOf('.'));
			System.out.println("===========> " + this.productClass15);
		}
	}
	
}
