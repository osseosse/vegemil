package com.vegemil.domain.vegemilBaby;

import org.springframework.web.multipart.MultipartFile;

import com.github.rkpunjal.sqlsafe.SQLInjectionSafe;
import com.vegemil.domain.CommonDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VegemilBabyBestReviewDTO extends CommonDTO {

	private MultipartFile fileName;
	private Long sIdx;
	private @SQLInjectionSafe String sUrl; 
	private String sWritedate;
	private String sWritetime; 
	private String sUid; 
	private @SQLInjectionSafe String sTitle; 
	private @SQLInjectionSafe String sEdayId; 
	private String sDesctime; 
	private @SQLInjectionSafe String sSns; 	
	private String sImage; 
	private String sRank; 
	private String sHonor; 
	private String sYear; 
	private String sMonth; 
 

}
