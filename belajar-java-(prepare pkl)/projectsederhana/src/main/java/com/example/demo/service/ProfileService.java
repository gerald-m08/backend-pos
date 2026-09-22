package com.example.demo.service;

import com.example.demo.dto.ProfileResponse;
import com.example.demo.entity.RoleToMenu;
import com.example.demo.entity.User;
import com.example.demo.repository.RoleToMenuRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final UserRepository userRepository;

    private final RoleToMenuRepository
            roleToMenuRepository;

    public ProfileResponse getProfile(
            String username,
            String token
    ) {

        User user =
                userRepository
                        .findByUsername(username)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User tidak ditemukan"
                                )
                        );

        List<String> menus =
                roleToMenuRepository
                        .findByRole(user.getRole())
                        .stream()
                        .map(RoleToMenu::getMenu)
                        .toList();

        return ProfileResponse.builder()
                .username(user.getUsername())
                .role(user.getRole().name())
                .token(token)
                .menus(menus)
                .build();
    }
}