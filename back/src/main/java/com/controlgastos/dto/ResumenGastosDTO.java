package com.controlgastos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para reportes de resumen de gastos e ingresos
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResumenGastosDTO {
    
    private Double totalIngresos;
    private Double totalGastos;
    private Double balance;
    private Long cantidadIngresos;
    private Long cantidadGastos;
    private String periodo; // Descripción del periodo analizado
}
