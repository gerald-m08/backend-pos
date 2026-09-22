package com.task.pos.dto.response;

import com.task.pos.enums.Role;
import lombok.Getter;

@Getter
public class LoginResponse {

    private final String token;
    private final String username;
    private final String fullName;
    private final Role role;

    public LoginResponse(String token, String username, String fullName, Role role) {
        this.token = token;
        this.username = username;
        this.fullName = fullName;
        this.role = role;
    }
}