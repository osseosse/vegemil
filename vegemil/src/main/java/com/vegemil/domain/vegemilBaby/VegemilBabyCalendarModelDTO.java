package com.vegemil.domain.vegemilBaby;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VegemilBabyCalendarModelDTO {

	private MultipartFile fileName1;
	private MultipartFile fileName2;
	private Long cIdx;
	private String cName; 
	private String cHp; 
	private String cAddr;
	private String cZipcode;
	private String cAddr1;
	private String cAddr2;
	private String cEmail; 
	private String cImage; 
	private String cWriteDate;
	private String cUpdateTime;
	private String cBabyName;
	private String cRoute;
	private String cImage2;
	private String cAlived;
 

}
