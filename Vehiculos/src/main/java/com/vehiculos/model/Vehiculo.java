package com.vehiculos.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection = "vehiculos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vehiculo {
    
    @Id
    private String id;
    
    private String marca;
    
    private String modelo;
    
    @JsonProperty("tipoLlantas")
    private String tipoLlantas;
    
    @JsonProperty("numeroPuertas")
    private Integer numeroPuertas;
    
    @JsonProperty("plantaId")
    private String plantaId;
    
    public Vehiculo(String marca, String modelo, String tipoLlantas, Integer numeroPuertas, String plantaId) {
        this.marca = marca;
        this.modelo = modelo;
        this.tipoLlantas = tipoLlantas;
        this.numeroPuertas = numeroPuertas;
        this.plantaId = plantaId;
    }
}
