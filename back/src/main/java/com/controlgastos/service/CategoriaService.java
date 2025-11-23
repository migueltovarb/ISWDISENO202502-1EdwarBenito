package com.controlgastos.service;

import com.controlgastos.dto.CategoriaDTO;
import com.controlgastos.dto.CategoriaResponseDTO;
import com.controlgastos.exception.DuplicateResourceException;
import com.controlgastos.exception.ResourceNotFoundException;
import com.controlgastos.model.Categoria;
import com.controlgastos.model.User;
import com.controlgastos.repository.CategoriaRepository;
import com.controlgastos.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio para la gestión de categorías
 * Contiene toda la lógica de negocio relacionada con categorías
 */
@Service
@RequiredArgsConstructor
public class CategoriaService {
    
    private final CategoriaRepository categoriaRepository;
    private final UserRepository userRepository;
    
    /**
     * Crea una nueva categoría para un usuario
     * @param userId ID del usuario
     * @param categoriaDTO datos de la categoría
     * @return categoría creada
     */
    @Transactional
    public CategoriaResponseDTO crearCategoria(String userId, CategoriaDTO categoriaDTO) {
        // Verificar que el usuario existe
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", userId));
        
        // Verificar que la categoría no exista para este usuario
        if (categoriaRepository.findByNombreAndUserId(categoriaDTO.getNombre(), userId).isPresent()) {
            throw new DuplicateResourceException("Categoría", "nombre", categoriaDTO.getNombre());
        }
        
        // Crear y guardar la categoría
        Categoria categoria = new Categoria(categoriaDTO.getNombre(), userId);
        Categoria savedCategoria = categoriaRepository.save(categoria);
        
        // Actualizar el usuario con la nueva categoría
        user.agregarCategoria(savedCategoria.getId());
        userRepository.save(user);
        
        return convertToDTO(savedCategoria);
    }
    
    /**
     * Obtiene todas las categorías de un usuario
     * @param userId ID del usuario
     * @return lista de categorías
     */
    public List<CategoriaResponseDTO> obtenerCategoriasPorUsuario(String userId) {
        // Verificar que el usuario existe
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("Usuario", "id", userId);
        }
        
        return categoriaRepository.findByUserId(userId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Obtiene una categoría por su ID
     * @param id ID de la categoría
     * @return categoría encontrada
     */
    public CategoriaResponseDTO obtenerCategoriaPorId(String id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría", "id", id));
        return convertToDTO(categoria);
    }
    
    /**
     * Actualiza una categoría existente
     * @param id ID de la categoría
     * @param categoriaDTO nuevos datos
     * @return categoría actualizada
     */
    @Transactional
    public CategoriaResponseDTO actualizarCategoria(String id, CategoriaDTO categoriaDTO) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría", "id", id));
        
        // Verificar que el nuevo nombre no exista para este usuario (excepto esta misma categoría)
        categoriaRepository.findByNombreAndUserId(categoriaDTO.getNombre(), categoria.getUserId())
                .ifPresent(existingCategoria -> {
                    if (!existingCategoria.getId().equals(id)) {
                        throw new DuplicateResourceException("Categoría", "nombre", categoriaDTO.getNombre());
                    }
                });
        
        categoria.setNombre(categoriaDTO.getNombre());
        Categoria updatedCategoria = categoriaRepository.save(categoria);
        
        return convertToDTO(updatedCategoria);
    }
    
    /**
     * Elimina una categoría
     * @param id ID de la categoría
     */
    @Transactional
    public void eliminarCategoria(String id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría", "id", id));
        
        // Actualizar el usuario eliminando la referencia
        User user = userRepository.findById(categoria.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", categoria.getUserId()));
        user.eliminarCategoria(id);
        userRepository.save(user);
        
        // Eliminar la categoría
        categoriaRepository.delete(categoria);
    }
    
    /**
     * Convierte una entidad Categoria a CategoriaResponseDTO
     */
    private CategoriaResponseDTO convertToDTO(Categoria categoria) {
        return new CategoriaResponseDTO(
                categoria.getId(),
                categoria.getNombre(),
                categoria.getUserId()
        );
    }
}
