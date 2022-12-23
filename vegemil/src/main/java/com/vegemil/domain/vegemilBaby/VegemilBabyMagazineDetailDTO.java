package com.vegemil.domain.vegemilBaby;

import com.vegemil.domain.CommonDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VegemilBabyMagazineDetailDTO extends CommonDTO {

	private Long mbsIdx;
	private String mbsMcategory; 
	private String mbsCategory; 
	private String mbsImage; 
	private String mbsTitle; 
	private String mbsGetdate; 
	private String mbsContent; 
	private String mbsSummary; 
	private String mbsAnge; 
	private String mbsAngehtml; 
}

