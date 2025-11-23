package com.controlgastos.repository;

import com.controlgastos.model.Transaccion;
import com.controlgastos.model.TipoTransaccion;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Repositorio para la entidad Transaccion
 * Proporciona operaciones CRUD y consultas personalizadas
 */
@Repository
public interface TransaccionRepository extends MongoRepository<Transaccion, String> {
    
    /**
     * Busca todas las transacciones de un usuario
     * @param userId el ID del usuario
     * @return lista de transacciones del usuario
     */
    List<Transaccion> findByUserId(String userId);
    
    /**
     * Busca transacciones por usuario y tipo
     * @param userId el ID del usuario
     * @param tipo el tipo de transacción (INGRESO o GASTO)
     * @return lista de transacciones filtradas
     */
    List<Transaccion> findByUserIdAndTipoTransaccion(String userId, TipoTransaccion tipo);
    
    /**
     * Busca transacciones por usuario y categoría
     * @param userId el ID del usuario
     * @param categoriaId el ID de la categoría
     * @return lista de transacciones de la categoría
     */
    List<Transaccion> findByUserIdAndCategoriaId(String userId, String categoriaId);
    
    /**
     * Busca transacciones por usuario en un rango de fechas
     * @param userId el ID del usuario
     * @param fechaInicio fecha de inicio del rango
     * @param fechaFin fecha de fin del rango
     * @return lista de transacciones en el rango
     */
    List<Transaccion> findByUserIdAndFechaBetween(String userId, LocalDateTime fechaInicio, LocalDateTime fechaFin);
    
    /**
     * Busca transacciones por usuario, tipo y rango de fechas
     * @param userId el ID del usuario
     * @param tipo el tipo de transacción
     * @param fechaInicio fecha de inicio del rango
     * @param fechaFin fecha de fin del rango
     * @return lista de transacciones filtradas
     */
    List<Transaccion> findByUserIdAndTipoTransaccionAndFechaBetween(
            String userId, TipoTransaccion tipo, LocalDateTime fechaInicio, LocalDateTime fechaFin);
    
    /**
     * Calcula el total de transacciones por tipo para un usuario
     * @param userId el ID del usuario
     * @param tipo el tipo de transacción
     * @return suma total de los montos
     */
    @Query("{ 'userId': ?0, 'tipoTransaccion': ?1 }")
    List<Transaccion> findForSum(String userId, TipoTransaccion tipo);
    
    /**
     * Elimina todas las transacciones de un usuario
     * @param userId el ID del usuario
     */
    void deleteByUserId(String userId);
    
    /**
     * Elimina transacciones por categoría
     * @param categoriaId el ID de la categoría
     */
    void deleteByCategoriaId(String categoriaId);
}
