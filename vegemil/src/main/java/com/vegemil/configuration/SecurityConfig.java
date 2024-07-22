package com.vegemil.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.vegemil.handler.CustomAuthenticationHandler;
import com.vegemil.service.MemberService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
    private MemberService memberService;
	
    @Autowired
    private CustomAuthenticationHandler customAuthenticationHandler;
    
    @Order(1)
    @Configuration
    public class AdminSecurityConfig extends WebSecurityConfigurerAdapter {
    
	    @Override
	    protected void configure(HttpSecurity http) throws Exception {
	        http
	        	.antMatcher("/admin/manage/**/**")
	            	.authorizeRequests().anyRequest().hasAuthority("ADMIN")
	            .and()
	            	.csrf().disable()
		            .formLogin()
		                .loginPage("/admin/auth/login")
		                .loginProcessingUrl("/member/loginProc")
		                .failureUrl("/admin/auth/login?error=true")
		                .defaultSuccessUrl("/admin/manage")
	            .and()
	                .logout()
		                .logoutRequestMatcher(new AntPathRequestMatcher("/admin/logout")) // 로그아웃 시 URL 재정의
		                .logoutSuccessUrl("/") // 로그아웃 성공 시 redirect 이동
		                .invalidateHttpSession(true) // HTTP Session 초기화
		                .deleteCookies("JSESSIONID") // 특정 쿠키 제거
	            .and()
	                .exceptionHandling()
	                .accessDeniedPage("/error/error");
	                
		}
	    
	    @Override
	    public void configure(AuthenticationManagerBuilder auth) throws Exception {
	        auth.userDetailsService(memberService).passwordEncoder(new BCryptPasswordEncoder());
	    }
    
    }

    @Order(2)
    @Configuration
    public class PaymentSecurityConfig extends WebSecurityConfigurerAdapter {
    
	    @Override
	    protected void configure(HttpSecurity http) throws Exception {
	        http
	        	.antMatcher("/comp/payment/**")
	            	.authorizeRequests().anyRequest().hasAuthority("COMP")
	            .and()
	            	.csrf().disable()
		            .formLogin()
		                .loginPage("/member/payLogin")
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
	                .accessDeniedPage("/error/error");
	    }
	    
	    @Override
	    public void configure(AuthenticationManagerBuilder auth) throws Exception {
	        auth.userDetailsService(memberService).passwordEncoder(new BCryptPasswordEncoder());
	    }
    
    }
    
    @Order(3)
    @Configuration
    public class CommonSecurityConfig extends WebSecurityConfigurerAdapter {
        
    	@Autowired
    	@Qualifier("vegemilDataSource")
	    private DataSource dataSource;
    	
	    @Override
	    protected void configure(HttpSecurity http) throws Exception {
	        http	        	
	            .authorizeRequests()
	                .antMatchers("/mypage/**").authenticated()
					.antMatchers("/vegemilBaby/sample/form").authenticated() 
	                .antMatchers("/rnd/factoryTour").authenticated()
	                .antMatchers("/rnd/tourApply").authenticated()
	                .antMatchers("/rnd/reviewWrite").authenticated()
	            .anyRequest()
	            	.permitAll()
	            .and().headers().frameOptions().sameOrigin()
	            .and()
	            	.csrf().disable()
		            .formLogin()
		                .loginPage("/member/login")
		                .loginProcessingUrl("/member/loginProc")
		                .successHandler(customAuthenticationHandler) // 커스텀 핸들러 설정
		                .failureHandler(customAuthenticationHandler) // 커스텀 핸들러 설정
		                //.failureUrl("/member/login?error=true")
		                //.defaultSuccessUrl("/")
	            .and()
	                .logout()
		                .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout")) // 로그아웃 시 URL 재정의
		                .logoutSuccessUrl("/") // 로그아웃 성공 시 redirect 이동
		                .invalidateHttpSession(true) // HTTP Session 초기화
		                .deleteCookies("remember-me", "JSESSIONID") //자동 로그인 쿠키, Tomcat이 발급한 세션 유지 쿠키 삭제
	            .and()
	                .exceptionHandling()
	                .accessDeniedPage("/error/error")
		        .and()
		            .rememberMe()
		            .key("dcf") //쿠키에 사용되는 값을 암호화하기 위한 키(key)값
		            .rememberMeParameter("remember-me")
		            .tokenRepository(persistentTokenRepository()) //DataSource 추가
		            .tokenValiditySeconds(604800) //토큰 유지 시간(초단위) - 일주일
		        .and().headers().frameOptions().disable()
		        .addHeaderWriter(new StaticHeadersWriter("X-Frame-Options", "ALLOW-FROM https://recruit.vegemil.co.kr/"));
	    }
    
	    @Override
	    public void configure(AuthenticationManagerBuilder auth) throws Exception {
	        auth.userDetailsService(memberService).passwordEncoder(new BCryptPasswordEncoder());
	    }
	    
	    // tokenRepository의 구현체
	    @Bean
	    public PersistentTokenRepository persistentTokenRepository() {
	    	JdbcTokenRepositoryImpl repo = new JdbcTokenRepositoryImpl();
	    	repo.setDataSource(dataSource);
	    	return repo;
	    }
    }
}