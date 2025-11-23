package com.controlgastos.controller;

import com.controlgastos.dto.LoginRequestDTO;
import com.controlgastos.dto.UserRequestDTO;
import com.controlgastos.dto.UserResponseDTO;
import com.controlgastos.service.UserService;
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
 * Controlador REST para la gestión de usuarios
 * Expone endpoints para operaciones CRUD de usuarios
 */
@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
@Tag(name = "Usuarios", description = "API para gestión de usuarios del sistema")
public class UserController {
    
    private final UserService userService;
    
    @Operation(summary = "Iniciar sesión", description = "Autentica un usuario con correo y contraseña")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Inicio de sesión exitoso"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "401", description = "Credenciales incorrectas")
    })
    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> login(
            @Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        
        UserResponseDTO usuario = userService.autenticarUsuario(loginRequestDTO);
        return ResponseEntity.ok(usuario);
    }
    
    @Operation(summary = "Crear un nuevo usuario", description = "Registra un nuevo usuario en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "409", description = "El apodo o correo ya existe")
    })
    @PostMapping
    public ResponseEntity<UserResponseDTO> crearUsuario(
            @Valid @RequestBody UserRequestDTO userRequestDTO) {
        
        UserResponseDTO nuevoUsuario = userService.crearUsuario(userRequestDTO);
        return new ResponseEntity<>(nuevoUsuario, HttpStatus.CREATED);
    }
    
    @Operation(summary = "Obtener todos los usuarios", description = "Retorna la lista de todos los usuarios registrados")
    @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida exitosamente")
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> obtenerTodosUsuarios() {
        List<UserResponseDTO> usuarios = userService.obtenerTodosUsuarios();
        return ResponseEntity.ok(usuarios);
    }
    
    @Operation(summary = "Obtener usuario por ID", description = "Retorna un usuario específico por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> obtenerUsuarioPorId(
            @Parameter(description = "ID del usuario", required = true)
            @PathVariable String id) {
        
        UserResponseDTO usuario = userService.obtenerUsuarioPorId(id);
        return ResponseEntity.ok(usuario);
    }
    
    @Operation(summary = "Obtener usuario por apodo", description = "Busca un usuario por su apodo único")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/apodo/{apodo}")
    public ResponseEntity<UserResponseDTO> obtenerUsuarioPorApodo(
            @Parameter(description = "Apodo del usuario", required = true)
            @PathVariable String apodo) {
        
        UserResponseDTO usuario = userService.obtenerUsuarioPorApodo(apodo);
        return ResponseEntity.ok(usuario);
    }
    
    @Operation(summary = "Actualizar usuario", description = "Actualiza la información de un usuario existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario actualizado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
        @ApiResponse(responseCode = "409", description = "El nuevo apodo o correo ya existe")
    })
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> actualizarUsuario(
            @Parameter(description = "ID del usuario", required = true)
            @PathVariable String id,
            @Valid @RequestBody UserRequestDTO userRequestDTO) {
        
        UserResponseDTO usuarioActualizado = userService.actualizarUsuario(id, userRequestDTO);
        return ResponseEntity.ok(usuarioActualizado);
    }
    
    @Operation(summary = "Eliminar usuario", description = "Elimina un usuario del sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Usuario eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(
            @Parameter(description = "ID del usuario", required = true)
            @PathVariable String id) {
        
        userService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
