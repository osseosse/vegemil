package com.vegemil.domain.contest;


import com.vegemil.domain.CommonDTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
public class PaintingContestAward23DTO extends CommonDTO {
	
	private String id;
	private String section;
	private String award;
	private String material;
	private String guardianName;
	private String guardianPh;
	private String contestantName;
	private String contestantGrade;
	private String paintingDesc; 
	private String paintingFilename;	

}
