package com.vegemil.service;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.vegemil.domain.AdminDTO;
import com.vegemil.domain.MemberDTO;
import com.vegemil.mapper.AdminMapper;

@Service
public class AdminService implements UserDetailsService {
	
	@Autowired
	private AdminMapper adminMapper;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	/**
	 * result : 0(아이디 중복)
	 * result : 1(저장 성공)
	 * result : 2(저장 실패)
	 */
    public Integer joinAdmin(MemberDTO member) {
    	
        // 비밀번호 암호화
    	member.setMPwd(passwordEncoder.encode(member.getMPwd()));
        
        int queryResult = 0;
        
        MemberDTO loginUser = adminMapper.findAdminById(member.getMId());
        
        if(loginUser == null) {
        	member.setMAuth("ADMIN");
        	queryResult = adminMapper.insertAdmin(member);
    		if(queryResult == 1) {
    			return 1;
    		} else {
    			return 2;
    		}
        }else {
        	return 0;
        }
    }
    
    public Map<String, Object> validationLogin(MemberDTO member) {
    	MemberDTO loginUser = adminMapper.findAdminById(member.getMId());
    	Map<String, Object> params = new LinkedHashMap<>();

       if(loginUser==null) {
    	   params.put("result", 1);
    	   return params;
       }

       if(!passwordEncoder.matches(member.getMPwd(), loginUser.getMPwd())) {
    	   params.put("result", 2);
    	   return params;
       }
       
       params.put("result", 0);
       params.put("admin", loginUser);
       return params;
    }
    
    public MemberDTO getAdmin(String aId) {
    	MemberDTO loginUser = adminMapper.findAdminById(aId);
    	
    	return loginUser;
    }
    
    public boolean initPwd(MemberDTO member) {
    	int result = adminMapper.checkMember(member);
    	if(result > 0) {
    		String password = "abcd1234";
    		member.setMPwd(passwordEncoder.encode(password));
    		result = adminMapper.updateAdminPwd(member);
    		if(result > 0) {
    			return true;
    		}
    	}
    	
    	return false;
    }
    
    public boolean changePwd(MemberDTO member) {
    	MemberDTO loginUser = adminMapper.findAdminById(member.getMId());
    	int result = 0; 

       if(!passwordEncoder.matches(member.getMPwd(), loginUser.getMPwd())) {
    	   member.setMPwd(passwordEncoder.encode(member.getMPwd()));
    	   result = adminMapper.updateAdminPwd(member);
   		   if(result > 0) {
   			   return true;
   		   }
       }
       
       return false;
    }
    
    public boolean activeMember(MemberDTO member) {
		
		int queryResult = 0;
		queryResult = adminMapper.selectAdminCount(member);
		
		if (queryResult != 0) {
			queryResult = adminMapper.activeAdmin(member);
		}

		return (queryResult == 1) ? true : false;
	}

	@Override
	public UserDetails loadUserByUsername(String aId) throws UsernameNotFoundException {
		//여기서 받은 유저 패스워드와 비교하여 로그인 인증
		MemberDTO admin = adminMapper.findAdminById(aId);
        if (admin == null){
            throw new UsernameNotFoundException("User not authorized.");
        }
        return admin;
	}

}