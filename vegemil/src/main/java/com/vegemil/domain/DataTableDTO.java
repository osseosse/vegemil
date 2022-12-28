package com.vegemil.domain;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataTableDTO {
	private List<?> data;
	private int draw;
	private int recordsTotal;
	private int recordsFiltered;
}
