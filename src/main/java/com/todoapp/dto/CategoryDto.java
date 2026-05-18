package com.todoapp.dto;

import lombok.Builder;
import lombok.Data;

/**
 * DTO justificado: Evita exponer la entidad JPA Category, previniendo
 * bucles infinitos en Swagger y cumpliendo estrictamente con el patrón REST.
 */
@Data
@Builder
public class CategoryDto {
    private Long id;
    private String title;
}