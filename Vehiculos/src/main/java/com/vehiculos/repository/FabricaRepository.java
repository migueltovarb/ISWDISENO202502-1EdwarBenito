package com.vehiculos.repository;

import com.vehiculos.model.Fabrica;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface FabricaRepository extends MongoRepository<Fabrica, String> {
    Optional<Fabrica> findByNombre(String nombre);
}
