package com.vegemil.domain.vegemilBaby;

import org.springframework.web.multipart.MultipartFile;

import com.github.rkpunjal.sqlsafe.SQLInjectionSafe;

import lombok.Data;

@Data
public class VegemilBabyCalendarModelDTO {

	private @SQLInjectionSafe MultipartFile fileName1;
	private @SQLInjectionSafe MultipartFile fileName2;
	private Long cIdx;
	private Long ROWNUM;
	private @SQLInjectionSafe String cName; 
	private @SQLInjectionSafe String cHp; 
	private @SQLInjectionSafe String cAddr;
	private @SQLInjectionSafe String cZipcode;
	private @SQLInjectionSafe String cAddr1;
	private @SQLInjectionSafe String cAddr2;
	private @SQLInjectionSafe String cEmail; 
	private @SQLInjectionSafe String cImage; 
	private @SQLInjectionSafe String cRank; 
	private @SQLInjectionSafe String cHonor; 
	private @SQLInjectionSafe String cWriteDate;
	private @SQLInjectionSafe String cUpdateTime;
	private @SQLInjectionSafe String cMonth;
	private @SQLInjectionSafe String cBabyName;
	private @SQLInjectionSafe String cRoute;
	private @SQLInjectionSafe String cImage2;
	private @SQLInjectionSafe String cAlived;
}
