package com.vegemil.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vegemil.domain.QnaDTO;
import com.vegemil.mapper.QnaMapper;
import com.vegemil.paging.PaginationInfo;

@Service
public class QnaServiceImpl implements QnaService {

	@Autowired
	private QnaMapper qnaMapper;

	@Override
	public boolean registerQna(QnaDTO params) {
		int queryResult = 0;

		if (params.getQId() == null) {
			queryResult = qnaMapper.insertQna(params);
		} else {
			queryResult = qnaMapper.updateQna(params);
		}

		return (queryResult == 1) ? true : false;
	}

	@Override
	public QnaDTO getQnaDetail(QnaDTO params) {
		return qnaMapper.selectQnaDetail(params);
	}

	@Override
	public boolean deleteQna(QnaDTO params) {
		int queryResult = 0;

		QnaDTO qna = qnaMapper.selectQnaDetail(params);

		if (qna != null && "N".equals(qna.getRDeleteYn())) {
			queryResult = qnaMapper.deleteQna(params);
		}

		return (queryResult == 1) ? true : false;
	}

	@Override
	public List<QnaDTO> getQnaList(QnaDTO params) {
		List<QnaDTO> qnaList = Collections.emptyList();

		int qnaTotalCount = qnaMapper.selectQnaTotalCount(params);

		PaginationInfo paginationInfo = new PaginationInfo(params);
		paginationInfo.setTotalRecordCount(qnaTotalCount);

		params.setPaginationInfo(paginationInfo);

		if (qnaTotalCount > 0) {
			qnaList = qnaMapper.selectQnaList(params.getMemNo());
		}

		return qnaList;
	}

}
