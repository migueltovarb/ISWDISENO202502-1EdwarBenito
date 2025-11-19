package com.vehiculos.service;

import com.vehiculos.dto.FabricaDTO;
import com.vehiculos.exception.InvalidOperationException;
import com.vehiculos.exception.ResourceNotFoundException;
import com.vehiculos.model.Fabrica;
import com.vehiculos.repository.FabricaRepository;
import com.vehiculos.repository.PlantaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class FabricaService {
    
    private final FabricaRepository fabricaRepository;
    private final PlantaRepository plantaRepository;
    
    public Fabrica crearFabrica(FabricaDTO dto) {
        log.info("Creando nueva fábrica: {}", dto.getNombre());
        
        // Verificar que el nombre no exista
        Optional<Fabrica> existente = fabricaRepository.findByNombre(dto.getNombre());
        if (existente.isPresent()) {
            throw new InvalidOperationException("Ya existe una fábrica con el nombre: " + dto.getNombre());
        }
        
        Fabrica fabrica = new Fabrica(dto.getNombre(), dto.getPais());
        fabrica.setPlantasIds(new ArrayList<>());
        
        Fabrica guardada = fabricaRepository.save(fabrica);
        log.info("Fábrica creada exitosamente con ID: {}", guardada.getId());
        return guardada;
    }
    
    public List<Fabrica> obtenerTodasLasFabricas() {
        log.info("Obteniendo todas las fábricas");
        return fabricaRepository.findAll();
    }
    
    public Fabrica obtenerFabricaPorId(String id) {
        log.info("Obteniendo fábrica con ID: {}", id);
        return fabricaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fábrica no encontrada con ID: " + id));
    }
    
    public Fabrica actualizarFabrica(String id, FabricaDTO dto) {
        log.info("Actualizando fábrica con ID: {}", id);
        
        Fabrica fabrica = obtenerFabricaPorId(id);
        
        if (dto.getNombre() != null) {
            fabrica.setNombre(dto.getNombre());
        }
        if (dto.getPais() != null) {
            fabrica.setPais(dto.getPais());
        }
        
        Fabrica actualizada = fabricaRepository.save(fabrica);
        log.info("Fábrica actualizada exitosamente");
        return actualizada;
    }
    
    public void eliminarFabrica(String id) {
        log.info("Eliminando fábrica con ID: {}", id);
        
        Fabrica fabrica = obtenerFabricaPorId(id);
        
        // Verificar si tiene plantas asociadas
        List<String> plantasIds = fabrica.getPlantasIds();
        if (plantasIds != null && !plantasIds.isEmpty()) {
            throw new InvalidOperationException("No se puede eliminar la fábrica porque tiene plantas asociadas. " +
                    "Primero debe eliminar todas las plantas.");
        }
        
        fabricaRepository.deleteById(id);
        log.info("Fábrica eliminada exitosamente");
    }
    
    public void agregarPlantaAFabrica(String fabricaId, String plantaId) {
        log.info("Agregando planta {} a fábrica {}", plantaId, fabricaId);
        
        Fabrica fabrica = obtenerFabricaPorId(fabricaId);
        if (!fabrica.getPlantasIds().contains(plantaId)) {
            fabrica.getPlantasIds().add(plantaId);
            fabricaRepository.save(fabrica);
        }
    }
    
    public void removerPlantaDeFabrica(String fabricaId, String plantaId) {
        log.info("Removiendo planta {} de fábrica {}", plantaId, fabricaId);
        
        Fabrica fabrica = obtenerFabricaPorId(fabricaId);
        fabrica.getPlantasIds().remove(plantaId);
        fabricaRepository.save(fabrica);
    }
}
