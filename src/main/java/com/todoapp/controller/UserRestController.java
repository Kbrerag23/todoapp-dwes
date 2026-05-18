package com.todoapp.controller;

import com.todoapp.dto.UserResponseDto;
import com.todoapp.model.User;
import com.todoapp.repository.UserRepository;
import com.todoapp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "6. Perfil", description = "Gestión de la cuenta")
public class UserRestController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PutMapping("/profile")
    @Operation(summary = "Modificar perfil de usuario (username, email, password...)")
    public UserResponseDto updateProfile(@RequestBody Map<String, String> updates, @Parameter(hidden = true) Authentication auth) {
        User user = userService.findEntityByEmail(auth.getName());
        if (updates.containsKey("fullname")) user.setFullname(updates.get("fullname"));
        if (updates.containsKey("username")) user.setUsername(updates.get("username"));
        if (updates.containsKey("password")) user.setPassword(passwordEncoder.encode(updates.get("password")));
        userRepository.save(user);
        return UserResponseDto.builder().id(user.getId()).username(user.getUsername()).email(user.getEmail()).fullname(user.getFullname()).role(user.getRole()).build();
    }
}