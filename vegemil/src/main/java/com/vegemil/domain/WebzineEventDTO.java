package com.vegemil.domain;


import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WebzineEventDTO {

    private Long indexNo;
    
    @NotBlank
    private String custName;
    private String custHp;
    
    @NotBlank
    private String custEmail;
    
    @NotBlank
    private String qrt;
    private String remark;
    private String answer;
    private String applyDate;

}