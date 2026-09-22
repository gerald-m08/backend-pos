package com.example.pos.service;

import com.example.pos.dto.request.LoginRequest;
import com.example.pos.dto.request.RegisterRequest;
import com.example.pos.entity.Role;
import com.example.pos.entity.User;
import com.example.pos.exception.ConflictException;
import com.example.pos.exception.UnauthorizedException;
import com.example.pos.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public User register(RegisterRequest request) {

        if (userRepository.existsByUsernameIgnoreCase(request.getUsername())) {
            throw new ConflictException(
                    "Username already exists"
            );
        }

        if (userRepository.existsByEmailIgnoreCase(request.getEmail())) {
            throw new ConflictException(
                    "Email already exists"
            );
        }

        User user = User.builder()
                .username(request.getUsername())
                .password(
                        passwordEncoder.encode(
                                request.getPassword()
                        )
                )
                .email(request.getEmail())
                .age(request.getAge())
                .role(Role.CASHIER)
                .build();

        return userRepository.save(user);
    }

    public String login(LoginRequest request) {

        User user = userRepository
                .findByUsernameIgnoreCase(
                        request.getUsername()
                )
                .orElseThrow(() ->
                        new UnauthorizedException(
                                "Username or password is incorrect"
                        )
                );

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        )) {
            throw new UnauthorizedException(
                    "Username or password is incorrect"
            );
        }

        return jwtService.generateToken(
                user.getUsername()
        );
    }
}