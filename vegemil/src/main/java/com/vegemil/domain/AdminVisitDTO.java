package com.vegemil.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminVisitDTO {

	private Long vIdx;
	private String vWritedate;
	private String vName;
	private String vTel;
	private String vHp;
	private String vEmail;
	private String vArea;
	private String vAddr;
	private String vOrg;
	private int vPcount;
	private String vAppdate;
	private int vApptime;
	private String vMemo;
	private int vConfstat;
	private String vConfdate;
	private int vConftime;
	private String vAdminmemo;
	private int vDisplay;
	private int vCancel;
}
