package ar.edu.utnfrc.backend.spring_service_web.services;


//import com.example.notificacion.repositories.NotificacionRepository;
//import com.example.notificacion.entities.Notificacion;
import ar.edu.utnfrc.backend.spring_service_web.configuracion.Configuracion;
import ar.edu.utnfrc.backend.spring_service_web.configuracion.Coordenadas;
import ar.edu.utnfrc.backend.spring_service_web.configuracion.ZonaRestringida;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ConfiguracionService {

    private static final String API_URL = "https://labsys.frc.utn.edu.ar/apps-disponibilizadas/backend/api/v1/configuracion/";
    private final RestTemplate restTemplate;

//    @Autowired
//    public NotificacionService(NotificacionRepository notificacionRepository, RestTemplate restTemplate) {
//        this.notificacionRepository = notificacionRepository;
//        this.restTemplate = restTemplate;
//    }

    public ConfiguracionService(RestTemplate restTemplate) {
//        this.notificacionRepository = notificacionRepository;
        this.restTemplate = restTemplate;
    }

    // Método para obtener la configuración desde la API
    public Configuracion obtenerConfiguracion() {
        return restTemplate.getForObject(API_URL, Configuracion.class);
    }

    // Método para verificar la posición del vehículo
    public boolean verificarPosicionVehiculo(Coordenadas posicionVehiculo) {
        Configuracion configuracion = obtenerConfiguracion();
        if (configuracion == null) {
            throw new RuntimeException("No se pudo obtener la configuración");
        }

        // Verificar si el vehículo está fuera del radio permitido
        if (!estaDentroDelRadio(configuracion.getCoordenadasAgencia(), posicionVehiculo, configuracion.getRadioAdmitidoKm())) {
//            registrarNotificacion("El vehículo ha excedido el radio permitido.");
            return false;
        }

        // Verificar si el vehículo está en una zona peligrosa
        for (ZonaRestringida zona : configuracion.getZonasRestringidas()) {
            if (estaEnZonaRestringida(zona, posicionVehiculo)) {
//                registrarNotificacion("El vehículo ha ingresado a una zona peligrosa.");
                return false;
            }
        }
        return true;
    }

    private static final double KM_A_GRADOS = 1 / 111.0;

    private boolean estaEnZonaRestringida(ZonaRestringida zona, Coordenadas vehiculo) { //ACA HAY QUE USAR LAS COORDENADAS DEL VEHICULO DE PRUEBA
        return vehiculo.getLat() <= zona.getNoroeste().getLat() &&
                vehiculo.getLat() >= zona.getSureste().getLat() &&
                vehiculo.getLon() >= zona.getNoroeste().getLon() &&
                vehiculo.getLon() <= zona.getSureste().getLon();
    }

    // Cálculo de distancia euclídea en kilómetros
    private double calcularDistancia(Coordenadas c1, Coordenadas c2) {
        double latDiff = c2.getLat() - c1.getLat();
        double lonDiff = c2.getLon() - c1.getLon();
        return Math.sqrt(latDiff * latDiff + lonDiff * lonDiff);
    }

    private boolean estaDentroDelRadio(Coordenadas agencia, Coordenadas vehiculo, double radioKm) {
        double radioEnGrados = radioKm * KM_A_GRADOS;
        double distancia = calcularDistancia(agencia, vehiculo);
        return distancia <= radioEnGrados;
    }


}