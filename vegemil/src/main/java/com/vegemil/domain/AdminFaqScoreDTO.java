package com.vegemil.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminFaqScoreDTO extends AdminCommonDTO {
	private Long idx;
	private String fId;
	private String fScore;
	private String fEtcBox;
	private String fName;
	private Long faqIdx;
	private String fQuestion;
	private String fInsertdate;
}
