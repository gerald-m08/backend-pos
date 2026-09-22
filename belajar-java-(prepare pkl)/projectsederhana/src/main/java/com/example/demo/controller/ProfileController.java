package com.example.demo.controller;

import com.example.demo.dto.ProfileResponse;
import com.example.demo.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/profile")
    public ResponseEntity<ProfileResponse> profile(Authentication authentication,
            @RequestHeader("Authorization") String authorization) {

        String token =
                authorization.substring(7);

        ProfileResponse response = profileService.getProfile(authentication.getName(),  token);

        return ResponseEntity.ok(response);
    }
}