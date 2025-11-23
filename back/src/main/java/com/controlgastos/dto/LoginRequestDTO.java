package com.controlgastos.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDTO {
    
    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "El correo electrónico debe ser válido")
    @Size(max = 50, message = "El correo electrónico no debe superar los 50 caracteres")
    private String correo;
    
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, max = 20, message = "La contraseña debe tener entre 8 y 20 caracteres")
    private String contrasenia;
}
