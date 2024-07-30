package com.vegemil.domain.vegemilBaby;


import java.time.LocalDate;

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
	
	public VegemilBabySampleQtyDTO() {
		// TODO Auto-generated constructor stub
	}
	
	public VegemilBabySampleQtyDTO(LocalDate date) {
		
		mYear = date.getYear();
		mMon = date.getMonthValue();
				
	
	}

}
