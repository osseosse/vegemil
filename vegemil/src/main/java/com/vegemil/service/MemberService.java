package com.vegemil.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vegemil.domain.MemberDTO;
import com.vegemil.mapper.MemberMapper;

@Service
public class MemberService implements UserDetailsService  {

	@Autowired
	private MemberMapper memberMapper;
	
	public int getMemberCount(MemberDTO params) {
		int memberTotalCount = memberMapper.selectMemberTotalCount(params);
		
		if(memberTotalCount < 0 ) {
			memberTotalCount = 0;
		}
		
		return memberTotalCount;
	}
	
	public String getDiKey(Long mIdx) {
		
		String diKey = null;
		diKey = memberMapper.selectMemDiKey(mIdx);
		if(diKey == null ) {
			diKey = "";
		}
		
		return diKey;
	}
	
	public int getMemberIdx(MemberDTO params) {
		
		int mIdx = memberMapper.getMemberIdx(params);
		if(mIdx < 0 ) {
			mIdx = 0;
		}
		
		return mIdx;
	}
	
	public int getMemberCountByEmail(MemberDTO params) {
		
		int mCnt = memberMapper.selectMemberCount(params);
		if(mCnt < 0 ) {
			mCnt = 0;
		}
		
		return mCnt;
	}
	
	public int overlappedID(MemberDTO member) throws Exception{
		int result = memberMapper.overlappedID(member);
		return result;
	}
	
	public String searchMemId(MemberDTO member) throws Exception{
		
		String mId = memberMapper.getMemId(member);
		
		return mId;
	}
	
	public MemberDTO getMember(Long mIdx) {
		
		MemberDTO member;
		member = memberMapper.selectMember(mIdx);

		return member;
	}
	
	public List<MemberDTO> getMemberList(MemberDTO params) {
		List<MemberDTO> memberList = Collections.emptyList();

		int memberTotalCount = memberMapper.selectMemberTotalCount(params);

		if (memberTotalCount > 0) {
			memberList = memberMapper.selectMemberList(params);
		}

		return memberList;
	}
	
	@Transactional
	public boolean resetPassword(MemberDTO member) {
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		int queryResult = 0;
		int memCount = 0;
		
		memCount = memberMapper.selectMemberCount(member);
		member.setMPwd(passwordEncoder.encode(member.getPassword()));
		
		if (memCount == 0) {
			queryResult = memberMapper.updateMemPwd(member);
		}

		return (queryResult == 1) ? true : false;
	}
	
	@Transactional
	public boolean registerMember(MemberDTO member) {
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		int queryResult = 0;
		int memCount = 0;
		
		memCount = memberMapper.selectMemberCount(member);
		member.setMPwd(passwordEncoder.encode(member.getPassword()));
		member.setMAuth("USER");
		
		
		if (memCount == 0) {
			queryResult = memberMapper.saveMember(member);
		} else {
			queryResult = memberMapper.updateMember(member);
		}

		return (queryResult == 1) ? true : false;
	}
	
	@Transactional
	public boolean registerComp(MemberDTO member) {
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		int queryResult = 0;
		int memCount = 0;
		
		memCount = memberMapper.selectMemberCount(member);
		member.setMPwd(passwordEncoder.encode(member.getPassword()));
		member.setMAuth("COMP");
		
		
		if (memCount == 0) {
			queryResult = memberMapper.saveMember(member);
		} else {
			queryResult = memberMapper.updateMember(member);
		}

		return (queryResult == 1) ? true : false;
	}
	
	@Transactional
	public boolean withdrawalMember(MemberDTO member) {
		
		int queryResult = 0;
		int memCount = 0;
		
		memCount = memberMapper.selectMemberCount(member);
		
		if (memCount != 0) {
			queryResult = memberMapper.deleteMember(member);
		}

		return (queryResult == 1) ? true : false;
	}
	
	public boolean activeMember(MemberDTO member) {
		
		int queryResult = 0;
		queryResult = memberMapper.selectMemberCount(member);
		
		if (queryResult != 0) {
			queryResult = memberMapper.activeMember(member);
		}

		return (queryResult == 1) ? true : false;
	}
	
	@Override
    public MemberDTO loadUserByUsername(String mId) throws UsernameNotFoundException {
        //여기서 받은 유저 패스워드와 비교하여 로그인 인증
		MemberDTO member = memberMapper.getMemberAccount(mId);
		int queryResult = 0;
        if (member == null){
            throw new UsernameNotFoundException("User not authorized.");
        } else {
        	queryResult = memberMapper.updateLastLogin(member);
        	if (queryResult == 0) {
        		throw new UsernameNotFoundException("error update last_login");
    		}
        }
        
        return member;
    }
	
	@Transactional
	public boolean wakeUpSleepMember(String mId) {
		
		int queryResult = 0;
		
		queryResult = memberMapper.updateSleepMemberToMemberTB(mId);
		
		if(queryResult != 0) {
			queryResult = memberMapper.deleteFromMemberSleep(mId);
		}
		return (queryResult == 1) ? true : false;
	}
	
	public MemberDTO getMemberSleepInfo(String mId) {
		return memberMapper.selectFromMemberSleep(mId);
	}
}
