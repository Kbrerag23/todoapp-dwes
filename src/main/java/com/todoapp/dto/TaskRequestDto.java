package com.todoapp.dto;

import com.todoapp.model.Priority;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

/**
 * DTO justificado: Se usa para recibir datos de creacion o edicion de tareas (POST/PUT).
 * Evita recibir objetos complejos completos. En su lugar, recibe IDs de categorias
 * y tags para que el Service busque las referencias reales en la base de datos.
 */
@Data
public class TaskRequestDto {
    private String title;
    private String description;
    private boolean completed;
    private Long categoryId;
    private List<Long> tagIds;
    private LocalDate deadline;
    private Priority priority;
}