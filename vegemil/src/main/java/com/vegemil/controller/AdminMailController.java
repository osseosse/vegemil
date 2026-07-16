package com.vegemil.controller;

import com.vegemil.domain.MemberDTO;
import com.vegemil.service.MailService;
import com.vegemil.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminMailController {

    @Autowired
    private MailService mailService;

    @Autowired
    private MemberService memberService;

    @PostMapping("/vegemail/marketing/agreement/notice/{mIdx}")
    public void sendMarketingAgreementNotice(@PathVariable("mIdx") Long mIdx){
        MemberDTO member = memberService.getMember(mIdx);
        mailService.marketingInfoNoticePer2year(member);
    }
}
