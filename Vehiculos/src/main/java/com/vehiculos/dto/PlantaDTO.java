package com.vehiculos.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlantaDTO {
    
    @JsonProperty("id")
    private String id;
    
    @NotBlank(message = "El nombre de la planta es requerido")
    @JsonProperty("nombre")
    private String nombre;
    
    @NotBlank(message = "La ubicación es requerida")
    @JsonProperty("ubicacion")
    private String ubicacion;
    
    @JsonProperty("fabricaId")
    private String fabricaId;
}
