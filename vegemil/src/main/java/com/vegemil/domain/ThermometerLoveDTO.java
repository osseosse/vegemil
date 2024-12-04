package com.vegemil.domain;

import java.time.LocalDateTime;

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
public class ThermometerLoveDTO {
	
	private long idx;
	private int year;
	private double temperature;
	private int active;
	private LocalDateTime fromDate;
	
	private int isOpen = 1;
	
	public ThermometerLoveDTO(int year,double temperature) {
		this.year= year;
		this.temperature = 0;
	}

}
