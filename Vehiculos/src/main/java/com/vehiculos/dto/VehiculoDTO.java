package com.vehiculos.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehiculoDTO {
    
    @JsonProperty("id")
    private String id;
    
    @NotBlank(message = "La marca es requerida")
    @JsonProperty("marca")
    private String marca;
    
    @NotBlank(message = "El modelo es requerido")
    @JsonProperty("modelo")
    private String modelo;
    
    @NotBlank(message = "El tipo de llantas es requerido")
    @JsonProperty("tipoLlantas")
    private String tipoLlantas;
    
    @NotNull(message = "El número de puertas es requerido")
    @JsonProperty("numeroPuertas")
    private Integer numeroPuertas;
    
    @JsonProperty("plantaId")
    private String plantaId;
}
