package com.example.pos.dto.response;

import com.example.pos.entity.Role;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class UserResponse {

    private UUID id;
    private String username;
    private Role role;
}