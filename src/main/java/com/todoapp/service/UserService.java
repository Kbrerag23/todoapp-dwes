package com.todoapp.service;

import com.todoapp.dto.UserRegistrationDto;
import com.todoapp.dto.UserResponseDto;
import com.todoapp.model.User;
import com.todoapp.model.UserRole;
import com.todoapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponseDto register(UserRegistrationDto dto) {
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Las contraseñas no coinciden");
        }
        if (userRepository.findByEmail(dto.getEmail()).isPresent() || userRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El usuario o email ya existe");
        }

        User user = User.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .fullname(dto.getFullname())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(UserRole.USER)
                .build();

        return mapToDto(userRepository.save(user));
    }

    public User findEntityByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
    }

    public void promoteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow();
        user.setRole(UserRole.GESTOR);
        userRepository.save(user);
    }

    public void demoteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow();
        user.setRole(UserRole.USER);
        userRepository.save(user);
    }

    private UserResponseDto mapToDto(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .fullname(user.getFullname())
                .role(user.getRole())
                .build();
    }
}