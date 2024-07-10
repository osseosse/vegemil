package com.vegemil.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.vegemil.domain.ClaimDTO;
import com.vegemil.domain.MailDTO;
import com.vegemil.domain.MemberDTO;
import com.vegemil.domain.PaymentDTO;
import com.vegemil.mapper.AdminMapper;
import com.vegemil.util.RedisUtil;

import javassist.NotFoundException;
import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
public class MailService {
    private JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;
    private final RedisUtil redisUtil;
    private static final String FROM_ADDRESS = "정식품 관리자 <webadmin@vegemil.co.kr>";
    
    @Autowired
	private AdminMapper adminMapper;
    
    @Autowired
    private EdayVempService edayVempService;

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
			message.addRecipients(MimeMessage.RecipientType.TO, "hypark023@osse.co.kr");
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

	public boolean verifyEmail(String email) throws NotFoundException {
		MimeMessage message = mailSender.createMimeMessage();
    	Context context = new Context();    	
    	try {    	
	    	message.setFrom(MailService.FROM_ADDRESS);
			message.addRecipients(MimeMessage.RecipientType.TO, email);
			//message.addRecipients(MimeMessage.RecipientType.TO, "kid4290@vegemil.co.kr");
			message.setSubject("[정식품] 관리자 권한 부여 완료하였습니다.");
			message.setText("관리자 권한 부여 완료하였습니다. 관리자 계정 아이디는 기입하신 이메일 주소입니다."); 
	        mailSender.send(message);
        
    	} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
    	
    	return true;
	}
	
	// 묹자 메일 마케팅 정보 수신 동의 메일 
    public void marketingInfoReceiveAgreeConfirm(MemberDTO member) {
    	
    	MimeMessage message = mailSender.createMimeMessage();
    	Context context = new Context();
    	
    	try {
    		SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일 a HH:mm:ss");
    		context.setVariable("agreeDate", sdf.format(new Date()));
    		context.setVariable("mName", member.getMName());
			context.setVariable("mSmssend", member.getMSmssend());
			context.setVariable("mEmailsend", member.getMEmailsend());
    		
	    	message.setFrom(MailService.FROM_ADDRESS);
			message.addRecipients(MimeMessage.RecipientType.TO, member.getMEmail());
			message.setSubject("[정식품] 마케팅 수신 동의 확인");
			message.setText(templateEngine.process("admin/email/agreeReceiveMarketingInfoConfirmMail", context), "utf-8", "html"); 
	        mailSender.send(message);
        
    	} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    // 비밀번호 변경 성공 안내 
    public void confirmPasswordChange(MemberDTO member) {
    	
    	MimeMessage message = mailSender.createMimeMessage();
    	Context context = new Context();
    	
    	try {
    		context.setVariable("mName", member.getMName());
    		context.setVariable("mEmail", member.getMEmail());
    		context.setVariable("mHp", member.getMHp());
    		
    		message.setFrom(MailService.FROM_ADDRESS);
    		message.addRecipients(MimeMessage.RecipientType.TO, member.getMEmail());
    		message.setSubject("[정식품] 새로운 비밀번호로 변경이 완료되었습니다.");
    		message.setText(templateEngine.process("admin/email/confirmPasswordChange", context), "utf-8", "html"); 
    		mailSender.send(message);
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
    
    // 불공정 거래 신고시 관리자 퇴직 알림 메일
    public void requestCheckPersonInCharge() {
    	
    	SimpleMailMessage messageText = new SimpleMailMessage();

    	try {
    		String[] addressList = new String[] {"cs@vegemil.co.kr","jade@osse.co.kr","informngdept@vegemil.co.kr"}; 
    		//String[] addressList = new String[] {"hypark023@gmail.com", "hypark023@osse.co.kr"}; 테스트용  
	        messageText.setTo(addressList);
			messageText.setFrom(MailService.FROM_ADDRESS);
			messageText.setSubject("[정식품] 불공정 거래행위 신고 처리 담당자 확인 요청의 건");
			messageText.setText("\n\n\n기존 cp신고 처리 담당자가 퇴직상태로 조회됩니다. 새로운 담당자 확인 후 유관부서에 공유 부탁드립니다.");
    		mailSender.send(messageText);
    		
    	} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    // 불공정 거래 신고시 관리자 메일 발송 
    public void alertSubmitCpDecl(ClaimDTO claimDTO, List<String> recipientsId) {
    	
    	MimeMessage message = mailSender.createMimeMessage();
    	Context context = new Context();
    	recipientsId.add("cs@vegemil.co.kr"); 
    	
    	try {
	    		MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
				
				String[] receiveList = recipientsId.toArray(new String[recipientsId.size()]);
		        messageHelper.setTo(receiveList);
		        
		        context.setVariable("cYear", claimDTO.getCWritedate().split("-")[0]);
		        context.setVariable("cMonth", claimDTO.getCWritedate().split("-")[1]);
		        context.setVariable("cDay", claimDTO.getCWritedate().split("-")[2]);
    			context.setVariable("cName", claimDTO.getCName());
    			context.setVariable("cHp", claimDTO.getCHp());
    			context.setVariable("cTel", claimDTO.getCTel());
    			context.setVariable("cEmail", claimDTO.getCEmail());
    			context.setVariable("cSubject", claimDTO.getCSubject());
    			context.setVariable("cContent", claimDTO.getCContent());
        		
        		message.setFrom(MailService.FROM_ADDRESS);
        		message.setSubject("[정식품] 불공정거래행위 상담이 접수되었습니다.");
        		message.setText(templateEngine.process("admin/email/cpComplainAlert", context), "utf-8", "html"); 
        		mailSender.send(message);
    		
    	} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    //불공정 거래 신고시 신고인 확인 메일 발송 
    public void confirmSubmitCpDecl(ClaimDTO claimDTO) {
    	
    	MimeMessage message = mailSender.createMimeMessage();
    	Context context = new Context();
    	
    	try {
    			context.setVariable("cYear", claimDTO.getCWritedate().split("-")[0]);
    			context.setVariable("cMonth", claimDTO.getCWritedate().split("-")[1]);
    			context.setVariable("cDay", claimDTO.getCWritedate().split("-")[2]);
    			context.setVariable("cName", claimDTO.getCName());
    			context.setVariable("cHp", claimDTO.getCHp());
    			context.setVariable("cTel", claimDTO.getCTel());
    			context.setVariable("cEmail", claimDTO.getCEmail());
    			context.setVariable("cSubject", claimDTO.getCSubject());
    			context.setVariable("cContent", claimDTO.getCContent());
    			
    			message.setFrom(MailService.FROM_ADDRESS);
    			message.setSubject("[정식품] 불공정거래행위 상담이 접수되었습니다.");
    			message.setText(templateEngine.process("admin/email/confirmCpComplain", context), "utf-8", "html"); 
    			message.addRecipients(MimeMessage.RecipientType.TO, claimDTO.getCEmail());
    			mailSender.send(message);
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
}