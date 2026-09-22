package com.example.pos.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {

    @NotBlank(message = "Username is required")
    @Size(
            min = 4,
            max = 50,
            message = "Username must be between 4 and 50 characters"
    )
    private String username;

    @NotBlank(message = "Password is required")
    @Size(
            min = 8,
            message = "Password must contain at least 8 characters"
    )
    private String password;

    @NotBlank(message = "Email is required")
    @Email(message = "Email format is invalid")
    private String email;

    @NotNull(message = "Age is required")
    @Min(
            value = 1,
            message = "Age must be greater than 0"
    )
    private Integer age;
}