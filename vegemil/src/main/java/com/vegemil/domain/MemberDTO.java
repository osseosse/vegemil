package com.vegemil.domain;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@SuppressWarnings("serial")
@Getter
@Setter
@Data
public class MemberDTO extends AdminCommonDTO implements UserDetails {

	private Long   mIdx;
	private String mId;
	private String mName;
	private String mPwd;
	private String mSex;
	private String mYear;
	private String mHp;
	private String mTel;
	private String mZipcode;
	private String mAddr1;
	private String mAddr2;
	private String mEmail;
	private String txtEmail;
	private String selEmail;
	private String mSmssend;
	private String mEmailsend;
	private String mJoindate;
	private int    mLogincount;
	private String mIp;
	private String mActive;
	private String mLeavedate;
	private String mLeavememo;
	private String mCybermonitor;
	private String mStipver;
	private Timestamp mLastLogindate;
	private String mCompName;
	private String mBizno;
	private String mType;
	private String mGreenbiaPay;
	private String mTermsOk;
	private String mAuth;		
	private String mReceiveModifydate;		
	private String mIdleDate;		
	private String mRecoveryDate;	
	private String mIsIdle;
	private String mDualYn;
	private String mDi;
	private String mLocation;
	
	//유입
	private Long mAge;
	private Long man;
	private Long women;
	private Long mCount;
	
	private Long total;
	private Long live;
	private Long sleep;
	private Long comp;

	@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(this.mAuth));
    }

    @Override
    public String getPassword() {
        return this.mPwd;
    }

    // 시큐리티의 userName
    // -> 따라서 얘는 인증할 때 id를 봄
    @Override
    public String getUsername() {
    	
        //return this.mEmail;
    	return this.mId;
    }

    // Vo의 userName !
    public String getUserName(){
        return this.mName;
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
