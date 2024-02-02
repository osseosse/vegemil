package com.vegemil.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PopupDTO {

	
	private Long idx;
	private String imgUrl;
	private String hrefUrl;
	private String startDate;
	private String endDate;
	private String alt;
	private String active;
	
	public PopupDTO(Long idx, String active) {
		this.idx = idx;
		this.active = active;
	}
	
}
