package com.vegemil.domain;


import com.github.rkpunjal.sqlsafe.SQLInjectionSafe;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubscribeDTO {

    private Long   sIdx;              
    private @SQLInjectionSafe String sName;               
    private @SQLInjectionSafe String sHp;         
    private @SQLInjectionSafe String sMemo;          
    private Long   sActive;
    private String sWritedate;
    private @SQLInjectionSafe String sEmail;
    private String mSabocol;  

}