package com.vegemil.domain;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FaqFeedbackDTO {

    private Long idx;              
    private String fId;               
    private String fScore;         
    private String fEtcbox;          
    private String fName;
    private Long faqIdx;
    private String fQuestion;
    private String fInsertdate;  

}