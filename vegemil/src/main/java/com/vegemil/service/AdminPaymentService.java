package com.vegemil.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.vegemil.domain.DataTableDTO;
import com.vegemil.domain.PaymentDTO;

@Service
public interface AdminPaymentService {

	public DataTableDTO getPaymentList(Map<String, Object> paramMap);
	
	public PaymentDTO getPayment(String lgdTid);
	
	public boolean registerPayment(PaymentDTO Payment);
}
