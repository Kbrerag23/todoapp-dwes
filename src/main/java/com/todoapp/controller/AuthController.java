package com.todoapp.controller;

import com.todoapp.dto.UserRegistrationDto;
import com.todoapp.dto.UserResponseDto;
import com.todoapp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticación", description = "Endpoints públicos")
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Registrar nuevo usuario", description = "Crea un usuario y devuelve sus datos sin la contraseña")
    public UserResponseDto register(@RequestBody UserRegistrationDto dto) {
        return userService.register(dto);
    }
}