package com.board.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import lombok.RequiredArgsConstructor;

/**
 * Spring Security 설정을 담당하는 클래스
 */
@Configurable
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	/**
     * HttpSecurity를 이용하여 보안 설정을 구성합니다.
     *
     * @param http HttpSecurity 객체
     * @throws Exception 예외 처리
     */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	    http.csrf().disable() 	 // CSRF 토큰 비활성화
	        .authorizeRequests() // 요청 URL에 따라 접근 권한 설정
	            .antMatchers("/", "/auth/**", "/board/**", "/login/**", "/member/**", "/js/**", "/css/**", "/image/**").permitAll() // 해당 경로들은 접근 허용
	            .anyRequest().authenticated()
	        .and()
	        .formLogin()
            	.disable()
	        .logout() // 로그아웃 설정
	            .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // 로그아웃 URL
	            .logoutSuccessUrl("/login")  // 로그아웃 성공시 반환 URL
	            .invalidateHttpSession(true) // 세션 무효화
	            .deleteCookies("JSESSIONID", "remember-me") // 쿠키 삭제
	            .permitAll()
	        .and()
	        .sessionManagement() 	// 세션 관리 설정
	            .maximumSessions(1) // 최대 허용 세션 수 (1)
	            .maxSessionsPreventsLogin(false) // 동시 로그인 방지 기능 비활성화
	            .expiredUrl("/login?error=true&exception=Have been attempted to login from a new place. or session expired") // 세션이 만료된 경우 이동할 URL
	        .and()
	        .and().rememberMe() 			 // 로그인 유지 설정
	            .alwaysRemember(false) 		 // 항상 기억할지 여부
	            .tokenValiditySeconds(43200) // 토큰 유효 시간 (43200초, 12시간)
	            .rememberMeParameter("remember-me")
	        .and();
	}
}