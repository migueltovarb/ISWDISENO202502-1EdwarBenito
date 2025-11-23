package com.controlgastos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

/**
 * DTO para la respuesta de usuario (sin contraseña)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
    
    private String id;
    private String apodo;
    private String correo;
    private List<String> transaccionesIds;
    private List<String> categoriasIds;
    private int totalTransacciones;
    private int totalCategorias;
}
