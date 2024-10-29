package ar.edu.utnfrc.backend.spring_service_web.services;

import ar.edu.utnfrc.backend.spring_service_web.entities.Client;
import ar.edu.utnfrc.backend.spring_service_web.entities.Empleado;
import ar.edu.utnfrc.backend.spring_service_web.entities.Prueba;
import ar.edu.utnfrc.backend.spring_service_web.entities.Vehiculo;
import ar.edu.utnfrc.backend.spring_service_web.repositories.ClientRepository;
import ar.edu.utnfrc.backend.spring_service_web.repositories.PruebaRepository;
import ar.edu.utnfrc.backend.spring_service_web.repositories.VehiculoRepository;
import ar.edu.utnfrc.backend.spring_service_web.services.interfaces.PruebaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PruebaServiceImpl extends ServiceImpl<Prueba, Integer> implements PruebaService {

    private final ClientRepository clienteRepository;
    private final VehiculoRepository vehiculoRepository;
    private final PruebaRepository pruebaRepository;

    //-------------------------------------------------------------------------------
    @Override
    public List<Prueba> listarPruebasEnCurso() {
        // Obtener todas las pruebas
        List<Prueba> todasLasPruebas = pruebaRepository.findAll();

        // Filtrar las pruebas en curso (donde fechaHoraFin es null o igual a fechaHoraInicio)
        return todasLasPruebas.stream()
                .filter(prueba -> prueba.getFechaHoraFin() == null ||
                        prueba.getFechaHoraFin().equals(prueba.getFechaHoraInicio()))
                .collect(Collectors.toList());
    }
    //-------------------------------------------------------------------------------
    @Override
    public Prueba create(Prueba entity) {
        // Obtener el cliente por su ID
        Client cliente = clienteRepository.findById(entity.getCliente().getId())
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));

        // Validar si el cliente está restringido
        if (Boolean.TRUE.equals(cliente.getRestringido())) {
            throw new IllegalArgumentException("El cliente está restringido para realizar pruebas.");
        }

        // Validar si la licencia del cliente está vencida
        LocalDate fechaVencimientoLicencia = LocalDate.parse(cliente.getFechaVencimientoLicencia(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        if (fechaVencimientoLicencia.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("La licencia del cliente está vencida.");
        }

        // Obtener el vehículo por su ID
        Vehiculo vehiculo = vehiculoRepository.findById(entity.getVehiculo().getId())
                .orElseThrow(() -> new IllegalArgumentException("Vehículo no encontrado"));

        // Verificar si el vehículo ya está en una prueba activa (asumimos que es la fecha actual)
        List<Prueba> pruebasActivas = pruebaRepository.findByVehiculoAndFechaHoraFinIsNull(vehiculo);
        if (!pruebasActivas.isEmpty()) {
            throw new IllegalArgumentException("El vehículo ya está siendo probado actualmente.");
        }

        // Si pasa todas las validaciones, se crea la prueba
        entity.setFechaHoraInicio(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        entity.setFechaHoraFin(null);

        // Guardar la prueba
        return pruebaRepository.save(entity);
    }

    @Override
    public Prueba update(Integer id, Prueba entity) {
        // Buscar la prueba actual por ID
        Prueba pruebaExistente = pruebaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Prueba no encontrada"));

        // Actualizar los campos que se desean modificar
        pruebaExistente.setComentarios(entity.getComentarios());
        pruebaExistente.setFechaHoraFin(entity.getFechaHoraFin());

        // Guardar y retornar la prueba actualizada
        return pruebaRepository.save(pruebaExistente);
    }

    @Override
    public Prueba finalizarPrueba(Integer id, String comentario) {
        Prueba prueba = pruebaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Prueba no encontrada"));

        // Verificar que la prueba no esté ya finalizada
        if (prueba.getFechaHoraFin() != null) {
            throw new IllegalArgumentException("La prueba ya ha sido finalizada.");
        }

        // Establece la fecha de finalización y el comentario
        prueba.setFechaHoraFin(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        prueba.setComentarios(comentario); // Establece el comentario proporcionado


        // Guarda la prueba actualizada
        return pruebaRepository.save(prueba);
    }

    @Override
    public void delete(Integer id) {
        // Implementar la lógica de eliminación
    }

    @Override
    public Prueba findById(Integer id) {
        return pruebaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Prueba no encontrada"));
    }

    @Override
    public List<Prueba> findAll() {
        return pruebaRepository.findAll();
    }

}
