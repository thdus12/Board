package com.board.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.board.handler.AuthFailureHandler;
import com.board.handler.AuthSucessHandler;
import com.board.jwt.JwtAuthenticationFilter;
import com.board.jwt.JwtTokenProvider;
import com.board.service.member.MemberDetailServiceImpl;

import lombok.RequiredArgsConstructor;

@Configurable
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private final JwtTokenProvider jwtTokenProvider;
	private final MemberDetailServiceImpl memberDetailServiceImpl;
	private final AuthSucessHandler authSucessHandler;
	private final AuthFailureHandler authFailureHandler;
	
	// JWT 인증 필터 빈을 생성하는 메서드
	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
	    // JwtAuthenticationFilter 객체 생성 및 초기화
		JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager(), jwtTokenProvider, memberDetailServiceImpl);
        jwtAuthenticationFilter.setFilterProcessesUrl("/login/**");
        return jwtAuthenticationFilter;
    }
	
	// BCryptPasswordEncoder는 Spring Security에서 제공하는 비밀번호 암호화 객체 (BCrypt라는 해시 함수를 이용하여 패스워드를 암호화 한다.)
	// 회원 비밀번호 등록시 해당 메서드를 이용하여 암호화해야 로그인 처리시 동일한 해시로 비교한다.
	@Bean
	public BCryptPasswordEncoder encryptPassword() {
	    return new BCryptPasswordEncoder();
	}
	  
	// 시큐리티가 로그인 과정에서 password를 가로챌때 해당 해쉬로 암호화해서 비교한다.
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	    auth.userDetailsService(memberDetailServiceImpl).passwordEncoder(encryptPassword());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	    http.csrf().disable() 	 // CSRF 토큰 비활성화
	        .authorizeRequests() // 요청 URL에 따라 접근 권한 설정
	            .antMatchers("/", "/auth/**", "/login/**", "/member/**", "/js/**", "/css/**", "/image/**").permitAll() // 해당 경로들은 접근 허용
	            .anyRequest().authenticated()
	        .and()
//	        .formLogin() 			 // 로그인 설정
//	            .loginPage("/login") // 로그인 페이지 경로
//	            .loginProcessingUrl("/login/action") // 로그인 액션 처리 URL
//	            .successHandler(authSucessHandler)   // 로그인 성공 핸들러 jwt 토큰 생성
//	            .failureHandler(authFailureHandler)  // 로그인 실패 핸들러
//	        .and()
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
	        .and()
	        // 첫 번째 인자로 추가할 필터 객체를 지정하고, 
	        // 두 번째 인자로 해당 필터보다 앞에 위치할 필터의 클래스를 지정함
	        // 필터 체인에서 JwtAuthenticationFilter는 
	        // 다른 인증과 관련된 필터보다 먼저 호출되어 JWT 토큰을 검증하고 사용자를 인증함
	        .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
	}

}