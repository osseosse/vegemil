package com.vegemil.domain;

import java.util.Date;

import com.github.rkpunjal.sqlsafe.SQLInjectionSafe;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ClaimDTO {
	private String cIdx;
	private @SQLInjectionSafe String cName;
	private @SQLInjectionSafe String cHp;
	private @SQLInjectionSafe String cTel;
	private @SQLInjectionSafe String cEmail;
	private @SQLInjectionSafe String cSubject;
	private @SQLInjectionSafe String cContent;
	private String cWritedate;
	private String cWritetime;
	private String cCheck;
	private String cAnswer;
	private String cIp;
	
	private String tel01;
	private String tel02;
	private String tel03;
	
	private String hp01;
	private String hp02;
	private String hp03;
	
	private String email01;
	private String email02;
	
	private Date submitTime; 
}
