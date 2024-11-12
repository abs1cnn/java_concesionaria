package ar.edu.utnfrc.backend.spring_service_web.services;

import ar.edu.utnfrc.backend.spring_service_web.configuracion.Coordenadas;
import ar.edu.utnfrc.backend.spring_service_web.entities.Client;
import ar.edu.utnfrc.backend.spring_service_web.entities.Posicion;
import ar.edu.utnfrc.backend.spring_service_web.entities.Prueba;
import ar.edu.utnfrc.backend.spring_service_web.entities.Vehiculo;
import ar.edu.utnfrc.backend.spring_service_web.modelo.Notificacion;
import ar.edu.utnfrc.backend.spring_service_web.repositories.ClientRepository;
import ar.edu.utnfrc.backend.spring_service_web.repositories.PosicionRepository;
import ar.edu.utnfrc.backend.spring_service_web.repositories.PruebaRepository;
import ar.edu.utnfrc.backend.spring_service_web.repositories.VehiculoRepository;
import ar.edu.utnfrc.backend.spring_service_web.services.interfaces.PruebaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PruebaServiceImpl extends ServiceImpl<Prueba, Integer> implements PruebaService {

    private final ClientRepository clienteRepository;
    private final VehiculoRepository vehiculoRepository;
    private final PruebaRepository pruebaRepository;
    private final ConfiguracionService configuration;
    private final PosicionRepository posicionRepository;

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

    @Override
    public List<Prueba> listarPruebasFinalizadas() {
        // Obtener todas las pruebas
        List<Prueba> todasLasPruebas = pruebaRepository.findAll();

        // Filtrar las pruebas finalizadas (donde fechaHoraFin no es null y es diferente de fechaHoraInicio)
        return todasLasPruebas.stream()
                .filter(prueba -> prueba.getFechaHoraFin() != null &&
                        !prueba.getFechaHoraFin().equals(prueba.getFechaHoraInicio()))
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

// PUNTO D

    @Override
    public void validarPosicionVehiculo(double lat, double lon, Integer id) {
            Integer telefonoEmpleado = 0;
            Posicion nuevaPosicion = new Posicion();
            Coordenadas c1 = new Coordenadas();
            c1.setLat(lat);
            c1.setLon(lon);
            // Busca el vehículo por ID
            Optional<Vehiculo> vehiculoOpt = vehiculoRepository.findById(id);
            if (vehiculoOpt.isEmpty()) {
                throw new IllegalArgumentException("El vehículo con ID " + id + " no existe.");
            }

            // Verifica si el vehículo tiene pruebas activas
            Vehiculo vehiculo = vehiculoOpt.get();
            List<Prueba> pruebasActivas = listarPruebasEnCurso();

            if (!pruebasActivas.isEmpty()) {
                boolean x = false;
                for (Prueba p :pruebasActivas){
                    if (p.getVehiculo().getId().equals(id)){
                        x = true;
                        telefonoEmpleado=p.getIdEmpleado().getTelefonoContacto();
                        break;
                    }

                }
                if (!x){
                    throw new IllegalArgumentException("El vehiculo "+ id + " no se encuentra en pruebas activas");
                }

                // Verifica si el vehículo ya tiene alguna posición registrada en la base de datos
                List<Posicion> posiciones = posicionRepository.findByVehiculo(vehiculo);
                if (!posiciones.isEmpty()) {

                    // Recupera el ID de la primera posición encontrada
                    Integer posicionExistenteId = posiciones.get(0).getId();
                    nuevaPosicion.setId(posicionExistenteId);
                }
                nuevaPosicion.setVehiculo(vehiculo);
                nuevaPosicion.setLatitud(lat);
                nuevaPosicion.setLongitud(lon);
                nuevaPosicion.setFechaHora(String.valueOf(LocalDateTime.now()));

               posicionRepository.save(nuevaPosicion); //
               if (!configuration.verificarPosicionVehiculo(c1)){
                   String mensaje = "El vehículo con ID " + id + " está en una zona restringida o fuera del radio permitido.";
                   enviarNotificacion(telefonoEmpleado, mensaje);
                   throw new IllegalArgumentException("El vehiculo esta en una zona restringida");
               }
            }
            else{
                throw new IllegalArgumentException("No hay pruebas activas.");
            }
    }

    private void enviarNotificacion(Integer telefonoEmpleado, String mensaje) {
        //EL MICROSERVICIO QUE CONSUMIMOS
        String  urlNotificacion= "http://localhost:1000/notificacion/crear"; // URL del
        RestTemplate restTemplate = new RestTemplate();

        // Creamos el objeto Notificacion que enviamos a Notificaciones
        Notificacion notificacion = new Notificacion();
        notificacion.setMensaje(mensaje);
        notificacion.setFecha(LocalDateTime.now());
        notificacion.setNrotelefono(String.valueOf(telefonoEmpleado));
        notificacion.setEsEmpleado(true);

        try {
            // Realiza la llamada POST al microservicio de Notificaciones
            restTemplate.postForEntity(urlNotificacion, notificacion, String.class);
        } catch (Exception e) {
            throw new RuntimeException("Error al enviar notificación: " + e.getMessage());
        }
    }

 //

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
