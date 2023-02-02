package com.vegemil.domain.vegemilBaby;

import org.springframework.web.multipart.MultipartFile;

import com.vegemil.domain.CommonDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VegemilBabyBestReviewDTO extends CommonDTO {

	private MultipartFile fileName;
	private Long sIdx;
	private String sUrl; 
	private String sWritedate;
	private String sWritetime; 
	private String sUid; 
	private String sTitle; 
	private String sEdayId; 
	private String sDesctime; 
	private String sSns; 	
	private String sImage; 
	private String sRank; 
	private String sHonor; 
	private String sYear; 
	private String sMonth; 
 

}
