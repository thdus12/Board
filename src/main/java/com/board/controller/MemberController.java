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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.board.dto.member.MemberResponseDto;
import com.board.service.member.MemberDetailServiceImpl;
import com.board.dto.member.MemberRequestDto ;

import lombok.RequiredArgsConstructor;

/**
 * 사용자와 관련된 동작을 처리하는 컨트롤러
 */
@RequiredArgsConstructor
@Controller
public class MemberController {
	private final WebClient webClient;
	private final MemberDetailServiceImpl memberDetailServiceImpl;

	/**
	 * 사용자 계정을 생성하는 메소드
	 *
	 * @param email 사용자의 이메일
	 * @param password 사용자의 비밀번호
	 * @param model 뷰에 데이터를 전달하는데 사용되는 Model 객체
	 * @param request 클라이언트의 요청 정보를 담은 HttpServletRequest 객체
	 * @return 계정 생성 성공 시 로그인 페이지로 리다이렉션, 실패 시 계정 생성 실패 페이지를 반환
	 */
	@PostMapping("/member/signup")
	public String createUser(@RequestParam String email, 
	                         @RequestParam String password, 
	                         Model model, 
	                         HttpServletRequest request) {
	    // 이메일과 비밀번호를 담은 DTO 생성
	    MemberRequestDto memberRequestDto = new MemberRequestDto(email, password);

	    try {
	        // "/auth/member/signup" 엔드포인트에 POST 요청을 보냄
	        ResponseEntity<MemberResponseDto> responseEntity = webClient.post()
	                .uri("/auth/member/signup")
	                .bodyValue(memberRequestDto)
	                .retrieve()
	                .toEntity(MemberResponseDto.class)
	                .block();  // 비동기 작업을 동기적으로 수행

	        // 요청이 성공적인 경우 (HTTP 상태 코드가 CREATED인 경우)
	        if(responseEntity.getStatusCode() == HttpStatus.CREATED) {
	            // 로그인 페이지로 리다이렉션
	            return "/member/login";
	        }
	    } catch (Exception e) {
	        // 계정 생성 실패 시 예외 처리
	        e.printStackTrace();
	    }

	    // 계정 생성 실패 에러 메시지를 모델에 추가
	    model.addAttribute("error", "계정 생성에 실패했습니다.");
	    return "/member/signup";
	}
	
	/**
	 * 사용자 인증을 처리하는 메소드
	 *
	 * @param email 사용자의 이메일
	 * @param password 사용자의 비밀번호
	 * @param model 뷰에 데이터를 전달하는데 사용되는 Model 객체
	 * @param request 클라이언트의 요청 정보를 담은 HttpServletRequest 객체
	 * @return 인증 성공 시 게시판 목록 페이지로 리다이렉션, 실패 시 로그인 페이지를 반환
	 */
	@PostMapping("/login")
	public String authenticateUser(@RequestParam String email,
	                               @RequestParam String password,
	                               Model model,
	                               HttpServletRequest request) {
	    // 이메일과 비밀번호를 담은 DTO 생성
	    MemberRequestDto memberRequestDto = new MemberRequestDto(email, password);

	    // "/auth/login" 엔드포인트에 POST 요청을 보냄
	    ResponseEntity<MemberResponseDto> responseEntity;
	    try {
	        responseEntity = webClient.post()
	                .uri("/auth/login")
	                .bodyValue(memberRequestDto)
	                .retrieve()
	                .toEntity(MemberResponseDto.class)
	                .block();
	    } catch (WebClientResponseException ex) {
	        if (ex.getStatusCode() == HttpStatus.BAD_REQUEST) {
	            // 400 에러 발생 시 에러 메시지를 모델에 추가하여 로그인 페이지에서 표시
	            model.addAttribute("error", "로그인 실패");
	            return "/member/login";
	        } else {
	            // 기타 예외 상황 처리
	            model.addAttribute("exception", ex.getMessage());
	            return "/member/login";
	        }
	    }

	    HttpStatus status = responseEntity.getStatusCode();

	    // 요청이 성공적인 경우 (HTTP 상태 코드가 OK인 경우)
	    if (status == HttpStatus.OK) {
	        // 세션에 로그인한 사용자 정보를 저장
	        HttpSession session = request.getSession();
	        session.setAttribute("user", responseEntity.getBody());

	        // Spring Security에 사용자의 인증 정보를 알림
	        UserDetails userDetails = memberDetailServiceImpl.loadUserByUsername(memberRequestDto.getEmail());
	        Authentication authentication = new UsernamePasswordAuthenticationToken(
	                userDetails, null, userDetails.getAuthorities());
	        SecurityContextHolder.getContext().setAuthentication(authentication);

	        // 게시판 카테고리 페이지로 리다이렉션
	        return "redirect:/board/boardCategoryList";
	    } else {
	        // 로그인 페이지를 반환
	        return "/member/login";
	    }
	}
	/**
	 * 사용자를 로그아웃 시키는 메소드 로그아웃 후 로그인 페이지로 리다이렉션
	 *
	 * @param request 클라이언트의 요청 정보를 담은 HttpServletRequest 객체
	 * @param response 클라이언트에게 응답을 보내는데 사용되는 HttpServletResponse 객체
	 * @throws IOException I/O 예외가 발생했을 경우
	 */
	@GetMapping("/member/logout")
	public void logoutPage(HttpServletRequest request,
	                       HttpServletResponse response) throws IOException {
	    new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
	    response.sendRedirect("/member/login");
	}

	/**
	 * 회원 가입 페이지를 반환하는 메소드
	 *
	 * @param model 뷰에 데이터를 전달하는데 사용되는 Model 객체
	 * @return 회원 가입 페이지의 경로
	 */
	@GetMapping("/member/signup")
	public String getSignupPage(Model model) {
	    return "/member/signup";
	}

	/**
	 * 로그인 페이지를 반환하는 메소드 에러와 예외 메시지를 뷰에 전달
	 *
	 * @param model 뷰에 데이터를 전달하는데 사용되는 Model 객체
	 * @param error "error" 파라미터. 기본값은 null
	 * @param exception "exception" 파라미터. 기본값은 null
	 * @return 로그인 페이지의 경로
	 */
	@GetMapping("/login")
	public String getLoginPage(Model model,
	                           @RequestParam(value = "error", required = false) String error,
	                           @RequestParam(value = "exception", required = false) String exception) {
	    // 에러 메시지를 뷰에 전달
	    model.addAttribute("error", error);
	    // 예외 메시지를 뷰에 전달
	    model.addAttribute("exception", exception);
	    // 로그인 페이지를 반환
	    return "/member/login";
	}
}
