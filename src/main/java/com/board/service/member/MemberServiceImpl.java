package com.board.service.member;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.board.dto.member.MemberRequestDto;
import com.board.entity.member.MemberEntity;
import com.board.entity.member.MemberRepository;

import lombok.RequiredArgsConstructor;

/**
 * 회원 관련 서비스의 구현 클래스
 */
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    /**
     * 현재 사용자를 조회하는 메소드
     *
     * @param memberRequestDto 현재 사용자 정보를 담은 DTO 객체
     * @return 현재 사용자의 MemberEntity 객체
     */
    @Override
    public MemberEntity getCurrentUser(MemberRequestDto memberRequestDto) {
        MemberEntity member = memberRepository.findByEmail(memberRequestDto.getEmail());
        return member;
    }

    /**
     * 주어진 이름의 사용자가 관리자인지 확인하는 메소드
     *
     * @param name 확인할 사용자의 이름
     * @return 사용자가 관리자인 경우 true, 아닌 경우 false
     */
    public boolean isAdmin(String name) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof MemberEntity) {
            MemberEntity memberEntity = (MemberEntity) authentication.getPrincipal();
            return memberEntity.getAuthorities().stream()
                    .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
        }
        return false;
    }

    /**
     * 주어진 멤버 ID로 멤버를 조회하는 메소드
     *
     * @param memberId 조회할 멤버의 ID
     * @return 조회된 멤버의 MemberEntity 객체
     */
    public MemberEntity getMemberByEmail(String memberId) {
        return memberRepository.findByEmail(memberId);
    }
}