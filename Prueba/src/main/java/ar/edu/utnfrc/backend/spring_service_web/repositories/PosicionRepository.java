package ar.edu.utnfrc.backend.spring_service_web.repositories;

import ar.edu.utnfrc.backend.spring_service_web.entities.Posicion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PosicionRepository extends JpaRepository<Posicion, Integer> {
}
