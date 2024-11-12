package ar.edu.utnfrc.backend.spring_service_web.services;

import ar.edu.utnfrc.backend.spring_service_web.configuracion.Coordenadas;
import ar.edu.utnfrc.backend.spring_service_web.entities.Posicion;
import ar.edu.utnfrc.backend.spring_service_web.entities.Prueba;
import ar.edu.utnfrc.backend.spring_service_web.entities.Vehiculo;
import ar.edu.utnfrc.backend.spring_service_web.modelo.DetalleEmpleado;
import ar.edu.utnfrc.backend.spring_service_web.modelo.Incidente;
import ar.edu.utnfrc.backend.spring_service_web.modelo.PruebaIncidente;
import ar.edu.utnfrc.backend.spring_service_web.repositories.PosicionRepository;
import ar.edu.utnfrc.backend.spring_service_web.repositories.PruebaRepository;
import ar.edu.utnfrc.backend.spring_service_web.repositories.VehiculoRepository;
import ar.edu.utnfrc.backend.spring_service_web.services.interfaces.PruebaService;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class IncidenteService {

    private final PruebaService pruebaService;
    private final ConfiguracionService configuracionService;
    private final PosicionRepository posicionRepository;
    private final PruebaRepository pruebaRepository;
    private final VehiculoRepository vehiculoRepository;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public IncidenteService(PruebaService pruebaService, ConfiguracionService configuracionService,
                            PosicionRepository posicionRepository, PruebaRepository pruebaRepository,
                            VehiculoRepository vehiculoRepository
                            ) {
        this.pruebaService = pruebaService;
        this.configuracionService = configuracionService;
        this.posicionRepository = posicionRepository;
        this.pruebaRepository = pruebaRepository;
        this.vehiculoRepository = vehiculoRepository;
    }

    // Método para generar un informe de incidentes por pruebas activas
    public List<Incidente> generarInformeDeIncidentes() {
        int id = 1;
        List<Incidente> incidentes = new ArrayList<>();
        List<Prueba> pruebasFinalizadas = pruebaService.listarPruebasFinalizadas();

        for (Prueba prueba : pruebasFinalizadas) {
            int idPrueba = prueba.getId();
            List<Posicion> posicionesVehiculo = posicionRepository.findByVehiculo(prueba.getVehiculo());

            for (Posicion posicion : posicionesVehiculo) {
                Coordenadas posicionVehiculo = new Coordenadas(posicion.getLatitud(), posicion.getLongitud());

                // Verifica si la posición actual está dentro de los límites permitidos
                boolean enZonaPermitida = configuracionService.verificarPosicionVehiculo(posicionVehiculo);

                if (!enZonaPermitida) {
                    // Crea un nuevo incidente si la posición está fuera de los límites permitidos
                    Incidente incidente = new Incidente();

                    incidente.setId_incidente(id);
                    id = id + 1;
                    incidente.setId_prueba(idPrueba);
                    incidente.setMotivo("El vehículo ha excedido el radio permitido o ha ingresado a una zona peligrosa.");

                    // Agrega el incidente a la lista de incidentes
                    incidentes.add(incidente);
                }
            }
        }

        return incidentes;
    }

    // Método para generar un detalle de incidentes para un empleado

    public List<DetalleEmpleado> generarDetalleEmpleado(int idEmpleado) {
        List<PruebaIncidente> pruebasConIncidente = new ArrayList<>();
        List<DetalleEmpleado> detalleEmpleado = new ArrayList<>();
        int idDetalle = 1;

        // Obtener todas las pruebas finalizadas
        List<Prueba> pruebasFinalizadas = pruebaService.listarPruebasFinalizadas();

        for (Prueba prueba : pruebasFinalizadas) {
            // Obtener las posiciones del vehículo en esta prueba
            List<Posicion> posicionesVehiculo = posicionRepository.findByVehiculo(prueba.getVehiculo());

            for (Posicion posicion : posicionesVehiculo) {
                // Crear coordenadas a partir de la posición actual
                Coordenadas coordsVehiculo = new Coordenadas();
                coordsVehiculo.setLat(posicion.getLatitud());
                coordsVehiculo.setLon(posicion.getLongitud());

                // Validar si la posición está dentro del radio permitido usando configuración
                if (!configuracionService.verificarPosicionVehiculo(coordsVehiculo)) {
                    // Agregar a la lista de incidentes para pruebas con este vehículo
                    PruebaIncidente pruebaIncidente = new PruebaIncidente();
                    pruebaIncidente.setId(prueba.getId());
                    pruebaIncidente.setVehiculo(prueba.getVehiculo());
                    pruebaIncidente.setCliente(prueba.getCliente());
                    pruebaIncidente.setEmpleado(prueba.getIdEmpleado());
                    pruebaIncidente.setFechaHoraInicio(prueba.getFechaHoraInicio());
                    pruebaIncidente.setFechaHoraFin(prueba.getFechaHoraFin());
                    pruebaIncidente.setComentarios(prueba.getComentarios());
                    pruebaIncidente.setPosicion(coordsVehiculo);
                    pruebasConIncidente.add(pruebaIncidente);
                }
            }
        }

        // Filtrar y generar detalles para el empleado con incidentes
        for (PruebaIncidente pruebaIncidente : pruebasConIncidente) {
            if (pruebaIncidente.getEmpleado().getLegajo() == idEmpleado) {
                DetalleEmpleado detalle = new DetalleEmpleado();
                detalle.setId_detalle(idDetalle++);
                detalle.setNombre(pruebaIncidente.getEmpleado().getNombre());
                detalle.setApellido(pruebaIncidente.getEmpleado().getApellido());
                detalle.setPrueba(pruebaService.findById(pruebaIncidente.getId()));
                detalle.setCoordsIncidente(pruebaIncidente.getPosicion());
                detalleEmpleado.add(detalle);
            }

        }

        return detalleEmpleado;
    }

    public List<Prueba> generarDetalleVehiculo(Integer id){
        Optional<Vehiculo> vehiculoOptional = vehiculoRepository.findById(id);
        List<Prueba> detalleVehiculos = new ArrayList<>();
        if(vehiculoOptional.isPresent()){

            Vehiculo vehiculo = vehiculoOptional.get();

            detalleVehiculos = pruebaRepository.findByVehiculo(vehiculo);
        }
        return detalleVehiculos;

    }

    public class DistanciaYTiempo {
        private double distanciaTotal;
        private int tiempoTotalMinutos;

        public DistanciaYTiempo(double distanciaTotal, int tiempoTotalMinutos) {
            this.distanciaTotal = distanciaTotal;
            this.tiempoTotalMinutos = tiempoTotalMinutos;
        }

        public double getDistanciaTotal() {
            return distanciaTotal;
        }

        public int getTiempoTotalMinutos() {
            return tiempoTotalMinutos;
        }

    }

    public DistanciaYTiempo calcularDistanciaYTiempo(int vehiculoId) {
        // Obtener las pruebas finalizadas para el vehículo especificado
        List<Prueba> pruebasFinalizadas = pruebaRepository.findAll().stream()
                .filter(prueba -> prueba.getVehiculo().getId() == vehiculoId && prueba.getFechaHoraFin() != null)
                .collect(Collectors.toList());

        Double distanciaTotal = 0.0;
        long tiempoTotalMinutos = 0;

        // Acumular la distancia recorrida y el tiempo de duración de cada prueba
        for (Prueba prueba : pruebasFinalizadas) {
            // Calcular la distancia total recorrida
                distanciaTotal += prueba.getKilometros();


            // Calcular la diferencia de tiempo entre fechaHoraInicio y fechaHoraFin
            LocalDateTime fechaInicio = LocalDateTime.parse(prueba.getFechaHoraInicio(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            LocalDateTime fechaFin = LocalDateTime.parse(prueba.getFechaHoraFin(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            long minutos = ChronoUnit.MINUTES.between(fechaInicio, fechaFin);
            tiempoTotalMinutos += minutos;
        }

        // Retornar la distancia total y el tiempo acumulado
        return new DistanciaYTiempo(distanciaTotal, (int) tiempoTotalMinutos);
    }

    /**
     * Calcula la distancia total recorrida por un vehículo en las pruebas finalizadas.
     *
     * @paramPruebas Lista de pruebas finalizadas para un vehículo.
     * @return Distancia total recorrida en kilómetros.
     */
    public double getDistanciaTotal(Vehiculo vehiculo) {
        double distanciaTotal = 0.0;
        List<Prueba> pruebas = vehiculo.getPruebas();  // Obtener todas las pruebas del vehículo

        for (Prueba prueba : pruebas) {
            distanciaTotal += prueba.getKilometros();  // Acumular los kilómetros de cada prueba
        }

        return distanciaTotal;
    }
    
    /**
     * Método auxiliar para calcular la distancia entre dos posiciones usando la fórmula de Haversine.
     *
     * @param p1 Posición inicial.
     * @param p2 Posición final.
     * @return Distancia en kilómetros entre las dos posiciones.
     */
    private double calcularDistancia(Posicion p1, Posicion p2) {
        final int RADIO_TIERRA = 6371; // Radio de la Tierra en kilómetros

        double latDistance = Math.toRadians(p2.getLatitud() - p1.getLatitud());
        double lonDistance = Math.toRadians(p2.getLongitud() - p1.getLongitud());

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(p1.getLatitud())) * Math.cos(Math.toRadians(p2.getLatitud()))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return RADIO_TIERRA * c; // Devuelve distancia en kilómetros
    }

    /**
     * Calcula el tiempo total en minutos que un vehículo estuvo en pruebas finalizadas.
     *
     * @param pruebas Lista de pruebas finalizadas para un vehículo.
     * @return Tiempo total en minutos.
     */
    public long getTiempoTotalMinutos(List<Prueba> pruebas) {
        long tiempoTotal = 0;

        for (Prueba prueba : pruebas) {
            LocalDateTime inicio = LocalDateTime.parse(prueba.getInicio(), FORMATTER);
            LocalDateTime fin = LocalDateTime.parse(prueba.getFin(), FORMATTER);
            tiempoTotal += Duration.between(inicio, fin).toMinutes();
        }

        return tiempoTotal;
    }
}