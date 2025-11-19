package com.vehiculos.controller;

import com.vehiculos.dto.VehiculoDTO;
import com.vehiculos.model.Vehiculo;
import com.vehiculos.service.VehiculoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/vehiculos")
@RequiredArgsConstructor
@Slf4j
public class VehiculoController {
    
    private final VehiculoService vehiculoService;
    
    @PostMapping
    public ResponseEntity<Vehiculo> crearVehiculo(@Valid @RequestBody VehiculoDTO dto) {
        log.info("POST - Crear nuevo vehículo");
        Vehiculo vehiculo = vehiculoService.crearVehiculo(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(vehiculo);
    }
    
    @GetMapping
    public ResponseEntity<List<Vehiculo>> obtenerTodosLosVehiculos() {
        log.info("GET - Obtener todos los vehículos");
        List<Vehiculo> vehiculos = vehiculoService.obtenerTodosLosVehiculos();
        return ResponseEntity.ok(vehiculos);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Vehiculo> obtenerVehiculoPorId(@PathVariable String id) {
        log.info("GET - Obtener vehículo por ID: {}", id);
        Vehiculo vehiculo = vehiculoService.obtenerVehiculoPorId(id);
        return ResponseEntity.ok(vehiculo);
    }
    
    @GetMapping("/planta/{plantaId}")
    public ResponseEntity<List<Vehiculo>> obtenerVehiculosPorPlanta(@PathVariable String plantaId) {
        log.info("GET - Obtener vehículos de la planta: {}", plantaId);
        List<Vehiculo> vehiculos = vehiculoService.obtenerVehiculosPorPlanta(plantaId);
        return ResponseEntity.ok(vehiculos);
    }
    
    @GetMapping("/marca/{marca}")
    public ResponseEntity<List<Vehiculo>> obtenerVehiculosPorMarca(@PathVariable String marca) {
        log.info("GET - Obtener vehículos de la marca: {}", marca);
        List<Vehiculo> vehiculos = vehiculoService.obtenerVehiculosPorMarca(marca);
        return ResponseEntity.ok(vehiculos);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Vehiculo> actualizarVehiculo(
            @PathVariable String id,
            @Valid @RequestBody VehiculoDTO dto) {
        log.info("PUT - Actualizar vehículo con ID: {}", id);
        Vehiculo vehiculo = vehiculoService.actualizarVehiculo(id, dto);
        return ResponseEntity.ok(vehiculo);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarVehiculo(@PathVariable String id) {
        log.info("DELETE - Eliminar vehículo con ID: {}", id);
        vehiculoService.eliminarVehiculo(id);
        return ResponseEntity.noContent().build();
    }
}
