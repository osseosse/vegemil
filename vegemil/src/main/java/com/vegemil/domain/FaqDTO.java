package com.vegemil.domain;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FaqDTO extends CommonDTO {

    private Long fIdx;              
    private String fCate;               
    private String fQuestion;         
    private String fAnswer;          
    private String fView;
    private String fEditdate;
    private String fGory;
    private String fImg;
    private int fHit;
    private int noticeYn;        
    private String createdDate;  
    private String modifiedDate;
    

}