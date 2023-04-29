package com.board.dto.member;

import com.board.security.Role;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberDto {
    private Long id;
    private String email;
    private String password;
    private Role role;
    
    @Builder
    public MemberDto(Long id, String email, String password,Role role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
    }
    
    @Override
    public String toString() {
        return "BoardListDto [id=" + id + ", "
        		+ "email=" + email 
        		+ ", password=" + password 
        		+ ", role=" + role + "]";
    }
}