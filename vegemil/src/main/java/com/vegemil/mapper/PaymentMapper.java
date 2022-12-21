package com.vegemil.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.vegemil.domain.PaymentDTO;

@Mapper
public interface PaymentMapper {

	public int savePayment(PaymentDTO params);
	
	public PaymentDTO getPayment(PaymentDTO params);

	public List<PaymentDTO> selectPaymentList(PaymentDTO params);

	public int selectPaymentTotalCount();
	
	public int selectPaymentCount(PaymentDTO params);

}
