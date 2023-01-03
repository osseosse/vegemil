package com.vegemil.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentDTO {

	private Long   rowIndex;
	private String lgdRespcode;
	private String lgdRespmsg;
	private String lgdMid;
	private String lgdOid;
	private Double lgdAmount;
	private String lgdTid;
	private String lgdPaytype;
	private String lgdPaydate;
	private String lgdFinancecode;
	private String lgdFinancename;
	private String lgdBuyer;
	private String lgdBuyerid;
	private String lgdBuyermail;
	private String lgdProductinfo;
	private String lgdCardnum;
	private String lgdCardinstallmonth;
	private String lgdCardnointyn;
	private String lgdFinanceauthnum;
	private String insertDate;
	private String lgdStatusCode;
	private String lgdStatus;
	private String lgdIp;
	private String lgdCancelDate;
	private String cancelDate;
	private String cancelReq;
	private String cancelReqDate;
	private String lgdReceiptUrl;
	private String lgdMd5;

}
