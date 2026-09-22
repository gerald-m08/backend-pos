package com.task.pos.dto.response;

import com.task.pos.enums.Role;
import lombok.Getter;

@Getter
public class RegisterResponse {

    private final String username;
    private final String fullName;
    private final Role role;

    public RegisterResponse(String username, String fullName, Role role) {
        this.username = username;
        this.fullName = fullName;
        this.role = role;
    }
}