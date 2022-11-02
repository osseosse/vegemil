package com.vegemil.service;

import java.util.List;

import com.vegemil.domain.QnaDTO;

public interface QnaService {

	public boolean registerQna(QnaDTO params);

	public QnaDTO getQnaDetail(QnaDTO params);

	public boolean deleteQna(QnaDTO params);

	public List<QnaDTO> getQnaList(QnaDTO params);

}
