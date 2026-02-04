package com.vegemil.domain;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class QnaDTO extends CommonDTO {

//	private MultipartFile fileName;
	private MultipartFile[] files;
	
	private String[] filePaths = new String[3];
	
	// 질문
	private Long sIdx;
	private String sCate;
	private String sId;
	private String sName;
	private String sHp;
	private String sEmail;
	private String sAddr;
	private String sSubject;
	private String sContent;

	private String sFile;
	private String sFileSub;
	private String sFileThird;

	private String sWritedate;
	private String sUpdatedate;
	private String sActiveuser;
	private String sActive;
	private String sDeleteYn;
	private int sHit;
	private String sGory;
	// 답변
	private String sAnswerYn;
	private String sAnswer;

	public String[] addFilePaths(int idx, String path) {

		filePaths[idx] = path;
		return filePaths;
	}

	public boolean setFileFields() {

		String[] targets = new String[] { sFile, sFileSub, sFileThird };

		if (filePaths != null) {
			for (int i = 0; i < Math.min(filePaths.length, targets.length); i++) {
				if (filePaths[i] != null && !filePaths[i].isEmpty()) {
					targets[i] = filePaths[i];
				}
			}
		}

		sFile = targets[0];
		sFileSub = targets[1];
		sFileThird = targets[2];

		return true;

	}

}