package com.vegemil.domain;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ThermometerLoveDTO {
	
	private long idx;
	private int year;
	private double temperature;
	private int active;
	private LocalDateTime fromDate;
	
	public ThermometerLoveDTO(int year, double temperature) {
		super();
		this.year = year;
		this.temperature = temperature;
	}
	
	

}
