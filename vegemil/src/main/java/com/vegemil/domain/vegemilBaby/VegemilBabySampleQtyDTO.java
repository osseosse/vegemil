package com.vegemil.domain.vegemilBaby;


import java.time.LocalDate;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class VegemilBabySampleQtyDTO {
	
	private int mId;
	private int mYear; 
	private int mMon;
	private int mNinf; 
	private int mNtod; 
	private int mNkin;
	private Date mUpdateDate;
	
	public VegemilBabySampleQtyDTO() {
		// TODO Auto-generated constructor stub
	}
	
	public VegemilBabySampleQtyDTO(LocalDate date) {
		
		mYear = date.getYear();
		mMon = date.getMonthValue();
				
	
	}

}
