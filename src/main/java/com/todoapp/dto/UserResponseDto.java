package com.todoapp.dto;

import com.todoapp.model.UserRole;
import lombok.Builder;
import lombok.Data;

/**
 * DTO justificado: Se utiliza para devolver la informacion del usuario en las peticiones GET.
 * Es estrictamente necesario para evitar exponer la contraseña (password) encriptada
 * u otros datos sensibles que si estan presentes en la entidad User de JPA.
 */
@Data
@Builder
public class UserResponseDto {
    private Long id;
    private String username;
    private String email;
    private String fullname;
    private UserRole role;
}