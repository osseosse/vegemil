package com.vegemil.domain;

import java.sql.Timestamp;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BizProposalDTO {

	private Long id;

	private String capchaKey;
	private String captchaInput;

	@NotBlank(message = "업체명은 필수입니다")
	private String companyName;

	@NotBlank(message = "담당자 이름은 필수입니다")
	private String personInCharge;

	@NotBlank(message = "연락처 입력은 필수입니다")
	private String contactNumberStart;
	@NotBlank(message = "연락처 입력은 필수입니다")
	private String contactNumberMid;
	@NotBlank(message = "연락처 입력은 필수입니다")
	private String contactNumberEnd;

	private String contactNumber;

	@NotBlank(message = "이메일 아이디 입력은 필수입니다.")
	private String emailId;
	@NotBlank(message = "이메일 도메인 입력은 필수입니다.")
	private String emailDomain;

	private String email;

	private String item;

	@NotBlank(message = "제목을 입력해주세요")
	private String title;
	@NotBlank(message = "내용을 입력해주세요")
	private String content;

	private MultipartFile file1;
	private MultipartFile file2;
	private MultipartFile file3;

	private String filePath1;
	private String filePath2;
	private String filePath3;

	private String fileOriginName1;
	private String fileOriginName2;
	private String fileOriginName3;

	@AssertTrue(message = "개인정보수집 방법에 동의해야만 문의가 등록됩니다.")
	private boolean consent;

	private String ipAddr;
	private String device;
	private int isCheck;
	private Timestamp createdAt;

	public BizProposalDTO(String capString) {
		this.capchaKey = capString;
	}

	public BizProposalDTO setDeviceAndIpAddr(String device, String ipAddr) {
		this.device = device;
		this.ipAddr = ipAddr;
		return this;
	}

}
