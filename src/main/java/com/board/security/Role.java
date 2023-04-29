package com.board.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
    USER("ROLE_MEMBER"),ADMIN("ROLE_ADMIN");
    private String value;
}