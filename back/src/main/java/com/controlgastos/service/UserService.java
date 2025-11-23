package com.controlgastos.service;

import com.controlgastos.dto.*;
import com.controlgastos.exception.DuplicateResourceException;
import com.controlgastos.exception.ResourceNotFoundException;
import com.controlgastos.exception.UnauthorizedException;
import com.controlgastos.model.User;
import com.controlgastos.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio para la gestión de usuarios
 * Contiene toda la lógica de negocio relacionada con usuarios
 */
@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;
    
    /**
     * Autentica un usuario con correo y contrasenia (HU002)
     * @param loginRequestDTO credenciales del usuario
     * @return usuario autenticado
     * @throws UnauthorizedException si las credenciales son incorrectas
     */
    public UserResponseDTO autenticarUsuario(LoginRequestDTO loginRequestDTO) {
        // Buscar usuario por correo
    User user = userRepository.findByCorreo(loginRequestDTO.getCorreo())
        .orElseThrow(() -> new UnauthorizedException("Correo electrónico o contrasenia incorrectos"));

    // Verificar contrasenia (en producción, usar BCrypt)
    if (!user.getContrasenia().equals(loginRequestDTO.getContrasenia())) {
        throw new UnauthorizedException("Correo electrónico o contrasenia incorrectos");
    }
        
        return convertToDTO(user);
    }
    
    /**
     * Crea un nuevo usuario
     * @param userRequestDTO datos del nuevo usuario
     * @return usuario creado
     * @throws DuplicateResourceException si el apodo o correo ya existe
     */
    @Transactional
    public UserResponseDTO crearUsuario(UserRequestDTO userRequestDTO) {
        // Verificar que el apodo no exista
        if (userRepository.existsByApodo(userRequestDTO.getApodo())) {
            throw new DuplicateResourceException("Usuario", "apodo", userRequestDTO.getApodo());
        }
        
        // Verificar que el correo no exista
        if (userRepository.existsByCorreo(userRequestDTO.getCorreo())) {
            throw new DuplicateResourceException("Usuario", "correo", userRequestDTO.getCorreo());
        }
        
        // Crear y guardar el usuario
    User user = new User(
        userRequestDTO.getApodo(),
        userRequestDTO.getCorreo(),
        userRequestDTO.getContrasenia() // En producción, encriptar la contrasenia
    );
        
        User savedUser = userRepository.save(user);
        return convertToDTO(savedUser);
    }
    
    /**
     * Obtiene todos los usuarios
     * @return lista de usuarios
     */
    public List<UserResponseDTO> obtenerTodosUsuarios() {
        return userRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Obtiene un usuario por su ID
     * @param id ID del usuario
     * @return usuario encontrado
     * @throws ResourceNotFoundException si no se encuentra el usuario
     */
    public UserResponseDTO obtenerUsuarioPorId(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", id));
        return convertToDTO(user);
    }
    
    /**
     * Obtiene un usuario por su apodo
     * @param apodo apodo del usuario
     * @return usuario encontrado
     */
    public UserResponseDTO obtenerUsuarioPorApodo(String apodo) {
        User user = userRepository.findByApodo(apodo)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "apodo", apodo));
        return convertToDTO(user);
    }
    
    /**
     * Actualiza un usuario existente
     * @param id ID del usuario a actualizar
     * @param userRequestDTO nuevos datos del usuario
     * @return usuario actualizado
     */
    @Transactional
    public UserResponseDTO actualizarUsuario(String id, UserRequestDTO userRequestDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", id));
        
        // Verificar si el nuevo apodo ya existe (y no es del mismo usuario)
        if (!user.getApodo().equals(userRequestDTO.getApodo()) 
                && userRepository.existsByApodo(userRequestDTO.getApodo())) {
            throw new DuplicateResourceException("Usuario", "apodo", userRequestDTO.getApodo());
        }
        
        // Verificar si el nuevo correo ya existe (y no es del mismo usuario)
        if (!user.getCorreo().equals(userRequestDTO.getCorreo()) 
                && userRepository.existsByCorreo(userRequestDTO.getCorreo())) {
            throw new DuplicateResourceException("Usuario", "correo", userRequestDTO.getCorreo());
        }
        
        // Actualizar campos
        user.setApodo(userRequestDTO.getApodo());
        user.setCorreo(userRequestDTO.getCorreo());
        if (userRequestDTO.getContrasenia() != null && !userRequestDTO.getContrasenia().isEmpty()) {
            user.setContrasenia(userRequestDTO.getContrasenia()); // En producción, encriptar
        }
        
        User updatedUser = userRepository.save(user);
        return convertToDTO(updatedUser);
    }
    
    /**
     * Elimina un usuario
     * @param id ID del usuario a eliminar
     */
    @Transactional
    public void eliminarUsuario(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", id));
        userRepository.delete(user);
    }
    
    /**
     * Convierte una entidad User a UserResponseDTO
     */
    private UserResponseDTO convertToDTO(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getApodo(),
                user.getCorreo(),
                user.getTransaccionesIds(),
                user.getCategoriasIds(),
                user.getTransaccionesIds() != null ? user.getTransaccionesIds().size() : 0,
                user.getCategoriasIds() != null ? user.getCategoriasIds().size() : 0
        );
    }
}
