package com.vegemil.domain;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.vegemil.paging.Criteria;
import com.vegemil.paging.PaginationInfo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonDTO extends Criteria {

	/** 페이징 정보 */
	private PaginationInfo paginationInfo;
	
	/** 삭제 여부 */
	private String deleteYn;

	/** 등록일 */
	//@DateTimeFormat(pattern = "yyyy-MM-dd")
	private String insertTime;

	/** 수정일 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate updateTime;

	/** 삭제일 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate deleteTime;

}
