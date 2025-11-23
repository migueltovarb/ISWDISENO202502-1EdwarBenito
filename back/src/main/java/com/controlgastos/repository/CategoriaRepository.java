package com.controlgastos.repository;

import com.controlgastos.model.Categoria;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la entidad Categoria
 * Proporciona operaciones CRUD y consultas personalizadas
 */
@Repository
public interface CategoriaRepository extends MongoRepository<Categoria, String> {
    
    /**
     * Busca todas las categorías de un usuario específico
     * @param userId el ID del usuario
     * @return lista de categorías del usuario
     */
    List<Categoria> findByUserId(String userId);
    
    /**
     * Busca una categoría por nombre y usuario
     * @param nombre el nombre de la categoría
     * @param userId el ID del usuario
     * @return Optional con la categoría si existe
     */
    Optional<Categoria> findByNombreAndUserId(String nombre, String userId);
    
    /**
     * Elimina todas las categorías de un usuario
     * @param userId el ID del usuario
     */
    void deleteByUserId(String userId);
}
