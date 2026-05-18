package com.todoapp.controller;

import com.todoapp.dto.TagDto;
import com.todoapp.model.Tag;
import com.todoapp.repository.TagRepository;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tag")
@RequiredArgsConstructor
@io.swagger.v3.oas.annotations.tags.Tag(name = "4. Tags", description = "Gestión de etiquetas")
public class TagRestController {

    private final TagRepository tagRepository;

    @GetMapping
    @Operation(summary = "Listar tags del usuario")
    public List<TagDto> getAllTags() {
        return tagRepository.findAll().stream()
                .map(t -> TagDto.builder().id(t.getId()).name(t.getName()).build())
                .collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear un tag")
    public TagDto createTag(@RequestBody TagDto dto) {
        Tag tag = new Tag();
        tag.setName(dto.getName());
        Tag saved = tagRepository.save(tag);
        return TagDto.builder().id(saved.getId()).name(saved.getName()).build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Editar un tag")
    public TagDto updateTag(@PathVariable("id") Long id, @RequestBody TagDto dto) {
        Tag tag = tagRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        tag.setName(dto.getName());
        Tag saved = tagRepository.save(tag);
        return TagDto.builder().id(saved.getId()).name(saved.getName()).build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Eliminar un tag")
    public void deleteTag(@PathVariable("id") Long id) {
        tagRepository.deleteById(id);
    }
}