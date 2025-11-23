package com.controlgastos.repository;

import com.controlgastos.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Repositorio para la entidad User
 * Proporciona operaciones CRUD y consultas personalizadas
 */
@Repository
public interface UserRepository extends MongoRepository<User, String> {
    
    /**
     * Busca un usuario por su apodo
     * @param apodo el apodo del usuario
     * @return Optional con el usuario si existe
     */
    Optional<User> findByApodo(String apodo);
    
    /**
     * Busca un usuario por su correo electrónico
     * @param correo el correo del usuario
     * @return Optional con el usuario si existe
     */
    Optional<User> findByCorreo(String correo);
    
    /**
     * Verifica si existe un usuario con el apodo especificado
     * @param apodo el apodo a verificar
     * @return true si existe, false en caso contrario
     */
    boolean existsByApodo(String apodo);
    
    /**
     * Verifica si existe un usuario con el correo especificado
     * @param correo el correo a verificar
     * @return true si existe, false en caso contrario
     */
    boolean existsByCorreo(String correo);
}
