package com.board.service.member;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.board.entity.member.MemberEntity;
import com.board.entity.member.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 사용자 인증을 위한 UserDetailsService의 구현 클래스
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MemberDetailServiceImpl implements UserDetailsService {
    private final MemberRepository memberRepository;
    
    /**
     * 주어진 이메일을 사용하여 사용자를 조회하는 메소드
     * 
     * @param email 조회할 사용자의 이메일
     * @return 조회된 사용자의 UserDetails 객체
     * @throws UsernameNotFoundException 주어진 이메일에 해당하는 사용자가 없을 경우 발생
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("check: " + email);
        MemberEntity member = memberRepository.findByEmail(email);
        
        if (member == null) {
            throw new UsernameNotFoundException("Not Found account.");
        }
        
        return member;
    }
}