package com.example.pos.service;

import com.example.pos.dto.request.UserRequest;
import com.example.pos.dto.response.PageResponse;
import com.example.pos.dto.response.UserResponse;
import com.example.pos.entity.User;
import com.example.pos.exception.ConflictException;
import com.example.pos.exception.ResourceNotFoundException;
import com.example.pos.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public PageResponse<UserResponse> getAll(
            int page,
            int limit,
            String sortBy,
            String sortDirection
    ) {
        String sortField = switch (sortBy.toLowerCase()) {
            case "username" -> "username";
            case "role" -> "role";
            default -> "createdAt";
        };

        Sort sort = sortDirection.equalsIgnoreCase("asc")
                ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(page - 1, limit, sort);
        Page<User> userPage = userRepository.findAll(pageable);

        List<UserResponse> data = userPage.getContent()
                .stream()
                .map(this::toResponse)
                .toList();

        return PageResponse.<UserResponse>builder()
                .page(page)
                .limit(limit)
                .totalData(userPage.getTotalElements())
                .totalPage(userPage.getTotalPages())
                .data(data)
                .build();
    }

    public UserResponse getById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return toResponse(user);
    }

    public UserResponse update(UUID id, UserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!user.getUsername().equalsIgnoreCase(request.getUsername())
                && userRepository.existsByUsernameIgnoreCase(request.getUsername())) {

            throw new ConflictException("Username already exists");
        }

        user.setUsername(request.getUsername());
        user.setRole(request.getRole());

        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        return toResponse(userRepository.save(user));
    }

    public void delete(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        userRepository.delete(user);
    }

    private UserResponse toResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .role(user.getRole())
                .build();
    }
}