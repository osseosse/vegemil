package com.vegemil.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.vegemil.domain.PaymentDTO;

@Service
public interface PaymentService {

	public PaymentDTO getPayment(PaymentDTO Payment);

	public List<PaymentDTO> getPaymentList(PaymentDTO params);
	
	public boolean registerPayment(PaymentDTO Payment);

}
