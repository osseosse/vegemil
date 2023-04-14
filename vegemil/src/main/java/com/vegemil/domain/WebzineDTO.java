package com.vegemil.domain;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WebzineDTO extends CommonDTO { 

    private Long indexNo;              
    private String qrtYear;
    private String qrtYear2;
    private String wYear;
    private String wQuarter;
    private String fileNo;         
    private String title;          
    private String snsTitle;
    private String category;
    private String remark;
    private String insertDate;
    private String sumLine;
    
}