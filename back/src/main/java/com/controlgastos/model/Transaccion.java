package com.controlgastos.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * Entidad que representa una transacción (ingreso o gasto)
 * Almacenada en la colección "transacciones" de MongoDB
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "transacciones")
public class Transaccion {
    
    @Id
    private String id;
    
    @NotNull(message = "El tipo de transacción es obligatorio")
    private TipoTransaccion tipoTransaccion;
    
    @NotBlank(message = "El ID de categoría es obligatorio")
    private String categoriaId; // Referencia a Categoria
    
    private String categoriaNombre; // Desnormalizado para consultas rápidas
    
    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;
    
    @NotNull(message = "La fecha es obligatoria")
    private LocalDateTime fecha;
    
    @NotNull(message = "El monto es obligatorio")
    @Positive(message = "El monto debe ser positivo")
    private Double monto;
    
    @NotBlank(message = "El ID de usuario es obligatorio")
    private String userId; // Referencia al usuario propietario
    
    /**
     * Constructor para crear una transacción sin ID (se generará automáticamente)
     */
    public Transaccion(TipoTransaccion tipoTransaccion, String categoriaId, String categoriaNombre,
                       String descripcion, LocalDateTime fecha, Double monto, String userId) {
        this.tipoTransaccion = tipoTransaccion;
        this.categoriaId = categoriaId;
        this.categoriaNombre = categoriaNombre;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.monto = monto;
        this.userId = userId;
    }
    
    @Override
    public String toString() {
        return "Transaccion{" +
                "id='" + id + '\'' +
                ", tipoTransaccion=" + tipoTransaccion +
                ", categoriaId='" + categoriaId + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", fecha=" + fecha +
                ", monto=" + monto +
                ", userId='" + userId + '\'' +
                '}';
    }
}
