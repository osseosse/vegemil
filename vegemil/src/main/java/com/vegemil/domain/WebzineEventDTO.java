package com.vegemil.domain;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.vegemil.constant.WebzineEventCate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WebzineEventDTO {

    private Long indexNo;
    
    @NotBlank
    private String custName;
    
    @NotBlank
    private String custHp;
    
    @NotBlank
    @Email
    private String custEmail;
    
    @NotBlank
    private String qrt;
    private String remark;
    private String answer;
    private String applyDate;
    
    @NotNull
    WebzineEventCate category;

}