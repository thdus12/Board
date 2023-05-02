package com.board.service.member;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.board.dto.member.MemberDto;
import com.board.dto.member.MemberFormDto;
import com.board.entity.board.member.Member;
import com.board.entity.board.member.MemberRepository;
import com.board.security.Role;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public MemberDto createUser(MemberFormDto memberFormDto) {

        // 이메일 중복 확인
        if(memberRepository.findByEmail(memberFormDto.getEmail()) != null){
            return null;
        }
        // 가입한 성공한 모든 유저는  "USER" 권한 부여
        Member member = memberRepository.save(Member.builder()
                                        .pwd(bCryptPasswordEncoder.encode(memberFormDto.getPassword()))
                                        .email(memberFormDto.getEmail())
                                        .role(Role.USER)
                                        .build());
        return MemberDto.builder()
                        .id(member.getId())
                        .email(member.getEmail())
                        .password(member.getPassword())
                        .role(member.getRole())
                        .build();
    }

	@Override
	public Member getCurrentUser(MemberFormDto memberFormDto) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = memberRepository.findByEmail(memberFormDto.getEmail());
        return member;
	}

}