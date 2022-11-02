package com.vegemil.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.vegemil.domain.QnaDTO;

@Mapper
public interface QnaMapper {

	public int insertQna(QnaDTO params);

	public QnaDTO selectQnaDetail(QnaDTO params);

	public int updateQna(QnaDTO params);

	public int deleteQna(QnaDTO params);

	public List<QnaDTO> selectQnaList(Long memNo);

	public int selectQnaTotalCount(QnaDTO params);

}
