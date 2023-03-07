package com.vegemil.paging;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardResponseVO {
	
	private Long ROWNUM;
	private String cMonth;
	private String cName;
	private String cImage;
	private String cRank;
	private String cHonor;
	private String cTitle2nd;
}
