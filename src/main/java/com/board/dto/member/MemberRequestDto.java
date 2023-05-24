package com.board.dto.member;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberRequestDto {
    @NotNull
    @Email
    private String email;
    @NotNull
    private String password;
    @NotNull
    private String role = "ROLE_MEMBER";
    
    public MemberRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}