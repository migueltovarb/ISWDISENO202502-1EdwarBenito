package com.controlgastos.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad que representa una categoría de transacciones
 * Almacenada en la colección "categorias" de MongoDB
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "categorias")
public class Categoria {
    
    @Id
    private String id;
    
    @NotBlank(message = "El nombre de la categoría es obligatorio")
    private String nombre;
    
    private String userId; // Referencia al usuario propietario
    
    /**
     * Constructor para crear una categoría sin ID (se generará automáticamente)
     */
    public Categoria(String nombre, String userId) {
        this.nombre = nombre;
        this.userId = userId;
    }
    
    @Override
    public String toString() {
        return "Categoria{id='" + id + "', nombre='" + nombre + "', userId='" + userId + "'}";
    }
}
