package com.vegemil.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.vegemil.domain.PaymentDTO;
import com.vegemil.mapper.PaymentMapper;

@Service
public class PaymentServiceImpl implements PaymentService {

	@Autowired
	private PaymentMapper paymentMapper;
	
	@Override
	public List<PaymentDTO> getPaymentList(PaymentDTO payment) {
		List<PaymentDTO> paymentList = Collections.emptyList();

		int paymentCount = paymentMapper.selectPaymentCount(payment);

		if (paymentCount > 0) {
			paymentList = paymentMapper.selectPaymentList(payment);
		}

		return paymentList;
	}
	
	@Override
	public PaymentDTO getPayment(PaymentDTO Payment) {
		
		PaymentDTO payment = new PaymentDTO();
		
		int paymentCount = paymentMapper.selectPaymentCount(Payment);

		if (paymentCount > 0) {
			payment = paymentMapper.selectPayment(payment);
		}
		
		return payment;
	}
	
	@Override
	public PaymentDTO getPayment(String lgdTid) {
		
		PaymentDTO payment = new PaymentDTO();
		payment = paymentMapper.selectPaymentByTid(lgdTid);
		
		return payment;
	}
	
	@Override
	public boolean registerPayment(PaymentDTO payment) {
		
		int queryResult = 0;
		
		queryResult = paymentMapper.savePayment(payment);

		return (queryResult == 1) ? true : false;
	}
	
	@Override
	public boolean requestPaymentCancel(String lgdTid) {
		
		int queryResult = 0;
		
		queryResult = paymentMapper.updatePaymentCancel(lgdTid);

		return (queryResult == 1) ? true : false;
	}
	
	
}
