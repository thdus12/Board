package com.board.entity.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Member 엔티티에 대한 JPA Repository 인터페이스
 */
@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    /**
     * 이메일을 기준으로 회원을 찾는 메소드
     *
     * @param email 이메일
     * @return 해당 이메일을 가진 회원 엔티티
     */
    public MemberEntity findByEmail(String email);
}
