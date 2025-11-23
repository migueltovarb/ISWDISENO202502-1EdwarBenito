package com.controlgastos.controller;

import com.controlgastos.dto.CategoriaDTO;
import com.controlgastos.dto.CategoriaResponseDTO;
import com.controlgastos.service.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controlador REST para la gestión de categorías
 * Expone endpoints para operaciones CRUD de categorías
 */
@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
@Tag(name = "Categorías", description = "API para gestión de categorías de transacciones")
public class CategoriaController {
    
    private final CategoriaService categoriaService;
    
    @Operation(summary = "Crear nueva categoría", description = "Crea una nueva categoría para un usuario específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Categoría creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
        @ApiResponse(responseCode = "409", description = "La categoría ya existe para este usuario")
    })
    @PostMapping("/usuario/{userId}")
    public ResponseEntity<CategoriaResponseDTO> crearCategoria(
            @Parameter(description = "ID del usuario propietario", required = true)
            @PathVariable String userId,
            @Valid @RequestBody CategoriaDTO categoriaDTO) {
        
        CategoriaResponseDTO nuevaCategoria = categoriaService.crearCategoria(userId, categoriaDTO);
        return new ResponseEntity<>(nuevaCategoria, HttpStatus.CREATED);
    }
    
    @Operation(summary = "Obtener categorías de un usuario", description = "Retorna todas las categorías de un usuario específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de categorías obtenida exitosamente"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/usuario/{userId}")
    public ResponseEntity<List<CategoriaResponseDTO>> obtenerCategoriasPorUsuario(
            @Parameter(description = "ID del usuario", required = true)
            @PathVariable String userId) {
        
        List<CategoriaResponseDTO> categorias = categoriaService.obtenerCategoriasPorUsuario(userId);
        return ResponseEntity.ok(categorias);
    }
    
    @Operation(summary = "Obtener categoría por ID", description = "Retorna una categoría específica por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categoría encontrada"),
        @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> obtenerCategoriaPorId(
            @Parameter(description = "ID de la categoría", required = true)
            @PathVariable String id) {
        
        CategoriaResponseDTO categoria = categoriaService.obtenerCategoriaPorId(id);
        return ResponseEntity.ok(categoria);
    }
    
    @Operation(summary = "Actualizar categoría", description = "Actualiza la información de una categoría existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categoría actualizada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "404", description = "Categoría no encontrada"),
        @ApiResponse(responseCode = "409", description = "El nuevo nombre ya existe")
    })
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> actualizarCategoria(
            @Parameter(description = "ID de la categoría", required = true)
            @PathVariable String id,
            @Valid @RequestBody CategoriaDTO categoriaDTO) {
        
        CategoriaResponseDTO categoriaActualizada = categoriaService.actualizarCategoria(id, categoriaDTO);
        return ResponseEntity.ok(categoriaActualizada);
    }
    
    @Operation(summary = "Eliminar categoría", description = "Elimina una categoría del sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Categoría eliminada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCategoria(
            @Parameter(description = "ID de la categoría", required = true)
            @PathVariable String id) {
        
        categoriaService.eliminarCategoria(id);
        return ResponseEntity.noContent().build();
    }
}
