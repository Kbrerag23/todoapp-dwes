package com.todoapp.controller;

import com.todoapp.model.User;
import com.todoapp.service.TaskService;
import com.todoapp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
@Tag(name = "7. Dashboard", description = "Estadísticas del usuario")
public class DashboardRestController {

    private final TaskService taskService;
    private final UserService userService;

    @GetMapping
    @Operation(summary = "Dashboard con estadísticas de tareas", description = "Devuelve estadísticas útiles como tareas pendientes, completadas, etc.")
    public Map<String, Object> getDashboard(@Parameter(hidden = true) Authentication auth) {
        User user = userService.findEntityByEmail(auth.getName());
        return taskService.getDashboardStats(user);
    }
}