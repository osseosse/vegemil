package com.vegemil.domain;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// 사업 문의 DTO 
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BizProposalDTO {

	private Long id;

	@NotBlank(message = "업체명은 필수입니다")
	private String companyName; // 업체명

	@NotBlank(message = "담당자 이름은 필수입니다")
	private String personInCharge; // 담당자

	@NotNull
	private int contactNumberStart;
	@NotNull
	private int contactNumberMid;
	@NotNull
	private int contactNumberEnd;

	private String contactNumber; // 연락처

	@NotBlank(message = "이메일 아이디 입력은 필수입니다.")
	private String emailId;
	@NotBlank(message = "이메일 도메인 입력은 필수입니다.")
	private String emailDomain;

	private String email; // 메일

	private String item; // 품목

	@NotBlank(message = "제목을 입력해주세요")
	private String title; // 제목
	@NotBlank(message = "내용을 입력해주세요")
	private String content; // 내용

	private MultipartFile file1; // 첨부1
	private MultipartFile file2; // 첨부2
	private MultipartFile file3; // 첨부3

	private String filePath1; // 첨부1 경로
	private String filePath2; // 첨부2 경로
	private String filePath3; // 첨부3 경로

	@AssertTrue
	private boolean consent;

	private String ipAddr; // 접속 아이피
	private String device; // 접속 기기
	private boolean isCheck; //
	private Timestamp createdAt;

	public BizProposalDTO(String ipAddr, String device) {
		super();
		this.ipAddr = ipAddr;
		this.device = device;
	}

	public BizProposalDTO combineFilels() {

		this.contactNumber = this.contactNumberStart + "-" + this.contactNumberMid + "-" + this.contactNumberEnd;
		this.email = this.emailId + "@" +this.emailDomain;
		
		System.out.println("========combineFiles=========");
		System.out.println(this);
		return this;
	}

	public BizProposalDTO setFilePaths() {

		String savePath = "D:\\uploadData";
		this.filePath1 = saveFile(savePath, file1);
		this.filePath2 = saveFile(savePath, file2);
		this.filePath3 = saveFile(savePath, file3);

		System.out.println("========setFilePaths=========");
		return this;
	}

	private String saveFile(String saveDir, MultipartFile file) {
		if (file == null || file.isEmpty()) {
			return "";
		}

		try {
			String filename = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + "_"
					+ file.getOriginalFilename();

			Path dirPath = Paths.get(saveDir);
			Files.createDirectories(dirPath);

			Path filePath = dirPath.resolve(filename);
			file.transferTo(filePath.toFile());

			return filePath.toString();

		} catch (IOException e) {
			throw new RuntimeException("파일 저장 실패", e);
		}
	}
	
	
	public void setDeviceAndIpAddr(String device, String ipAddr) {
		this.device = device;
		this.ipAddr = ipAddr;
	}


}
