package com.vegemil.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MailDTO {

	private String address;
    private String title;
    private String message;
    private String memId;

}
