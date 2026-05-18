package com.todoapp.dto;

import com.todoapp.model.Priority;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO justificado: Se utiliza para devolver una tarea formateada al cliente.
 * Evita ciclos infinitos de serializacion JSON (StackOverflowError) en las relaciones
 * bidireccionales de Hibernate y formatea al autor usando su propio UserResponseDto.
 */
@Data
@Builder
public class TaskResponseDto {
    private Long id;
    private String title;
    private String description;
    private boolean completed;
    private LocalDateTime createdAt;
    private LocalDate deadline;
    private Priority priority;
    private String categoryName;
    private List<String> tags;
    private UserResponseDto author;
}