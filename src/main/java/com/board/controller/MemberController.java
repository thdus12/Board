package com.board.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;

import com.board.dto.member.MemberResponseDto;
import com.board.service.member.MemberDetailServiceImpl;
import com.board.dto.member.MemberRequestDto ;

import lombok.RequiredArgsConstructor;

// 회원 컨트롤러 클래스
@RequiredArgsConstructor
@Controller
public class MemberController {
	private final WebClient webClient;
	private final MemberDetailServiceImpl memberDetailServiceImpl;

	@PostMapping("/member/signup")
    public String createUser(@RequestParam String email, 
						     @RequestParam String password, 
						     Model model, 
						     HttpServletRequest request) {
		MemberRequestDto memberRequestDto = new MemberRequestDto(email, password);
		
        ResponseEntity<MemberResponseDto> responseEntity = webClient.post()
                .uri("/auth/member/signup")  // API endpoint
                .bodyValue(memberRequestDto)
                .retrieve()
                .toEntity(MemberResponseDto.class)
                .block();  // 비동기 작업을 동기적으로 수행
        
        System.out.println("@@@@@@responseEntity.getStatusCode()="+responseEntity.getStatusCode());
        
        if(responseEntity.getStatusCode() == HttpStatus.CREATED) {
            return "/member/login";
        } else {
            return "/member/failsignup";
        }
    }
	
	@PostMapping("/login")
	public String authenticateUser(@RequestParam String email, 
								   @RequestParam String password, 
								   Model model, 
								   HttpServletRequest request) {
	    MemberRequestDto memberRequestDto = new MemberRequestDto(email, password);
		
		ResponseEntity<MemberResponseDto> responseEntity = webClient.post()
	        .uri("/auth/login")
	        .bodyValue(memberRequestDto)
	        .retrieve()
	        .toEntity(MemberResponseDto.class)
	        .block();

	    if(responseEntity.getStatusCode() == HttpStatus.OK) {
	        // 로그인에 성공한 사용자 정보를 세션에 저장
	        HttpSession session = request.getSession();
	        session.setAttribute("user", responseEntity.getBody());

	        // Spring Security에 인증 정보를 알려주기
	        UserDetails userDetails = memberDetailServiceImpl.loadUserByUsername(memberRequestDto.getEmail());
	        Authentication authentication = new UsernamePasswordAuthenticationToken(
	            userDetails, null, userDetails.getAuthorities());
	        SecurityContextHolder.getContext().setAuthentication(authentication);

	        return "redirect:/board/list";
	    } else {
	        return "/member/login";
	    }
	}
	
	@GetMapping("/member/logout")
    public void logoutPage(HttpServletRequest request,HttpServletResponse response) throws IOException {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        response.sendRedirect("/member/login");
    }
	
	@GetMapping("/member/signup")
	public String getSignupPage(Model model) {
		return "/member/signup";
    }

    // 로그인 페이지를 반환하는 메소드
    @GetMapping("/login")
    public String getLoginPage(Model model, // 뷰에 데이터를 전달하는 Model 객체
						       // "error" 파라미터를 선택적으로 받아옵니다. 기본값은 null입니다.
						       @RequestParam(value = "error", required = false) String error,
						       // "exception" 파라미터를 선택적으로 받아옵니다. 기본값은 null입니다.
						       @RequestParam(value = "exception", required = false) String exception) {
        // 에러 메시지를 뷰에 전달
        model.addAttribute("error", error);
        // 예외 메시지를 뷰에 전달
        model.addAttribute("exception", exception);
        // "/member/login" 뷰를 반환
        return "/member/login";
    }
}
