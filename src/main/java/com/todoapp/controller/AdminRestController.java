package com.todoapp.controller;

import com.todoapp.dto.CategoryDto;
import com.todoapp.dto.UserResponseDto;
import com.todoapp.model.Category;
import com.todoapp.model.User;
import com.todoapp.repository.CategoryRepository;
import com.todoapp.repository.UserRepository;
import com.todoapp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Tag(name = "1. Administrador", description = "Endpoints exclusivos para ADMIN")
public class AdminRestController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @GetMapping("/users")
    @Operation(summary = "Listar todos los usuarios")
    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(u -> UserResponseDto.builder().id(u.getId()).username(u.getUsername()).email(u.getEmail()).fullname(u.getFullname()).role(u.getRole()).build())
                .collect(Collectors.toList());
    }

    @GetMapping("/users/{id}")
    @Operation(summary = "Ver un usuario concreto")
    public UserResponseDto getUser(@PathVariable("id") Long id) {
        User u = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return UserResponseDto.builder().id(u.getId()).username(u.getUsername()).email(u.getEmail()).fullname(u.getFullname()).role(u.getRole()).build();
    }

    @PutMapping("/users/{id}")
    @Operation(summary = "Editar un usuario")
    public UserResponseDto updateUser(@PathVariable("id") Long id, @RequestBody Map<String, String> updates) {
        User u = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (updates.containsKey("fullname")) u.setFullname(updates.get("fullname"));
        if (updates.containsKey("username")) u.setUsername(updates.get("username"));
        userRepository.save(u);
        return UserResponseDto.builder().id(u.getId()).username(u.getUsername()).email(u.getEmail()).fullname(u.getFullname()).role(u.getRole()).build();
    }

    @DeleteMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Eliminar un usuario")
    public void deleteUser(@PathVariable("id") Long id) {
        userRepository.deleteById(id);
    }

    @PostMapping("/users/{id}/promote")
    @Operation(summary = "Promover usuario a GESTOR")
    public Map<String, String> promote(@PathVariable("id") Long id) {
        userService.promoteUser(id);
        return Map.of("message", "Usuario promovido a GESTOR");
    }

    @PostMapping("/users/{id}/demote")
    @Operation(summary = "Degradar GESTOR a usuario")
    public Map<String, String> demote(@PathVariable("id") Long id) {
        userService.demoteUser(id);
        return Map.of("message", "Usuario degradado a USER");
    }

    @GetMapping("/categories")
    @Operation(summary = "Listar todas las categorías (admin)")
    public List<CategoryDto> getCategories() {
        return categoryRepository.findAll().stream()
                .map(c -> CategoryDto.builder().id(c.getId()).title(c.getTitle()).build())
                .collect(Collectors.toList());
    }

    @PostMapping("/categories")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear categoría (admin)")
    public CategoryDto createCategory(@RequestBody CategoryDto dto) {
        Category c = new Category();
        c.setTitle(dto.getTitle());
        Category saved = categoryRepository.save(c);
        return CategoryDto.builder().id(saved.getId()).title(saved.getTitle()).build();
    }

    @PutMapping("/categories/{id}")
    @Operation(summary = "Editar categoría (admin)")
    public CategoryDto updateCategory(@PathVariable("id") Long id, @RequestBody CategoryDto dto) {
        Category c = categoryRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        c.setTitle(dto.getTitle());
        Category saved = categoryRepository.save(c);
        return CategoryDto.builder().id(saved.getId()).title(saved.getTitle()).build();
    }

    @DeleteMapping("/categories/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Eliminar categoría (admin)")
    public void deleteCategory(@PathVariable("id") Long id) {
        categoryRepository.deleteById(id);
    }
}