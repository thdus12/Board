package com.board.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.board.dto.member.MemberResponseDto;
import com.board.dto.member.MemberRequestDto ;
import com.board.service.member.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// 회원 컨트롤러 클래스
@RequiredArgsConstructor
@Controller
@Slf4j
public class MemberController {
	private final MemberService memberService;
	
	@PostMapping("/member/signup")
	public String createUser(MemberRequestDto memberRequestDto) {
		MemberResponseDto memberResponseDto  = memberService.createUser(memberRequestDto);
		if(memberResponseDto == null){
	        return "/member/failsignup";
	    }else{
			System.out.println(memberResponseDto.toString());
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
    public String getLoginPage(
        Model model, // 뷰에 데이터를 전달하는 Model 객체
        // "error" 파라미터를 선택적으로 받아옵니다. 기본값은 null입니다.
        @RequestParam(value = "error", required = false) String error,
        // "exception" 파라미터를 선택적으로 받아옵니다. 기본값은 null입니다.
        @RequestParam(value = "exception", required = false) String exception
    ) {
        // 에러 메시지를 뷰에 전달
        model.addAttribute("error", error);
        // 예외 메시지를 뷰에 전달
        model.addAttribute("exception", exception);
        // "/member/login" 뷰를 반환
        return "/member/login";
    }
}
