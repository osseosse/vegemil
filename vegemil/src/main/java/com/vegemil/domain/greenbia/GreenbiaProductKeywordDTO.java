package com.vegemil.domain.greenbia;

import com.vegemil.domain.CommonDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GreenbiaProductKeywordDTO extends CommonDTO {

	private String gName; 
	private String gCode;
	private String gLcate; 
	private String gMcate; 
	private String gContents; 	
	private Long   gTube; 	
	private String gCapacity;
	private String gCal;
	private String gTubeMark; 
	private String gKeyword; 
	
}
