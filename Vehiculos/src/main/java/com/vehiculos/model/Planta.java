package com.vehiculos.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "plantas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Planta {
    
    @Id
    private String id;
    
    private String nombre;
    
    private String ubicacion;
    
    private String fabricaId;
    
    public Planta(String nombre, String ubicacion, String fabricaId) {
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.fabricaId = fabricaId;
    }
}
