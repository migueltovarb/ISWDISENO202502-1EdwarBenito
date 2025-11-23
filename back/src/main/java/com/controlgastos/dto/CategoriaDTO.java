package com.controlgastos.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para la creación y actualización de categorías
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaDTO {
    
    @NotBlank(message = "El nombre de la categoría es obligatorio")
    private String nombre;
}
