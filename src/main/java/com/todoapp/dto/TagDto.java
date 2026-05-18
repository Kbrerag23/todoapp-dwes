package com.todoapp.dto;

import lombok.Builder;
import lombok.Data;

/**
 * DTO justificado: Evita exponer la entidad JPA Tag y previene
 * el bucle infinito (StackOverflow) en Swagger al parsear las tareas.
 */
@Data
@Builder
public class TagDto {
    private Long id;
    private String name;
}