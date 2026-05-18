package com.todoapp.controller;

import com.todoapp.dto.CategoryDto;
import com.todoapp.model.Category;
import com.todoapp.repository.CategoryRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/manager")
@RequiredArgsConstructor
@Tag(name = "2. Gestor", description = "Endpoints para GESTOR o ADMIN (Ítem 3b de la rúbrica)")
public class ManagerRestController {

    private final CategoryRepository categoryRepository;

    @GetMapping("/categories")
    @Operation(summary = "Listar categorías (gestor)")
    public List<CategoryDto> getCategories() {
        return categoryRepository.findAll().stream()
                .map(c -> CategoryDto.builder().id(c.getId()).title(c.getTitle()).build())
                .collect(Collectors.toList());
    }

    @PostMapping("/categories")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear categoría (gestor)")
    public CategoryDto createCategory(@RequestBody CategoryDto dto) {
        Category c = new Category();
        c.setTitle(dto.getTitle());
        Category saved = categoryRepository.save(c);
        return CategoryDto.builder().id(saved.getId()).title(saved.getTitle()).build();
    }

    @PutMapping("/categories/{id}")
    @Operation(summary = "Editar categoría (gestor)")
    public CategoryDto updateCategory(@PathVariable("id") Long id, @RequestBody CategoryDto dto) {
        Category c = categoryRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        c.setTitle(dto.getTitle());
        Category saved = categoryRepository.save(c);
        return CategoryDto.builder().id(saved.getId()).title(saved.getTitle()).build();
    }

    @DeleteMapping("/categories/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Eliminar categoría (gestor)")
    public void deleteCategory(@PathVariable("id") Long id) {
        categoryRepository.deleteById(id);
    }
}