package com.board.service.member;

import java.util.NoSuchElementException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.board.dto.member.MemberRequestDto;
import com.board.dto.member.MemberResponseDto;
import com.board.entity.board.BoardEntity;
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
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public MemberResponseDto createUser(MemberRequestDto memberRequestDto) {

        // 이메일 중복 확인
        if(memberRepository.findByEmail(memberRequestDto.getEmail()) != null){
            return null;
        }
        
        log.info("Role: {}", Role.MEMBER);
        
        // 가입한 성공한 모든 유저는  "MEMBER" 권한 부여
        MemberEntity member = memberRepository.save(MemberEntity.builder()
                                        .pwd(bCryptPasswordEncoder.encode(memberRequestDto.getPassword()))
                                        .email(memberRequestDto.getEmail())
                                        .role(Role.MEMBER)
                                        .build());
        return MemberResponseDto.builder()
                        .id(member.getId())
                        .email(member.getEmail())
                        .password(member.getPassword())
                        .role(member.getRole())
                        .build();
    }

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