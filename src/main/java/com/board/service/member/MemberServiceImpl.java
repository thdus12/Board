package com.board.service.member;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.board.dto.member.MemberRequestDto;
import com.board.dto.member.MemberResponseDto;
import com.board.entity.member.MemberEntity;
import com.board.entity.member.MemberRepository;
import com.board.security.Role;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

	@Override
	public MemberEntity getCurrentUser(MemberRequestDto memberRequestDto) {
		 MemberEntity member = memberRepository.findByEmail(memberRequestDto.getEmail());
        return member;
	}

	public boolean isAdmin(String name) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof MemberEntity) {
        	MemberEntity memberEntity = (MemberEntity) authentication.getPrincipal();
            return memberEntity.getAuthorities().stream()
                    .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
        }
        return false;
	}

	public MemberEntity getMemberByEmail(String memberId) {
		return memberRepository.findByEmail(memberId);
	}
}