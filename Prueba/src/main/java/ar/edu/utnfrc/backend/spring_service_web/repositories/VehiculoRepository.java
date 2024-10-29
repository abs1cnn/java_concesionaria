package ar.edu.utnfrc.backend.spring_service_web.repositories;

import ar.edu.utnfrc.backend.spring_service_web.entities.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, Integer> {

}
