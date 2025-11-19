package com.vehiculos.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Document(collection = "fabricas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Fabrica {
    
    @Id
    private String id;
    
    private String nombre;
    
    private String pais;
    
    private List<String> plantasIds;
    
    public Fabrica(String nombre, String pais) {
        this.nombre = nombre;
        this.pais = pais;
    }
}
