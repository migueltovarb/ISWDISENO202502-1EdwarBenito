package com.vehiculos.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FabricaDTO {
    
    @JsonProperty("id")
    private String id;
    
    @NotBlank(message = "El nombre de la fábrica es requerido")
    @JsonProperty("nombre")
    private String nombre;
    
    @NotBlank(message = "El país es requerido")
    @JsonProperty("pais")
    private String pais;
}
