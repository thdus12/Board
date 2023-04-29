package com.board.service.member;

import com.board.dto.member.MemberDto;
import com.board.dto.member.MemberFormDto;


public interface MemberService {
	MemberDto createUser(MemberFormDto userFormDto);    
}