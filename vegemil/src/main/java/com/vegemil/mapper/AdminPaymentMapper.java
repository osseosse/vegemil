package com.vegemil.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.vegemil.domain.AdminBabyDTO;
import com.vegemil.domain.AdminBestReviewDTO;
import com.vegemil.domain.AdminCalendarModelDTO;
import com.vegemil.domain.AdminSampleBabyDTO;
import com.vegemil.domain.PaymentDTO;

@Mapper
public interface AdminPaymentMapper {
	
	public List<PaymentDTO> selectPaymentList(Map<String, Object> paramMap);
	
	public PaymentDTO selectPayment(String lgdTid);
	
	public int selectPaymentTotalCount(Map<String, Object> paramMap);
	
	public int savePayment(PaymentDTO params);
	
}
