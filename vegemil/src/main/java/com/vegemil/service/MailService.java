package com.vegemil.service;

import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.vegemil.domain.AdminDTO;
import com.vegemil.domain.MailDTO;
import com.vegemil.domain.MemberDTO;
import com.vegemil.domain.PaymentDTO;
import com.vegemil.mapper.AdminMapper;
import com.vegemil.util.RedisUtil;

import javassist.NotFoundException;


@Service
@AllArgsConstructor
public class MailService {
    private JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;
    private final RedisUtil redisUtil;
    private static final String FROM_ADDRESS = "정식품 관리자 <webadmin@vegemil.co.kr>";
    
    @Autowired
	private AdminMapper adminMapper;

    public void mailSendOrigen(MailDTO mailDto) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailDto.getAddress());
        message.setFrom(MailService.FROM_ADDRESS);
        message.setSubject(mailDto.getTitle());
        message.setText(mailDto.getMessage());

        mailSender.send(message);
    }
    
    public void mailSend(MemberDTO member) {
    	
    	try {
	    	MimeMessage message = mailSender.createMimeMessage();
	    	message.setFrom(MailService.FROM_ADDRESS);
			message.addRecipients(MimeMessage.RecipientType.TO, "ys9331@vegemil.co.kr");
	        message.setSubject("관리자 회원인증");
	        message.setText(setAuthContext(member), "utf-8", "html");
	        mailSender.send(message);
    	}catch(MessagingException e) {
    		e.printStackTrace();
    	}
    }
    
    public String setAuthContext(MemberDTO member) {
    	Context context = new Context();
    	try {
			
    		UUID uuid = UUID.randomUUID();
            context.setVariable("member", member);
    		
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return templateEngine.process("admin/email/confirmMail", context);
    }
    
    public void mailSendGreenbiaCancel(PaymentDTO payment) {
    	
    	MimeMessage message = mailSender.createMimeMessage();
    	Context context = new Context();
    	
    	try {
    		
    		context.setVariable("lgdTid", payment.getLgdTid());
			context.setVariable("lgdBuyer", payment.getLgdBuyer());
			context.setVariable("lgdAmount", payment.getLgdAmount());
			context.setVariable("insertDate", payment.getInsertDate());
    		
	    	message.setFrom(MailService.FROM_ADDRESS);
			message.addRecipients(MimeMessage.RecipientType.TO, "healthsalesteam@vegemil.co.kr");
			//message.addRecipients(MimeMessage.RecipientType.TO, "kid4290@vegemil.co.kr");
			message.setSubject("[그린비아] 결제 취소요청 메일");
			message.setText(templateEngine.process("email/greenbiaEmail", context), "utf-8", "html"); 
	        mailSender.send(message);
        
    	} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    public String sendPwResetEmail(MemberDTO member) {
    	
        try {
        	
        	MimeMessage message = mailSender.createMimeMessage();
        	message.setFrom(MailService.FROM_ADDRESS);
			message.addRecipients(MimeMessage.RecipientType.TO, member.getMEmail());
			message.setSubject("[정식품] 비밀번호 재설정 메일");
			message.setText(setPwContext(member), "utf-8", "html"); 
	        mailSender.send(message);
	        
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return member.getMEmail();
        
    }
    
    private String setPwContext(MemberDTO member) {
	
		Context context = new Context();
		try {
			
			UUID uuid = UUID.randomUUID();
	        // redis에 회원 mIdx 정보 저장
	        redisUtil.setDataExpire(uuid.toString(), String.valueOf(member.getMIdx()), 7);
	        context.setVariable("authToken", uuid.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return templateEngine.process("email/resetPwMail", context);
    
    }

	public String verifyMemberIdx(String key) throws NotFoundException {
		
	    String mIdx = redisUtil.getData(key);
	    if(mIdx == null || mIdx.equals("")) { 
	    	throw new NotFoundException("유효하지 않은 링크입니다.");
	    }
	    redisUtil.deleteData(key);
	    
	    return mIdx;
	}

	public boolean verifyEmail(MemberDTO member) throws NotFoundException {
		int queryResult = adminMapper.selectAdminCount(member);
			
		if (queryResult != 0) {
			queryResult = adminMapper.activeAdmin(member);
		}
	
		return (queryResult == 1) ? true : false;
	    
	}
    
}