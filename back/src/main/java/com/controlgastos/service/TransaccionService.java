package com.controlgastos.service;

import com.controlgastos.dto.ResumenGastosDTO;
import com.controlgastos.dto.TransaccionRequestDTO;
import com.controlgastos.dto.TransaccionResponseDTO;
import com.controlgastos.exception.ResourceNotFoundException;
import com.controlgastos.model.Categoria;
import com.controlgastos.model.TipoTransaccion;
import com.controlgastos.model.Transaccion;
import com.controlgastos.model.User;
import com.controlgastos.repository.CategoriaRepository;
import com.controlgastos.repository.TransaccionRepository;
import com.controlgastos.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio para la gestión de transacciones
 * Contiene toda la lógica de negocio relacionada con transacciones (ingresos y gastos)
 */
@Service
@RequiredArgsConstructor
public class TransaccionService {
    
    private final TransaccionRepository transaccionRepository;
    private final UserRepository userRepository;
    private final CategoriaRepository categoriaRepository;
    
    /**
     * Crea una nueva transacción para un usuario
     * @param userId ID del usuario
     * @param transaccionDTO datos de la transacción
     * @return transacción creada
     */
    @Transactional
    public TransaccionResponseDTO crearTransaccion(String userId, TransaccionRequestDTO transaccionDTO) {
        // Verificar que el usuario existe
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", userId));
        
        // Verificar que la categoría existe y pertenece al usuario
        Categoria categoria = categoriaRepository.findById(transaccionDTO.getCategoriaId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoría", "id", transaccionDTO.getCategoriaId()));
        
        if (!categoria.getUserId().equals(userId)) {
            throw new IllegalArgumentException("La categoría no pertenece al usuario especificado");
        }
        
        // Crear y guardar la transacción
        Transaccion transaccion = new Transaccion(
                transaccionDTO.getTipoTransaccion(),
                transaccionDTO.getCategoriaId(),
                categoria.getNombre(),
                transaccionDTO.getDescripcion(),
                transaccionDTO.getFecha(),
                transaccionDTO.getMonto(),
                userId
        );
        
        Transaccion savedTransaccion = transaccionRepository.save(transaccion);
        
        // Actualizar el usuario con la nueva transacción
        user.agregarTransaccion(savedTransaccion.getId());
        userRepository.save(user);
        
        return convertToDTO(savedTransaccion);
    }
    
    /**
     * Obtiene todas las transacciones de un usuario
     * @param userId ID del usuario
     * @return lista de transacciones
     */
    public List<TransaccionResponseDTO> obtenerTransaccionesPorUsuario(String userId) {
        // Verificar que el usuario existe
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("Usuario", "id", userId);
        }
        
        return transaccionRepository.findByUserId(userId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Obtiene transacciones filtradas por tipo
     * @param userId ID del usuario
     * @param tipo tipo de transacción (INGRESO o GASTO)
     * @return lista de transacciones filtradas
     */
    public List<TransaccionResponseDTO> obtenerTransaccionesPorTipo(String userId, TipoTransaccion tipo) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("Usuario", "id", userId);
        }
        
        return transaccionRepository.findByUserIdAndTipoTransaccion(userId, tipo).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Obtiene transacciones por rango de fechas
     * @param userId ID del usuario
     * @param fechaInicio fecha de inicio
     * @param fechaFin fecha de fin
     * @return lista de transacciones en el rango
     */
    public List<TransaccionResponseDTO> obtenerTransaccionesPorFecha(
            String userId, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("Usuario", "id", userId);
        }
        
        return transaccionRepository.findByUserIdAndFechaBetween(userId, fechaInicio, fechaFin).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Obtiene transacciones por categoría
     * @param userId ID del usuario
     * @param categoriaId ID de la categoría
     * @return lista de transacciones de la categoría
     */
    public List<TransaccionResponseDTO> obtenerTransaccionesPorCategoria(String userId, String categoriaId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("Usuario", "id", userId);
        }
        
        if (!categoriaRepository.existsById(categoriaId)) {
            throw new ResourceNotFoundException("Categoría", "id", categoriaId);
        }
        
        return transaccionRepository.findByUserIdAndCategoriaId(userId, categoriaId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Obtiene una transacción por su ID
     * @param id ID de la transacción
     * @return transacción encontrada
     */
    public TransaccionResponseDTO obtenerTransaccionPorId(String id) {
        Transaccion transaccion = transaccionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transacción", "id", id));
        return convertToDTO(transaccion);
    }
    
    /**
     * Actualiza una transacción existente
     * @param id ID de la transacción
     * @param transaccionDTO nuevos datos
     * @return transacción actualizada
     */
    @Transactional
    public TransaccionResponseDTO actualizarTransaccion(String id, TransaccionRequestDTO transaccionDTO) {
        Transaccion transaccion = transaccionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transacción", "id", id));
        
        // Verificar que la nueva categoría existe
        Categoria categoria = categoriaRepository.findById(transaccionDTO.getCategoriaId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoría", "id", transaccionDTO.getCategoriaId()));
        
        // Actualizar campos
        transaccion.setTipoTransaccion(transaccionDTO.getTipoTransaccion());
        transaccion.setCategoriaId(transaccionDTO.getCategoriaId());
        transaccion.setCategoriaNombre(categoria.getNombre());
        transaccion.setDescripcion(transaccionDTO.getDescripcion());
        transaccion.setFecha(transaccionDTO.getFecha());
        transaccion.setMonto(transaccionDTO.getMonto());
        
        Transaccion updatedTransaccion = transaccionRepository.save(transaccion);
        return convertToDTO(updatedTransaccion);
    }
    
    /**
     * Elimina una transacción
     * @param id ID de la transacción
     */
    @Transactional
    public void eliminarTransaccion(String id) {
        Transaccion transaccion = transaccionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transacción", "id", id));
        
        // Actualizar el usuario eliminando la referencia
        User user = userRepository.findById(transaccion.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", transaccion.getUserId()));
        user.eliminarTransaccion(id);
        userRepository.save(user);
        
        // Eliminar la transacción
        transaccionRepository.delete(transaccion);
    }
    
    /**
     * Calcula el resumen de gastos e ingresos de un usuario
     * @param userId ID del usuario
     * @return resumen con totales y balance
     */
    public ResumenGastosDTO calcularResumen(String userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("Usuario", "id", userId);
        }
        
        List<Transaccion> ingresos = transaccionRepository.findByUserIdAndTipoTransaccion(
                userId, TipoTransaccion.INGRESO);
        List<Transaccion> gastos = transaccionRepository.findByUserIdAndTipoTransaccion(
                userId, TipoTransaccion.GASTO);
        
        double totalIngresos = ingresos.stream()
                .mapToDouble(Transaccion::getMonto)
                .sum();
        
        double totalGastos = gastos.stream()
                .mapToDouble(Transaccion::getMonto)
                .sum();
        
        double balance = totalIngresos - totalGastos;
        
        return new ResumenGastosDTO(
                totalIngresos,
                totalGastos,
                balance,
                (long) ingresos.size(),
                (long) gastos.size(),
                "Todos los periodos"
        );
    }
    
    /**
     * Calcula el resumen de gastos e ingresos en un rango de fechas
     * @param userId ID del usuario
     * @param fechaInicio fecha de inicio
     * @param fechaFin fecha de fin
     * @return resumen del periodo
     */
    public ResumenGastosDTO calcularResumenPorFecha(
            String userId, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("Usuario", "id", userId);
        }
        
        List<Transaccion> ingresos = transaccionRepository.findByUserIdAndTipoTransaccionAndFechaBetween(
                userId, TipoTransaccion.INGRESO, fechaInicio, fechaFin);
        List<Transaccion> gastos = transaccionRepository.findByUserIdAndTipoTransaccionAndFechaBetween(
                userId, TipoTransaccion.GASTO, fechaInicio, fechaFin);
        
        double totalIngresos = ingresos.stream()
                .mapToDouble(Transaccion::getMonto)
                .sum();
        
        double totalGastos = gastos.stream()
                .mapToDouble(Transaccion::getMonto)
                .sum();
        
        double balance = totalIngresos - totalGastos;
        
        String periodo = String.format("Desde %s hasta %s", 
                fechaInicio.toLocalDate(), fechaFin.toLocalDate());
        
        return new ResumenGastosDTO(
                totalIngresos,
                totalGastos,
                balance,
                (long) ingresos.size(),
                (long) gastos.size(),
                periodo
        );
    }
    
    /**
     * Convierte una entidad Transaccion a TransaccionResponseDTO
     */
    private TransaccionResponseDTO convertToDTO(Transaccion transaccion) {
        return new TransaccionResponseDTO(
                transaccion.getId(),
                transaccion.getTipoTransaccion(),
                transaccion.getCategoriaId(),
                transaccion.getCategoriaNombre(),
                transaccion.getDescripcion(),
                transaccion.getFecha(),
                transaccion.getMonto(),
                transaccion.getUserId()
        );
    }
}
