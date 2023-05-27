package com.board.dto.member;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * 사용자에 대한 정보를 전달하는 Data Transfer Object 클래스
 */
@Data
public class MemberRequestDto {
    @NotNull                         	 // 필수 입력 필드를 나타내는 어노테이션
    @Email                           	 // 이메일 형식 검증을 위한 어노테이션
    private String email;             	 // 사용자의 이메일
    @NotNull                         	 // 필수 입력 필드를 나타내는 어노테이션
    private String password;          	 // 사용자의 비밀번호
    @NotNull                         	 // 필수 입력 필드를 나타내는 어노테이션
    private String role = "ROLE_MEMBER"; // 사용자의 역할 (기본값은 "ROLE_MEMBER")

    /**
     * MemberRequestDto의 생성자
     *
     * @param email    사용자의 이메일
     * @param password 사용자의 비밀번호
     */
    public MemberRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
