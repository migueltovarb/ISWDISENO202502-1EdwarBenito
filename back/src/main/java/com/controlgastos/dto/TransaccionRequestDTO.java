package com.controlgastos.dto;

import com.controlgastos.model.TipoTransaccion;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * DTO para la creación y actualización de transacciones
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransaccionRequestDTO {
    
    @NotNull(message = "El tipo de transacción es obligatorio")
    private TipoTransaccion tipoTransaccion;
    
    @NotBlank(message = "El ID de categoría es obligatorio")
    private String categoriaId;
    
    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;
    
    @NotNull(message = "La fecha es obligatoria")
    private LocalDateTime fecha;
    
    @NotNull(message = "El monto es obligatorio")
    @Positive(message = "El monto debe ser positivo")
    private Double monto;
}
