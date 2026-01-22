
package com.vegemil.domain;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthDTO extends CommonDTO {
	
	private Long memberId; 
	private Long authId;
	private LocalDateTime  createdAt; 
	
}
