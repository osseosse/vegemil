package com.vegemil.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.vegemil.service.MemberService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MemberService memberService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                //.antMatchers("/member/**").authenticated()
                //.antMatchers("/application/**").authenticated()
            	//.antMatchers("/**").permitAll()
            	//.antMatchers("/mypage/qna").permitAll()
                //.antMatchers("/member/**").hasAuthority("USER") // user 권한의 유저만  접근가능
                //.antMatchers("/application/**").hasAuthority("USER") // user 권한의 유저만  접근가능
                //.antMatchers("/mypage/**").hasAuthority("USER") // user 권한의 유저만  접근가능
                //.antMatchers("/recruit/join", "/recruit/register").hasAuthority("USER") // user 권한의 유저만  접근가능
                .antMatchers("/**").permitAll()
            .anyRequest()
                .authenticated()
                .and().csrf().disable()
            .formLogin()
                .loginPage("/member/login")
                .loginProcessingUrl("/member/loginProc")
                .failureUrl("/member/login?error=true")
                .defaultSuccessUrl("/")
            .and()
                .logout()
	                .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout")) // 로그아웃 시 URL 재정의
	                .logoutSuccessUrl("/") // 로그아웃 성공 시 redirect 이동
	                .invalidateHttpSession(true) // HTTP Session 초기화
	                .deleteCookies("JSESSIONID") // 특정 쿠키 제거
            .and()
                .exceptionHandling()
                .accessDeniedPage("/accessDenied");
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(memberService).passwordEncoder(new BCryptPasswordEncoder());
    }

}