package com.board.entity.board.member;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

//Member 엔티티에 대한 JPA Repository 인터페이스
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

	// 회원의 마지막 로그인 시간을 업데이트하는 쿼리
	static final String UPDATE_MEMBER_LAST_LOGIN = 
			"UPDATE Member SET LAST_LOGIN_TIME = :lastLoginTime WHERE EMAIL = :email";
	
	// 트랜잭션을 사용하여 데이터베이스 작업을 수행
	@Transactional
	// 쿼리가 데이터를 수정하는 작업
	@Modifying
	// 쿼리를 실행하는 메소드
	@Query(value = UPDATE_MEMBER_LAST_LOGIN, nativeQuery = true)
	public int updateMemberLastLogin(
	    @Param("email") String email, // 이메일 파라미터
	    @Param("lastLoginTime") LocalDateTime lastLoginTime // 마지막 로그인 시간 파라미터
	);
	
	// 이메일을 기준으로 회원을 찾는 메소드
	public Member findByEmail(String email);
 
}