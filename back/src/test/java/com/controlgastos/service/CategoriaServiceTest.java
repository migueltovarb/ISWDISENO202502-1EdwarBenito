package com.controlgastos.service;

import com.controlgastos.dto.CategoriaDTO;
import com.controlgastos.dto.CategoriaResponseDTO;
import com.controlgastos.exception.DuplicateResourceException;
import com.controlgastos.exception.ResourceNotFoundException;
import com.controlgastos.model.Categoria;
import com.controlgastos.model.User;
import com.controlgastos.repository.CategoriaRepository;
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
 * Pruebas unitarias para CategoriaService
 */
@ExtendWith(MockitoExtension.class)
class CategoriaServiceTest {
    
    @Mock
    private CategoriaRepository categoriaRepository;
    
    @Mock
    private UserRepository userRepository;
    
    @InjectMocks
    private CategoriaService categoriaService;
    
    private User user;
    private Categoria categoria;
    private CategoriaDTO categoriaDTO;
    
    @BeforeEach
    void setUp() {
        user = new User("testuser", "test@example.com", "password123");
        user.setId("user1");
        
        categoria = new Categoria("Alimentos", "user1");
        categoria.setId("cat1");
        
        categoriaDTO = new CategoriaDTO("Alimentos");
    }
    
    @Test
    void crearCategoria_Exitoso() {
        // Arrange
        when(userRepository.findById(anyString())).thenReturn(Optional.of(user));
        when(categoriaRepository.findByNombreAndUserId(anyString(), anyString())).thenReturn(Optional.empty());
        when(categoriaRepository.save(any(Categoria.class))).thenReturn(categoria);
        when(userRepository.save(any(User.class))).thenReturn(user);
        
        // Act
        CategoriaResponseDTO resultado = categoriaService.crearCategoria("user1", categoriaDTO);
        
        // Assert
        assertNotNull(resultado);
        assertEquals("Alimentos", resultado.getNombre());
        assertEquals("user1", resultado.getUserId());
        verify(categoriaRepository, times(1)).save(any(Categoria.class));
    }
    
    @Test
    void crearCategoria_UsuarioNoExiste_LanzaExcepcion() {
        // Arrange
        when(userRepository.findById(anyString())).thenReturn(Optional.empty());
        
        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            categoriaService.crearCategoria("user999", categoriaDTO);
        });
        
        verify(categoriaRepository, never()).save(any(Categoria.class));
    }
    
    @Test
    void crearCategoria_NombreDuplicado_LanzaExcepcion() {
        // Arrange
        when(userRepository.findById(anyString())).thenReturn(Optional.of(user));
        when(categoriaRepository.findByNombreAndUserId(anyString(), anyString()))
                .thenReturn(Optional.of(categoria));
        
        // Act & Assert
        assertThrows(DuplicateResourceException.class, () -> {
            categoriaService.crearCategoria("user1", categoriaDTO);
        });
        
        verify(categoriaRepository, never()).save(any(Categoria.class));
    }
    
    @Test
    void obtenerCategoriasPorUsuario_Exitoso() {
        // Arrange
        Categoria cat2 = new Categoria("Transporte", "user1");
        cat2.setId("cat2");
        
        when(userRepository.existsById(anyString())).thenReturn(true);
        when(categoriaRepository.findByUserId(anyString())).thenReturn(Arrays.asList(categoria, cat2));
        
        // Act
        List<CategoriaResponseDTO> resultado = categoriaService.obtenerCategoriasPorUsuario("user1");
        
        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Alimentos", resultado.get(0).getNombre());
        assertEquals("Transporte", resultado.get(1).getNombre());
    }
    
    @Test
    void obtenerCategoriaPorId_Exitoso() {
        // Arrange
        when(categoriaRepository.findById(anyString())).thenReturn(Optional.of(categoria));
        
        // Act
        CategoriaResponseDTO resultado = categoriaService.obtenerCategoriaPorId("cat1");
        
        // Assert
        assertNotNull(resultado);
        assertEquals("cat1", resultado.getId());
        assertEquals("Alimentos", resultado.getNombre());
    }
    
    @Test
    void actualizarCategoria_Exitoso() {
        // Arrange
        when(categoriaRepository.findById(anyString())).thenReturn(Optional.of(categoria));
        when(categoriaRepository.findByNombreAndUserId(anyString(), anyString()))
                .thenReturn(Optional.empty());
        when(categoriaRepository.save(any(Categoria.class))).thenReturn(categoria);
        
        CategoriaDTO updateDTO = new CategoriaDTO("Comida Rápida");
        
        // Act
        CategoriaResponseDTO resultado = categoriaService.actualizarCategoria("cat1", updateDTO);
        
        // Assert
        assertNotNull(resultado);
        verify(categoriaRepository, times(1)).save(any(Categoria.class));
    }
    
    @Test
    void eliminarCategoria_Exitoso() {
        // Arrange
        when(categoriaRepository.findById(anyString())).thenReturn(Optional.of(categoria));
        when(userRepository.findById(anyString())).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);
        doNothing().when(categoriaRepository).delete(any(Categoria.class));
        
        // Act
        categoriaService.eliminarCategoria("cat1");
        
        // Assert
        verify(categoriaRepository, times(1)).delete(categoria);
    }
}
