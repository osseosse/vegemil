package com.vegemil.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vegemil.domain.DataTableDTO;
import com.vegemil.domain.PaymentDTO;
import com.vegemil.mapper.AdminPaymentMapper;

@Service
public class AdminPaymentServiceImpl implements AdminPaymentService {
	
	@Autowired
	private AdminPaymentMapper adminPaymentMapper;

	@Override
	public DataTableDTO getPaymentList(Map<String, Object> paramMap) {
		List<PaymentDTO> paymentList = Collections.emptyList();
		DataTableDTO dataTableDto = new DataTableDTO();

		int paymentTotalCount = adminPaymentMapper.selectPaymentTotalCount(paramMap);

		if (paymentTotalCount > 0) {
			int start = Integer.parseInt(paramMap.get("start").toString());
			int length = Integer.parseInt(paramMap.get("length").toString());
			
			paramMap.put("start", start);
			paramMap.put("length", length);
			paymentList = adminPaymentMapper.selectPaymentList(paramMap);
		}
		
		dataTableDto.setData(paymentList);
		dataTableDto.setRecordsTotal(paymentTotalCount);
		dataTableDto.setRecordsFiltered(paymentTotalCount);
		dataTableDto.setDraw(Integer.parseInt(paramMap.get("draw").toString()));

		return dataTableDto;
	}
	
	@Override
	public PaymentDTO getPayment(String lgdTid) {
		
		PaymentDTO payment = new PaymentDTO();
		payment = adminPaymentMapper.selectPayment(lgdTid);
		if(payment == null) {
			payment = new PaymentDTO();
		}
		
		return payment;
	}
	
	
	@Override
	public boolean registerPayment(PaymentDTO payment) {
		
		int queryResult = 0;
		
		queryResult = adminPaymentMapper.savePayment(payment);

		return (queryResult == 1) ? true : false;
	}
}
