package com.controlgastos.controller;

import com.controlgastos.dto.ResumenGastosDTO;
import com.controlgastos.dto.TransaccionRequestDTO;
import com.controlgastos.dto.TransaccionResponseDTO;
import com.controlgastos.model.TipoTransaccion;
import com.controlgastos.service.TransaccionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Controlador REST para la gestión de transacciones (ingresos y gastos)
 * Expone endpoints para operaciones CRUD y reportes de transacciones
 */
@RestController
@RequestMapping("/api/transacciones")
@RequiredArgsConstructor
@Tag(name = "Transacciones", description = "API para gestión de transacciones (ingresos y gastos)")
public class TransaccionController {
    
    private final TransaccionService transaccionService;
    
    @Operation(summary = "Crear nueva transacción", description = "Registra una nueva transacción (ingreso o gasto) para un usuario")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Transacción creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "404", description = "Usuario o categoría no encontrado")
    })
    @PostMapping("/usuario/{userId}")
    public ResponseEntity<TransaccionResponseDTO> crearTransaccion(
            @Parameter(description = "ID del usuario propietario", required = true)
            @PathVariable String userId,
            @Valid @RequestBody TransaccionRequestDTO transaccionDTO) {
        
        TransaccionResponseDTO nuevaTransaccion = transaccionService.crearTransaccion(userId, transaccionDTO);
        return new ResponseEntity<>(nuevaTransaccion, HttpStatus.CREATED);
    }
    
    @Operation(summary = "Obtener transacciones de un usuario", description = "Retorna todas las transacciones de un usuario específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de transacciones obtenida exitosamente"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/usuario/{userId}")
    public ResponseEntity<List<TransaccionResponseDTO>> obtenerTransaccionesPorUsuario(
            @Parameter(description = "ID del usuario", required = true)
            @PathVariable String userId) {
        
        List<TransaccionResponseDTO> transacciones = transaccionService.obtenerTransaccionesPorUsuario(userId);
        return ResponseEntity.ok(transacciones);
    }
    
    @Operation(summary = "Obtener transacciones por tipo", description = "Filtra transacciones de un usuario por tipo (INGRESO o GASTO)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de transacciones filtradas"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/usuario/{userId}/tipo/{tipo}")
    public ResponseEntity<List<TransaccionResponseDTO>> obtenerTransaccionesPorTipo(
            @Parameter(description = "ID del usuario", required = true)
            @PathVariable String userId,
            @Parameter(description = "Tipo de transacción: INGRESO o GASTO", required = true)
            @PathVariable TipoTransaccion tipo) {
        
        List<TransaccionResponseDTO> transacciones = transaccionService.obtenerTransaccionesPorTipo(userId, tipo);
        return ResponseEntity.ok(transacciones);
    }
    
    @Operation(summary = "Obtener transacciones por rango de fechas", 
               description = "Filtra transacciones de un usuario por rango de fechas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de transacciones en el rango"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/usuario/{userId}/fecha")
    public ResponseEntity<List<TransaccionResponseDTO>> obtenerTransaccionesPorFecha(
            @Parameter(description = "ID del usuario", required = true)
            @PathVariable String userId,
            @Parameter(description = "Fecha de inicio (formato: yyyy-MM-dd'T'HH:mm:ss)", required = true)
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @Parameter(description = "Fecha de fin (formato: yyyy-MM-dd'T'HH:mm:ss)", required = true)
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin) {
        
        List<TransaccionResponseDTO> transacciones = transaccionService.obtenerTransaccionesPorFecha(
                userId, fechaInicio, fechaFin);
        return ResponseEntity.ok(transacciones);
    }
    
    @Operation(summary = "Obtener transacciones por categoría", 
               description = "Filtra transacciones de un usuario por categoría")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de transacciones de la categoría"),
        @ApiResponse(responseCode = "404", description = "Usuario o categoría no encontrado")
    })
    @GetMapping("/usuario/{userId}/categoria/{categoriaId}")
    public ResponseEntity<List<TransaccionResponseDTO>> obtenerTransaccionesPorCategoria(
            @Parameter(description = "ID del usuario", required = true)
            @PathVariable String userId,
            @Parameter(description = "ID de la categoría", required = true)
            @PathVariable String categoriaId) {
        
        List<TransaccionResponseDTO> transacciones = transaccionService.obtenerTransaccionesPorCategoria(
                userId, categoriaId);
        return ResponseEntity.ok(transacciones);
    }
    
    @Operation(summary = "Obtener transacción por ID", description = "Retorna una transacción específica por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Transacción encontrada"),
        @ApiResponse(responseCode = "404", description = "Transacción no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<TransaccionResponseDTO> obtenerTransaccionPorId(
            @Parameter(description = "ID de la transacción", required = true)
            @PathVariable String id) {
        
        TransaccionResponseDTO transaccion = transaccionService.obtenerTransaccionPorId(id);
        return ResponseEntity.ok(transaccion);
    }
    
    @Operation(summary = "Actualizar transacción", description = "Actualiza la información de una transacción existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Transacción actualizada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "404", description = "Transacción no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<TransaccionResponseDTO> actualizarTransaccion(
            @Parameter(description = "ID de la transacción", required = true)
            @PathVariable String id,
            @Valid @RequestBody TransaccionRequestDTO transaccionDTO) {
        
        TransaccionResponseDTO transaccionActualizada = transaccionService.actualizarTransaccion(id, transaccionDTO);
        return ResponseEntity.ok(transaccionActualizada);
    }
    
    @Operation(summary = "Eliminar transacción", description = "Elimina una transacción del sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Transacción eliminada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Transacción no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTransaccion(
            @Parameter(description = "ID de la transacción", required = true)
            @PathVariable String id) {
        
        transaccionService.eliminarTransaccion(id);
        return ResponseEntity.noContent().build();
    }
    
    @Operation(summary = "Calcular resumen de gastos e ingresos", 
               description = "Genera un resumen con totales de ingresos, gastos y balance de un usuario")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Resumen calculado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/usuario/{userId}/resumen")
    public ResponseEntity<ResumenGastosDTO> calcularResumen(
            @Parameter(description = "ID del usuario", required = true)
            @PathVariable String userId) {
        
        ResumenGastosDTO resumen = transaccionService.calcularResumen(userId);
        return ResponseEntity.ok(resumen);
    }
    
    @Operation(summary = "Calcular resumen por periodo", 
               description = "Genera un resumen de gastos e ingresos en un rango de fechas específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Resumen del periodo calculado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/usuario/{userId}/resumen/periodo")
    public ResponseEntity<ResumenGastosDTO> calcularResumenPorFecha(
            @Parameter(description = "ID del usuario", required = true)
            @PathVariable String userId,
            @Parameter(description = "Fecha de inicio (formato: yyyy-MM-dd'T'HH:mm:ss)", required = true)
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @Parameter(description = "Fecha de fin (formato: yyyy-MM-dd'T'HH:mm:ss)", required = true)
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin) {
        
        ResumenGastosDTO resumen = transaccionService.calcularResumenPorFecha(userId, fechaInicio, fechaFin);
        return ResponseEntity.ok(resumen);
    }
}
