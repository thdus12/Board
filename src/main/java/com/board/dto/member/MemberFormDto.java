package com.board.dto.member;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberFormDto {
    @NotNull
    @Email
    private String email;

    @NotNull
    private String password;
}