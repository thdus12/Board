package com.board.service.member;

import com.board.dto.member.MemberRequestDto;

import com.board.entity.member.MemberEntity;

/**
 * 회원 관련 서비스의 기능을 정의한 인터페이스
 */
public interface MemberService {

    /**
     * 현재 사용자를 조회하는 메소드
     *
     * @param memberFormDto 현재 사용자 정보를 담은 DTO 객체
     * @return 현재 사용자의 MemberEntity 객체
     */
    MemberEntity getCurrentUser(MemberRequestDto memberFormDto);
}
