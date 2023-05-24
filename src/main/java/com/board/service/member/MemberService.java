package com.board.service.member;

import com.board.dto.member.MemberRequestDto ;
import com.board.dto.member.MemberResponseDto;
import com.board.entity.member.MemberEntity;


public interface MemberService {
	MemberEntity getCurrentUser(MemberRequestDto memberFormDto);
}