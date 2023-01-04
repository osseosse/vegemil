package com.vegemil.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.vegemil.domain.PaymentDTO;

@Service
public interface PaymentService {

	public PaymentDTO getPayment(PaymentDTO Payment);
	
	public PaymentDTO getPayment(String lgdTid);

	public List<PaymentDTO> getPaymentList(PaymentDTO params);
	
	public boolean registerPayment(PaymentDTO Payment);
	
	public boolean requestPaymentCancel(String lgdTid);

}
