package com.example.pos.dto.request;

import com.example.pos.entity.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {

    @NotBlank(message = "Username is required")
    @Size(min = 4, max = 50, message = "Username must be between 4 and 50 characters")
    private String username;

    @Size(min = 8, message = "Password must contain at least 8 characters")
    private String password;

    @NotNull(message = "Role is required")
    private Role role;
}