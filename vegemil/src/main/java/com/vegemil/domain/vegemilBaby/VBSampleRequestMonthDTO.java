package com.vegemil.domain.vegemilBaby;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString 
public class VBSampleRequestMonthDTO {
	
	private String sItem; // 아이템
	private int reqCnt; // 샘플 신청 건수 
	private int reqMonth; // 현재 월

}
