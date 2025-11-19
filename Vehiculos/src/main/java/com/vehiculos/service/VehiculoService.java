package com.vehiculos.service;

import com.vehiculos.dto.VehiculoDTO;
import com.vehiculos.exception.InvalidOperationException;
import com.vehiculos.exception.ResourceNotFoundException;
import com.vehiculos.model.Vehiculo;
import com.vehiculos.repository.VehiculoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class VehiculoService {
    
    private final VehiculoRepository vehiculoRepository;
    private final PlantaService plantaService;
    
    public Vehiculo crearVehiculo(VehiculoDTO dto) {
        log.info("Creando nuevo vehículo: {} {}", dto.getMarca(), dto.getModelo());
        
        // Validar que plantaId no sea nulo o vacío
        if (dto.getPlantaId() == null || dto.getPlantaId().isBlank()) {
            throw new InvalidOperationException("El ID de la planta es requerido");
        }
        
        // Verificar que la planta existe
        plantaService.obtenerPlantaPorId(dto.getPlantaId());
        
        // Validaciones adicionales
        if (dto.getNumeroPuertas() <= 0) {
            throw new InvalidOperationException("El número de puertas debe ser mayor a 0");
        }
        
        Vehiculo vehiculo = new Vehiculo(
                dto.getMarca(),
                dto.getModelo(),
                dto.getTipoLlantas(),
                dto.getNumeroPuertas(),
                dto.getPlantaId()
        );
        
        Vehiculo guardado = vehiculoRepository.save(vehiculo);
        log.info("Vehículo creado exitosamente con ID: {}", guardado.getId());
        return guardado;
    }
    
    public List<Vehiculo> obtenerTodosLosVehiculos() {
        log.info("Obteniendo todos los vehículos");
        return vehiculoRepository.findAll();
    }
    
    public Vehiculo obtenerVehiculoPorId(String id) {
        log.info("Obteniendo vehículo con ID: {}", id);
        return vehiculoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehículo no encontrado con ID: " + id));
    }
    
    public List<Vehiculo> obtenerVehiculosPorPlanta(String plantaId) {
        log.info("Obteniendo vehículos de la planta: {}", plantaId);
        
        // Verificar que la planta existe
        plantaService.obtenerPlantaPorId(plantaId);
        
        return vehiculoRepository.findByPlantaId(plantaId);
    }
    
    public List<Vehiculo> obtenerVehiculosPorMarca(String marca) {
        log.info("Obteniendo vehículos de la marca: {}", marca);
        return vehiculoRepository.findByMarca(marca);
    }
    
    public Vehiculo actualizarVehiculo(String id, VehiculoDTO dto) {
        log.info("Actualizando vehículo con ID: {}", id);
        
        Vehiculo vehiculo = obtenerVehiculoPorId(id);
        
        if (dto.getMarca() != null) {
            vehiculo.setMarca(dto.getMarca());
        }
        if (dto.getModelo() != null) {
            vehiculo.setModelo(dto.getModelo());
        }
        if (dto.getTipoLlantas() != null) {
            vehiculo.setTipoLlantas(dto.getTipoLlantas());
        }
        if (dto.getNumeroPuertas() != null) {
            if (dto.getNumeroPuertas() <= 0) {
                throw new InvalidOperationException("El número de puertas debe ser mayor a 0");
            }
            vehiculo.setNumeroPuertas(dto.getNumeroPuertas());
        }
        
        Vehiculo actualizado = vehiculoRepository.save(vehiculo);
        log.info("Vehículo actualizado exitosamente");
        return actualizado;
    }
    
    public void eliminarVehiculo(String id) {
        log.info("Eliminando vehículo con ID: {}", id);
        
        obtenerVehiculoPorId(id);
        vehiculoRepository.deleteById(id);
        
        log.info("Vehículo eliminado exitosamente");
    }
}
