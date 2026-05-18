package com.todoapp.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO justificado: Se utiliza exclusivamente para recibir los datos de registro (POST).
 * Desacopla la vista de la base de datos y permite validar que las contraseñas coinciden
 * antes de intentar crear la entidad User real.
 */
@Data
@NoArgsConstructor
public class UserRegistrationDto {
    private String username;
    private String email;
    private String fullname;
    private String password;
    private String confirmPassword;
}