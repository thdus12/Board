package com.board.web;

import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.board.dto.member.MemberDto;
import com.board.entity.board.member.MemberRepository;

import java.util.*;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ViewController {
    private final MemberRepository memberRepository;
    @RequestMapping("/member/login")
    String loginView(){
        return "/member/login";
    }

    @RequestMapping("/member/fail")
    String failView(){
        return "/member/fail";
    }
    @RequestMapping("/member/admin")
    ModelAndView andView(){
        List<MemberDto> userDtolist = memberRepository.findAll().stream().map(u -> MemberDto.builder().id(u.getId()).email(u.getEmail()).password(u.getPassword()).role(u.getRole()).build()).collect(Collectors.toList());
        ModelAndView modelAndView = new ModelAndView("/admin");
        modelAndView.addObject("userList",userDtolist);

        return modelAndView;
    }
    @RequestMapping("/member/myinfo")
    ModelAndView myView(Authentication authentication) {
    	MemberDto userDTO = Optional.ofNullable(memberRepository.findByEmail(authentication.getName()))
        .map(u -> MemberDto.builder().id(u.getId()).email(u.getEmail()).password(u.getPassword()).role(u.getRole()).build())
        .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));

        ModelAndView modelAndView = new ModelAndView("/member/myinfo");
        modelAndView.addObject("user", userDTO);

        return modelAndView;
    }
    @RequestMapping("/member/signup")
    String signupView(){
    	System.out.println("1");
    	return "/member/signup";
    }
}