package com.board.dto.member;

import com.board.security.Role;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 사용자에 대한 정보를 응답하는 Data Transfer Object 클래스
 */
@Data
@NoArgsConstructor
public class MemberResponseDto {
    private Long id;           // 사용자의 고유 식별자(ID)
    private String email;      // 사용자의 이메일
    private String password;   // 사용자의 비밀번호
    private Role role;         // 사용자의 역할 (Role 열거형)

    /**
     * MemberResponseDto의 생성자
     *
     * @param id       사용자의 고유 식별자(ID)
     * @param email    사용자의 이메일
     * @param password 사용자의 비밀번호
     * @param role     사용자의 역할 (Role 열거형)
     */
    @Builder
    public MemberResponseDto(Long id, String email, String password, Role role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
    }
    
    /**
     * 객체를 문자열로 표현하기 위해 재정의된 toString() 메서드
     *
     * @return 객체의 문자열 표현
     */
    @Override
    public String toString() {
        return "MemberListDto ["
                + "id=" + id
                + ", email=" + email
                + ", password=" + password
                + ", role=" + role
                + "]";
    }
}