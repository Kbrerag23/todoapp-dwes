package com.todoapp.controller;

import com.todoapp.dto.CategoryDto;
import com.todoapp.repository.CategoryRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@Tag(name = "5. Categorías Generales", description = "Listado público de categorías")
public class CategoryRestController {

    private final CategoryRepository categoryRepository;

    @GetMapping
    @Operation(summary = "Listar categorías disponibles (usuario)")
    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(c -> CategoryDto.builder().id(c.getId()).title(c.getTitle()).build())
                .collect(Collectors.toList());
    }
}