package ar.edu.utnfrc.backend.spring_service_web.repositories;

import ar.edu.utnfrc.backend.spring_service_web.entities.Posicion;
import ar.edu.utnfrc.backend.spring_service_web.entities.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PosicionRepository extends JpaRepository<Posicion, Integer> {
    List<Posicion> findByVehiculo(Vehiculo vehiculo);
}
