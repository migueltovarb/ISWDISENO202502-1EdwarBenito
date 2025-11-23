package com.controlgastos.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para la creación de un nuevo usuario
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {
    
    @NotBlank(message = "El apodo es obligatorio")
    @Size(min = 3, max = 50, message = "El apodo debe tener entre 3 y 50 caracteres")
    private String apodo;
    
    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo debe ser válido")
    private String correo;
    
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String contrasenia;
}
