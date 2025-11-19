package com.vehiculos.controller;

import com.vehiculos.dto.PlantaDTO;
import com.vehiculos.model.Planta;
import com.vehiculos.service.PlantaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/plantas")
@RequiredArgsConstructor
@Slf4j
public class PlantaController {
    
    private final PlantaService plantaService;
    
    @PostMapping
    public ResponseEntity<Planta> crearPlanta(@Valid @RequestBody PlantaDTO dto) {
        log.info("POST - Crear nueva planta");
        Planta planta = plantaService.crearPlanta(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(planta);
    }
    
    @GetMapping
    public ResponseEntity<List<Planta>> obtenerTodasLasPlantas() {
        log.info("GET - Obtener todas las plantas");
        List<Planta> plantas = plantaService.obtenerTodasLasPlantas();
        return ResponseEntity.ok(plantas);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Planta> obtenerPlantaPorId(@PathVariable String id) {
        log.info("GET - Obtener planta por ID: {}", id);
        Planta planta = plantaService.obtenerPlantaPorId(id);
        return ResponseEntity.ok(planta);
    }
    
    @GetMapping("/fabrica/{fabricaId}")
    public ResponseEntity<List<Planta>> obtenerPlantasPorFabrica(@PathVariable String fabricaId) {
        log.info("GET - Obtener plantas de la fábrica: {}", fabricaId);
        List<Planta> plantas = plantaService.obtenerPlantasPorFabrica(fabricaId);
        return ResponseEntity.ok(plantas);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Planta> actualizarPlanta(
            @PathVariable String id,
            @Valid @RequestBody PlantaDTO dto) {
        log.info("PUT - Actualizar planta con ID: {}", id);
        Planta planta = plantaService.actualizarPlanta(id, dto);
        return ResponseEntity.ok(planta);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPlanta(@PathVariable String id) {
        log.info("DELETE - Eliminar planta con ID: {}", id);
        plantaService.eliminarPlanta(id);
        return ResponseEntity.noContent().build();
    }
}
