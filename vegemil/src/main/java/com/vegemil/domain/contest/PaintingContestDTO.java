package com.vegemil.domain.contest;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.vegemil.domain.CommonDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PaintingContestDTO extends CommonDTO{

	private Long id;
	
	@NotBlank
	private String section; 				//부문
	
	@NotBlank
	private String material; 				// 시제
	
	private String paintingFilename; 		//첨부파일원래이름
	private String paintingSavedFilename; 	//첨부파일저장이름
	
	@Length(max=110)
	private String paintingDesc; 			//작품설명
	
	
	@NotNull
	private String guardianName; 			// 보호자 이름
	@NotNull
	private String guardianPh; 				// 보호자 번호
	@NotNull
	private String guardianDI; 				// 보호자 di
	
	@NotBlank
	private String contestantName; 			//참가자 이름
	@NotBlank
	private String contestantBirth; 		// 참가자 생년월일
	@NotBlank
	private String contestantGrade; 		// 참가자 학년
	
	
	private String contestRoot;  			// 인지 경로
	private String beansoupAwareness; 		// 간단요리사 제품 사전 인지 여부
	
	private String prize; 					// 수상 등수  
	
	private String marketingAgree;			//마케팅 수신 동의여부
	
	//
	
	@NotBlank
	private String zipCode;	
	@NotBlank
	private String addr1;
	private String addr2;
	
	// 풀주소ㄹ

	
	//paging
	private Integer start;
	private Integer length;

}
