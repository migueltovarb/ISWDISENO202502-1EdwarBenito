package com.controlgastos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para la respuesta de categoría
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaResponseDTO {
    
    private String id;
    private String nombre;
    private String userId;
}
