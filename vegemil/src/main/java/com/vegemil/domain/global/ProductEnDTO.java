package com.vegemil.domain.global;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductEnDTO {

	private Long p_idx;
	private String category_code;
	private String category_name;
	private String sub_category_code;
	private String sub_category_name;
	private String sub_category_ico;
	private String p_code; 
	private String thumbnail1; 
	private String thumbnail2; 
	private String thumbnail3; 
	private String p_name; 
	private String ko_name; 
	private String count; 
	private String display_priority; 
	private String new_product; 
	private String best_product; 
	private String new_product_all; 
	private String rep_product_all; 
	private String new_product_cate; 
	private String rep_product_cate; 
	private String p_active; 
	private String p_onactive; 
	private String p_onuri; 
	private String capacity1; 
	private String capacity2; 
	private String capacity3; 
	private String kcal1; 
	private String kcal2; 
	private String kcal3; 
	private String release_ym; 
	private String description;
	private String expiration_month; 
	private String recommender; 
	private String tip; 
	private String tag1; 
	private String tag2; 
	private String tag3; 
	private String insert_date; 
	private String brand_display;
}
