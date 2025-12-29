package com.vegemil.domain;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

// 사업 문의 DTO 
@Getter
@Setter
public class BizProposalDTO {
	
	  private String id; 
	  private String companyName; //업체명 
	  private String personInCharge; //담당자
	  private String contactNumber; // 연락처 
	  private String email; // 메일 
	  private String item; // 품목
	  private String title; // 제목 
	  private String content; // 내용 
	  
	  private String file1; // 첨부1
	  private String file2; // 첨부2
	  private String file3; // 첨부3
	  
	  private String ipAddr; // 접속 아이피 
	  private String device; // 접속 기기
	  private boolean isCheck; //
	  private Timestamp createdAt;
}