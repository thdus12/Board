package com.board.service.member;

import org.springframework.security.core.userdetails.User;

import com.board.dto.member.MemberDto;
import com.board.dto.member.MemberFormDto;
import com.board.entity.board.member.Member;


public interface MemberService {
	MemberDto createUser(MemberFormDto userFormDto);    
	
	Member getCurrentUser(MemberFormDto userFormDto);
}