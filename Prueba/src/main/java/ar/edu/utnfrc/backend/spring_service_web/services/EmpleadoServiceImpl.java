package ar.edu.utnfrc.backend.spring_service_web.services;

import ar.edu.utnfrc.backend.spring_service_web.entities.Empleado;  // Importa la entidad Empleado
import ar.edu.utnfrc.backend.spring_service_web.repositories.EmpleadoRepository;  // Importa el repositorio correspondiente
import ar.edu.utnfrc.backend.spring_service_web.services.interfaces.EmpleadoService;  // Cambiado a EmpleadoService
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmpleadoServiceImpl extends ServiceImpl<Empleado, Integer> implements EmpleadoService {  // Cambiado a EmpleadoServiceImpl

    private final EmpleadoRepository empleadoRepository;  // Cambiado a empleadoRepository

    @Override
    public Empleado create(Empleado entity) {
        empleadoRepository.save(entity);  // Cambiado a empleadoRepository
        return entity;
    }

    @Override
    public Empleado update(Integer legajo, Empleado entity) {  // Cambiado a legajo en lugar de id
        entity.setLegajo(legajo);  // Asegúrate de que la entidad tenga el método setLegajo
        empleadoRepository.save(entity);  // Cambiado a empleadoRepository
        return entity;
    }

    @Override
    public void delete(Integer legajo) {  // Cambiado a legajo en lugar de id
        Empleado entity = findById(legajo);  // Cambiado a legajo
        empleadoRepository.delete(entity);  // Cambiado a empleadoRepository
    }

    @Override
    public Empleado findById(Integer legajo) {  // Cambiado a legajo en lugar de id
        return empleadoRepository.findById(legajo).orElseThrow();  // Cambiado a empleadoRepository
    }

    @Override
    public List<Empleado> findAll() {
        return empleadoRepository.findAll();  // Cambiado a empleadoRepository
    }

}
