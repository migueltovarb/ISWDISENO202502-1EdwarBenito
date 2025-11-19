package com.vehiculos.repository;

import com.vehiculos.model.Vehiculo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VehiculoRepository extends MongoRepository<Vehiculo, String> {
    List<Vehiculo> findByPlantaId(String plantaId);
    List<Vehiculo> findByMarca(String marca);
}
