package ar.edu.utnfrc.backend.spring_service_web.services;
import ar.edu.utnfrc.backend.spring_service_web.entities.Posicion;
import ar.edu.utnfrc.backend.spring_service_web.repositories.PosicionRepository;
import ar.edu.utnfrc.backend.spring_service_web.services.interfaces.PosicionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class PosicionServiceImpl extends ServiceImpl<Posicion, Integer> implements PosicionService {

    private final PosicionRepository posicionRepository;

    @Override
    public Posicion create(Posicion entity) {
        return null;
    }

    @Override
    public Posicion update(Integer id, Posicion entity) {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public Posicion findById(Integer id) {
        return null;
    }

    @Override
    public List<Posicion> findAll() {
        return List.of();
    }
}
