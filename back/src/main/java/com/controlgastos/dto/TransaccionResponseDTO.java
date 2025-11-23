package com.controlgastos.dto;

import com.controlgastos.model.TipoTransaccion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * DTO para la respuesta de transacción
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransaccionResponseDTO {
    
    private String id;
    private TipoTransaccion tipoTransaccion;
    private String categoriaId;
    private String categoriaNombre;
    private String descripcion;
    private LocalDateTime fecha;
    private Double monto;
    private String userId;
}
