package com.vegemil.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vegemil.domain.AdminDTO;
import com.vegemil.domain.MemberDTO;
import com.vegemil.domain.ProductDTO;
import com.vegemil.domain.UrlDTO;
import com.vegemil.domain.UserAgentDTO;
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
        	member.setMAuth("USER");       	
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
    
    public Map<String, Object> getMainInformation() {
    	
    	Map<String, Object> hs = new HashMap<>();
    	
    	String yymm = "";
    	LocalDate now = LocalDate.now();
    	yymm = now.toString().substring(2, 7);
    	
    	//1. 회원수
    	MemberDTO memCount = adminMapper.selectMemCount();
    	hs.put("memCount", memCount);
    	
    	//2. 성비
    	List<MemberDTO> sexRate = new ArrayList<>();
    	sexRate = adminMapper.selectSexRate();
    	hs.put("sexRate", sexRate);
    	
    	//3. 가입자수
    	List<MemberDTO> monthlyJoinCount = new ArrayList<>();
    	monthlyJoinCount = adminMapper.selectMonthlyJoinCount();
    	hs.put("monthlyJoinCount", monthlyJoinCount);
    	
    	//4. 모바일 기기별 월 유입
    	List<UserAgentDTO> monthlyMobileAgent = new ArrayList<>();
    	monthlyMobileAgent = adminMapper.selectMonthlyMobileAgent();
    	hs.put("monthlyMobileAgent", monthlyMobileAgent);
    	
    	//5. 디바이스별 유입 월
    	UserAgentDTO userAgent = new UserAgentDTO();
    	userAgent = adminMapper.selectUserAgent(yymm);
    	hs.put("userAgent", userAgent);
    	
    	//6. 조회수 상위페이지
    	List<UrlDTO> urlList = adminMapper.selectUrlList();
    	hs.put("urlList", urlList);
    	
    	//7. 조회수 상위 제품
		List<ProductDTO> productList = adminMapper.selectProductList();
		hs.put("productList", productList);
    	
    	
    	return hs;
    	
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
    
	@Transactional
	public boolean updateAgentCount(Map<String, Object> params) {
		int queryResult = 0;
		
		queryResult = adminMapper.updateAgentCount(params);

		return (queryResult == 1) ? true : false;
	}
	
	@Transactional
	public boolean registerUrl(String url, String title, String table) {
		int queryResult = 0;
		
		int result = adminMapper.selectUrlCount(url, table);
		
		if(result == 0) {
			queryResult = adminMapper.insertUrl(url, title, table);
		} else {
			queryResult = adminMapper.updateUrl(url, title, table);
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