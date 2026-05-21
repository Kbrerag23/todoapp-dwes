package com.todoapp.controller;

import com.todoapp.dto.TaskRequestDto;
import com.todoapp.dto.TaskResponseDto;
import com.todoapp.model.Priority;
import com.todoapp.model.User;
import com.todoapp.service.TaskService;
import com.todoapp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
@Tag(name = "3. Tareas", description = "Gestión de tareas")
public class TaskRestController {

    private final TaskService taskService;
    private final UserService userService;

    private User getAuthUser(Authentication auth) {
        return userService.findEntityByEmail(auth.getName());
    }

    @GetMapping
    @Operation(summary = "Listar todas las tareas del usuario autenticado")
    public List<TaskResponseDto> getMyTasks(@Parameter(hidden = true) Authentication auth) {
        return taskService.getTasksByAuthor(getAuthUser(auth));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Ver una tarea concreta del usuario conectado")
    public TaskResponseDto getTaskById(@PathVariable("id") Long id, @Parameter(hidden = true) Authentication auth) {
        return taskService.getTaskById(id, getAuthUser(auth));
    }

    @GetMapping("/search")
    @Operation(summary = "Buscar tareas con filtros (Ampliación y Base)")
    public List<TaskResponseDto> searchTasks(
            @RequestParam(name = "title", required = false) String title,
            @RequestParam(name = "completed", required = false) Boolean completed,
            @RequestParam(name = "category", required = false) String category,
            @RequestParam(name = "priority", required = false) Priority priority,
            @RequestParam(name = "deadlineBefore", required = false) @Parameter(schema = @Schema(type = "string", format = "date")) LocalDate deadlineBefore,
            @Parameter(hidden = true) Authentication auth) {
        return taskService.searchTasks(getAuthUser(auth), title, completed, category, priority, deadlineBefore);
    }

    @GetMapping("/by-tag")
    @Operation(summary = "Buscar tareas con un tag concreto")
    public List<TaskResponseDto> getTasksByTag(@RequestParam("tag") String tag, @Parameter(hidden = true) Authentication auth) {
        return taskService.getTasksByTag(tag, getAuthUser(auth));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear una nueva tarea")
    public TaskResponseDto createTask(@RequestBody TaskRequestDto dto, @Parameter(hidden = true) Authentication auth) {
        return taskService.createTask(dto, getAuthUser(auth));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Editar una tarea (solo si es del usuario)")
    public TaskResponseDto updateTask(@PathVariable("id") Long id, @RequestBody TaskRequestDto dto, @Parameter(hidden = true) Authentication auth) {
        return taskService.updateTask(id, dto, getAuthUser(auth));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Eliminar una tarea")
    public void deleteTask(@PathVariable("id") Long id, @Parameter(hidden = true) Authentication auth) {
        taskService.deleteTask(id, getAuthUser(auth));
    }

    @PostMapping("/{id}/tags")
    @Operation(summary = "Asignar tags a una tarea")
    public TaskResponseDto addTagToTask(@PathVariable("id") Long id, @RequestParam("tagId") Long tagId, @Parameter(hidden = true) Authentication auth) {
        return taskService.addTagToTask(id, tagId, getAuthUser(auth));
    }

    @DeleteMapping("/{id}/tags/{tagId}")
    @Operation(summary = "Eliminar un tag de una tarea")
    public TaskResponseDto removeTagFromTask(@PathVariable("id") Long id, @PathVariable("tagId") Long tagId, @Parameter(hidden = true) Authentication auth) {
        return taskService.removeTagFromTask(id, tagId, getAuthUser(auth));
    }
}