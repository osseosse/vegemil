package com.vegemil.domain.vegemilBaby;

import com.vegemil.domain.CommonDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VegemilBabyEventDTO extends CommonDTO {

	private Long eIdx;
	private String eSubject; 
	private String eContent; 
	private String eImg; 
	private String eStart; 
	private String eEnd; 
	private String eUri; 
	private String eActive; 
	private String eCate; 

}
