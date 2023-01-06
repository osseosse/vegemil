package com.vegemil.domain;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter	
@Setter
@ToString
public class ScheduleDTO {
	
	//신청 영역
	private String id;
	private String title;
	private String content;
	private String start;
	private String end;
	
}
