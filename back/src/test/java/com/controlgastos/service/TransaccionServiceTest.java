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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias para TransaccionService
 */
@ExtendWith(MockitoExtension.class)
class TransaccionServiceTest {
    
    @Mock
    private TransaccionRepository transaccionRepository;
    
    @Mock
    private UserRepository userRepository;
    
    @Mock
    private CategoriaRepository categoriaRepository;
    
    @InjectMocks
    private TransaccionService transaccionService;
    
    private User user;
    private Categoria categoria;
    private Transaccion transaccion;
    private TransaccionRequestDTO transaccionRequestDTO;
    
    @BeforeEach
    void setUp() {
        user = new User("testuser", "test@example.com", "password123");
        user.setId("user1");
        
        categoria = new Categoria("Alimentos", "user1");
        categoria.setId("cat1");
        
        transaccion = new Transaccion(
                TipoTransaccion.GASTO,
                "cat1",
                "Alimentos",
                "Compra de supermercado",
                LocalDateTime.now(),
                150.50,
                "user1"
        );
        transaccion.setId("trans1");
        
        transaccionRequestDTO = new TransaccionRequestDTO();
        transaccionRequestDTO.setTipoTransaccion(TipoTransaccion.GASTO);
        transaccionRequestDTO.setCategoriaId("cat1");
        transaccionRequestDTO.setDescripcion("Compra de supermercado");
        transaccionRequestDTO.setFecha(LocalDateTime.now());
        transaccionRequestDTO.setMonto(150.50);
    }
    
    @Test
    void crearTransaccion_Exitoso() {
        // Arrange
        when(userRepository.findById(anyString())).thenReturn(Optional.of(user));
        when(categoriaRepository.findById(anyString())).thenReturn(Optional.of(categoria));
        when(transaccionRepository.save(any(Transaccion.class))).thenReturn(transaccion);
        when(userRepository.save(any(User.class))).thenReturn(user);
        
        // Act
        TransaccionResponseDTO resultado = transaccionService.crearTransaccion("user1", transaccionRequestDTO);
        
        // Assert
        assertNotNull(resultado);
        assertEquals(TipoTransaccion.GASTO, resultado.getTipoTransaccion());
        assertEquals(150.50, resultado.getMonto());
        verify(transaccionRepository, times(1)).save(any(Transaccion.class));
    }
    
    @Test
    void crearTransaccion_UsuarioNoExiste_LanzaExcepcion() {
        // Arrange
        when(userRepository.findById(anyString())).thenReturn(Optional.empty());
        
        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            transaccionService.crearTransaccion("user999", transaccionRequestDTO);
        });
        
        verify(transaccionRepository, never()).save(any(Transaccion.class));
    }
    
    @Test
    void crearTransaccion_CategoriaNoExiste_LanzaExcepcion() {
        // Arrange
        when(userRepository.findById(anyString())).thenReturn(Optional.of(user));
        when(categoriaRepository.findById(anyString())).thenReturn(Optional.empty());
        
        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            transaccionService.crearTransaccion("user1", transaccionRequestDTO);
        });
        
        verify(transaccionRepository, never()).save(any(Transaccion.class));
    }
    
    @Test
    void obtenerTransaccionesPorUsuario_Exitoso() {
        // Arrange
        Transaccion trans2 = new Transaccion(
                TipoTransaccion.INGRESO,
                "cat1",
                "Alimentos",
                "Venta",
                LocalDateTime.now(),
                500.0,
                "user1"
        );
        trans2.setId("trans2");
        
        when(userRepository.existsById(anyString())).thenReturn(true);
        when(transaccionRepository.findByUserId(anyString()))
                .thenReturn(Arrays.asList(transaccion, trans2));
        
        // Act
        List<TransaccionResponseDTO> resultado = transaccionService.obtenerTransaccionesPorUsuario("user1");
        
        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals(TipoTransaccion.GASTO, resultado.get(0).getTipoTransaccion());
        assertEquals(TipoTransaccion.INGRESO, resultado.get(1).getTipoTransaccion());
    }
    
    @Test
    void obtenerTransaccionesPorTipo_Exitoso() {
        // Arrange
        when(userRepository.existsById(anyString())).thenReturn(true);
        when(transaccionRepository.findByUserIdAndTipoTransaccion(anyString(), any(TipoTransaccion.class)))
                .thenReturn(Arrays.asList(transaccion));
        
        // Act
        List<TransaccionResponseDTO> resultado = transaccionService.obtenerTransaccionesPorTipo(
                "user1", TipoTransaccion.GASTO);
        
        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(TipoTransaccion.GASTO, resultado.get(0).getTipoTransaccion());
    }
    
    @Test
    void calcularResumen_Exitoso() {
        // Arrange
        Transaccion ingreso1 = new Transaccion(
                TipoTransaccion.INGRESO, "cat1", "Ingresos", 
                "Salario", LocalDateTime.now(), 1000.0, "user1");
        Transaccion gasto1 = new Transaccion(
                TipoTransaccion.GASTO, "cat1", "Gastos", 
                "Compra", LocalDateTime.now(), 300.0, "user1");
        
        when(userRepository.existsById(anyString())).thenReturn(true);
        when(transaccionRepository.findByUserIdAndTipoTransaccion(anyString(), eq(TipoTransaccion.INGRESO)))
                .thenReturn(Arrays.asList(ingreso1));
        when(transaccionRepository.findByUserIdAndTipoTransaccion(anyString(), eq(TipoTransaccion.GASTO)))
                .thenReturn(Arrays.asList(gasto1));
        
        // Act
        ResumenGastosDTO resultado = transaccionService.calcularResumen("user1");
        
        // Assert
        assertNotNull(resultado);
        assertEquals(1000.0, resultado.getTotalIngresos());
        assertEquals(300.0, resultado.getTotalGastos());
        assertEquals(700.0, resultado.getBalance());
        assertEquals(1L, resultado.getCantidadIngresos());
        assertEquals(1L, resultado.getCantidadGastos());
    }
    
    @Test
    void obtenerTransaccionPorId_Exitoso() {
        // Arrange
        when(transaccionRepository.findById(anyString())).thenReturn(Optional.of(transaccion));
        
        // Act
        TransaccionResponseDTO resultado = transaccionService.obtenerTransaccionPorId("trans1");
        
        // Assert
        assertNotNull(resultado);
        assertEquals("trans1", resultado.getId());
        assertEquals("Compra de supermercado", resultado.getDescripcion());
    }
    
    @Test
    void actualizarTransaccion_Exitoso() {
        // Arrange
        when(transaccionRepository.findById(anyString())).thenReturn(Optional.of(transaccion));
        when(categoriaRepository.findById(anyString())).thenReturn(Optional.of(categoria));
        when(transaccionRepository.save(any(Transaccion.class))).thenReturn(transaccion);
        
        TransaccionRequestDTO updateDTO = new TransaccionRequestDTO();
        updateDTO.setTipoTransaccion(TipoTransaccion.GASTO);
        updateDTO.setCategoriaId("cat1");
        updateDTO.setDescripcion("Actualizado");
        updateDTO.setFecha(LocalDateTime.now());
        updateDTO.setMonto(200.0);
        
        // Act
        TransaccionResponseDTO resultado = transaccionService.actualizarTransaccion("trans1", updateDTO);
        
        // Assert
        assertNotNull(resultado);
        verify(transaccionRepository, times(1)).save(any(Transaccion.class));
    }
    
    @Test
    void eliminarTransaccion_Exitoso() {
        // Arrange
        when(transaccionRepository.findById(anyString())).thenReturn(Optional.of(transaccion));
        when(userRepository.findById(anyString())).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);
        doNothing().when(transaccionRepository).delete(any(Transaccion.class));
        
        // Act
        transaccionService.eliminarTransaccion("trans1");
        
        // Assert
        verify(transaccionRepository, times(1)).delete(transaccion);
    }
}
