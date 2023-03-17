package com.vegemil.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAgentDTO {

	private Long mId;
    private Long pc;
    private Long mobile;
    private Long tablet;
    private Long ios;
    private Long android;
    private Long etc;
    private String yymm;

}
