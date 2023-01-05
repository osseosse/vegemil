package com.vegemil.domain;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleDTO {

    private Long   id;              
    private String groupId;               
    private String title;         
    private String writer;          
    private String content;
    private String start;
    private String end;
    private Boolean allday = true;

}