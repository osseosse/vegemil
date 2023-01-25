package com.vegemil.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminWebzineEventDTO {

	private Long indexNo;              
    private String custName;
    private String custHp;
    private String custEmail;
    private String qrt;
    private String remark;
    private String answer;
    private String applyDate;
}
