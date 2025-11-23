package com.controlgastos.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad que representa un usuario del sistema
 * Almacenada en la colección "usuarios" de MongoDB
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "usuarios")
public class User {
    
    @Id
    private String id;
    
    @NotBlank(message = "El apodo es obligatorio")
    @Size(min = 3, max = 50, message = "El apodo debe tener entre 3 y 50 caracteres")
    @Indexed(unique = true)
    private String apodo;
    
    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo debe ser válido")
    @Indexed(unique = true)
    private String correo;
    
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String contrasenia;
    
    // Listas de IDs referenciadas (no embebidas para mejor escalabilidad)
    private List<String> transaccionesIds = new ArrayList<>();
    private List<String> categoriasIds = new ArrayList<>();
    
    /**
     * Constructor para crear un usuario sin ID (se generará automáticamente)
     */
    public User(String apodo, String correo, String contrasenia) {
        this.apodo = apodo;
        this.correo = correo;
        this.contrasenia = contrasenia;
        this.transaccionesIds = new ArrayList<>();
        this.categoriasIds = new ArrayList<>();
    }
    
    /**
     * Agrega una transacción al usuario
     */
    public void agregarTransaccion(String transaccionId) {
        if (this.transaccionesIds == null) {
            this.transaccionesIds = new ArrayList<>();
        }
        this.transaccionesIds.add(transaccionId);
    }
    
    /**
     * Agrega una categoría al usuario
     */
    public void agregarCategoria(String categoriaId) {
        if (this.categoriasIds == null) {
            this.categoriasIds = new ArrayList<>();
        }
        this.categoriasIds.add(categoriaId);
    }
    
    /**
     * Elimina una transacción del usuario
     */
    public void eliminarTransaccion(String transaccionId) {
        if (this.transaccionesIds != null) {
            this.transaccionesIds.remove(transaccionId);
        }
    }
    
    /**
     * Elimina una categoría del usuario
     */
    public void eliminarCategoria(String categoriaId) {
        if (this.categoriasIds != null) {
            this.categoriasIds.remove(categoriaId);
        }
    }
    
    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", apodo='" + apodo + '\'' +
                ", correo='" + correo + '\'' +
                ", totalTransacciones=" + (transaccionesIds != null ? transaccionesIds.size() : 0) +
                ", totalCategorias=" + (categoriasIds != null ? categoriasIds.size() : 0) +
                '}';
    }
}
