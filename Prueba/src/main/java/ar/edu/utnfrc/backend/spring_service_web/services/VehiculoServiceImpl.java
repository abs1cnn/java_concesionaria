package ar.edu.utnfrc.backend.spring_service_web.services;
import ar.edu.utnfrc.backend.spring_service_web.repositories.VehiculoRepository;
import ar.edu.utnfrc.backend.spring_service_web.entities.Vehiculo;
import ar.edu.utnfrc.backend.spring_service_web.services.interfaces.VehiculoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehiculoServiceImpl extends ServiceImpl<Vehiculo, Integer> implements VehiculoService {

    private final VehiculoRepository vehiculoRepository;

    @Override
    public Vehiculo create(Vehiculo entity) {
        vehiculoRepository.save(entity);
        return entity;
    }

    @Override
    public Vehiculo update(Integer id, Vehiculo entity) {
        vehiculoRepository.save(entity);
        return entity;
    }

    @Override
    public void delete(Integer id) {
        Vehiculo entity = findById(id);
        vehiculoRepository.delete(entity);
    }

    @Override
    public Vehiculo findById(Integer id) {
        return vehiculoRepository.findById(id).orElseThrow();
    }

    @Override
    public List<Vehiculo> findAll() {
        return vehiculoRepository.findAll();
    }

}