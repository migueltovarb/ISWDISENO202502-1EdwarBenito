package com.controlgastos.service;

import com.controlgastos.dto.UserRequestDTO;
import com.controlgastos.dto.UserResponseDTO;
import com.controlgastos.exception.DuplicateResourceException;
import com.controlgastos.exception.ResourceNotFoundException;
import com.controlgastos.model.User;
import com.controlgastos.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias para UserService
 * Utiliza JUnit 5 y Mockito para simular dependencias
 */
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    
    @Mock
    private UserRepository userRepository;
    
    @InjectMocks
    private UserService userService;
    
    private User user;
    private UserRequestDTO userRequestDTO;
    
    @BeforeEach
    void setUp() {
        user = new User("testuser", "test@example.com", "password123");
        user.setId("1");
        
    userRequestDTO = new UserRequestDTO();
    userRequestDTO.setApodo("testuser");
    userRequestDTO.setCorreo("test@example.com");
    userRequestDTO.setContrasenia("password123");
    }
    
    @Test
    void crearUsuario_Exitoso() {
        // Arrange
        when(userRepository.existsByApodo(anyString())).thenReturn(false);
        when(userRepository.existsByCorreo(anyString())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(user);
        
        // Act
        UserResponseDTO resultado = userService.crearUsuario(userRequestDTO);
        
        // Assert
        assertNotNull(resultado);
        assertEquals("testuser", resultado.getApodo());
        assertEquals("test@example.com", resultado.getCorreo());
        verify(userRepository, times(1)).save(any(User.class));
    }
    
    @Test
    void crearUsuario_ApodoDuplicado_LanzaExcepcion() {
        // Arrange
        when(userRepository.existsByApodo(anyString())).thenReturn(true);
        
        // Act & Assert
        assertThrows(DuplicateResourceException.class, () -> {
            userService.crearUsuario(userRequestDTO);
        });
        
        verify(userRepository, never()).save(any(User.class));
    }
    
    @Test
    void crearUsuario_CorreoDuplicado_LanzaExcepcion() {
        // Arrange
        when(userRepository.existsByApodo(anyString())).thenReturn(false);
        when(userRepository.existsByCorreo(anyString())).thenReturn(true);
        
        // Act & Assert
        assertThrows(DuplicateResourceException.class, () -> {
            userService.crearUsuario(userRequestDTO);
        });
        
        verify(userRepository, never()).save(any(User.class));
    }
    
    @Test
    void obtenerUsuarioPorId_Exitoso() {
        // Arrange
        when(userRepository.findById(anyString())).thenReturn(Optional.of(user));
        
        // Act
        UserResponseDTO resultado = userService.obtenerUsuarioPorId("1");
        
        // Assert
        assertNotNull(resultado);
        assertEquals("1", resultado.getId());
        assertEquals("testuser", resultado.getApodo());
    }
    
    @Test
    void obtenerUsuarioPorId_NoEncontrado_LanzaExcepcion() {
        // Arrange
        when(userRepository.findById(anyString())).thenReturn(Optional.empty());
        
        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            userService.obtenerUsuarioPorId("999");
        });
    }
    
    @Test
    void obtenerTodosUsuarios_Exitoso() {
        // Arrange
        User user2 = new User("user2", "user2@example.com", "pass456");
        user2.setId("2");
        when(userRepository.findAll()).thenReturn(Arrays.asList(user, user2));
        
        // Act
        List<UserResponseDTO> resultado = userService.obtenerTodosUsuarios();
        
        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("testuser", resultado.get(0).getApodo());
        assertEquals("user2", resultado.get(1).getApodo());
    }
    
    @Test
    void actualizarUsuario_Exitoso() {
        // Arrange
        when(userRepository.findById(anyString())).thenReturn(Optional.of(user));
        when(userRepository.existsByApodo(anyString())).thenReturn(false);
        when(userRepository.existsByCorreo(anyString())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(user);
        
    UserRequestDTO updateDTO = new UserRequestDTO();
    updateDTO.setApodo("updateduser");
    updateDTO.setCorreo("updated@example.com");
    updateDTO.setContrasenia("newpass123");
        
        // Act
        UserResponseDTO resultado = userService.actualizarUsuario("1", updateDTO);
        
        // Assert
        assertNotNull(resultado);
        verify(userRepository, times(1)).save(any(User.class));
    }
    
    @Test
    void eliminarUsuario_Exitoso() {
        // Arrange
        when(userRepository.findById(anyString())).thenReturn(Optional.of(user));
        doNothing().when(userRepository).delete(any(User.class));
        
        // Act
        userService.eliminarUsuario("1");
        
        // Assert
        verify(userRepository, times(1)).delete(user);
    }
    
    @Test
    void eliminarUsuario_NoEncontrado_LanzaExcepcion() {
        // Arrange
        when(userRepository.findById(anyString())).thenReturn(Optional.empty());
        
        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            userService.eliminarUsuario("999");
        });
        
        verify(userRepository, never()).delete(any(User.class));
    }
}
