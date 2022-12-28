package com.vegemil.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
public class AdminDTO implements UserDetails {

	private Long   idNo;		/** 번호  */
	private String aId;			/** 아이디 */
	private String aName;		/** 사원명 */
	private String aPwd;		/** 비밀번호 */
	private String aClass;		/** 사원등급 */
	private String aLocation;   /** 부서명 */
	private String aAuth;
	private String aRole;

	@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        return authorities;
//        return Collections.singletonList(new SimpleGrantedAuthority(this.aAuth));
    }

    @Override
    public String getPassword() {
        return this.aPwd;
    }

    // 시큐리티의 userName
    // -> 따라서 얘는 인증할 때 id를 봄
    @Override
    public String getUsername() {
        return this.aId;
    }

    // Vo의 userName !
    public String getUserName(){
        return this.aName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
