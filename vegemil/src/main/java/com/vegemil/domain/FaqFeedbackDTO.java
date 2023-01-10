package com.vegemil.domain;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FaqFeedbackDTO {

	private Long idx;
    private Long fIdx;          
    private String fId;               
    private String fScore;         
    private String fEtcbox;          
    private String fName;
    private String fQuestion;
    private String fInsertdate;  

}