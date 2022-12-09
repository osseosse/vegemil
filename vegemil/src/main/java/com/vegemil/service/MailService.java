package com.vegemil.service;

import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.vegemil.domain.MailDTO;
import com.vegemil.domain.MemberDTO;
import com.vegemil.util.RedisUtil;

import javassist.NotFoundException;


@Service
@AllArgsConstructor
public class MailService {
    private JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;
    private final RedisUtil redisUtil;
    private static final String FROM_ADDRESS = "정식품 <dcfrecruit@vegemil.co.kr>";

    public void mailSend(MailDTO mailDto) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailDto.getAddress());
        message.setFrom(MailService.FROM_ADDRESS);
        message.setSubject(mailDto.getTitle());
        message.setText(mailDto.getMessage());

        mailSender.send(message);
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
    
}