package com.example.notificacion.services;

import com.example.notificacion.models.Coordenadas;
import com.example.notificacion.models.ZonaRestringida;
import com.example.notificacion.repositories.NotificacionRepository;
import com.example.notificacion.entities.Notificacion;
import com.example.notificacion.models.Configuracion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;



import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class NotificacionService {


    private final NotificacionRepository notificacionRepository;
    private final RestTemplate restTemplate;

    /*
    private static final String API_URL = "https://labsys.frc.utn.edu.ar/apps-disponibilizadas/backend/api/v1/configuracion/";

    @Autowired
    public NotificacionService(NotificacionRepository notificacionRepository, RestTemplate restTemplate) {
        this.notificacionRepository = notificacionRepository;
        this.restTemplate = restTemplate;
    }
     */

    // Inyección automática del repositorio y restTemplate
    @Autowired
    public NotificacionService(NotificacionRepository notificacionRepository) {
        this.notificacionRepository = notificacionRepository;  // Spring inyecta el repositorio aquí
        this.restTemplate = new RestTemplate();  // El RestTemplate no necesita inyección si no es un bean de Spring
    }

    public Notificacion crearNotificacion(Notificacion notificacion) {
        return notificacionRepository.save(notificacion);
    }


    /*
    public List<Notificacion> obtenerTodasLasNotificaciones() {
        return notificacionRepository.findAll();
    }
    */


    //--------------------NOTIFICACION INTTERESADOS--------------------------

    @SuppressWarnings("unchecked")
    public List<String> obtenerTelefonos() {
        // URL de la API a consumir
        String url = "http://localhost:8888/cliente";

        // Realiza la solicitud GET a la API y recibe un array de clientes
        List<Map<String, Object>> clientes = restTemplate.getForObject(url, List.class);

        List<String> telefonos = new ArrayList<>();

        // Itera sobre cada cliente y extrae el campo "nroTelefono"

        for (Map<String, Object> cliente : clientes) {
            if (cliente.containsKey("nroTelefono")) {
                // Obtiene el número de teléfono y lo convierte a String
                Object nroTelefonoObj = cliente.get("nroTelefono");

                // Usamos String.valueOf() para convertir cualquier tipo de objeto a String
                String numTelefono = String.valueOf(nroTelefonoObj);

                telefonos.add(numTelefono);
            }
        }
        return telefonos;
    }


    public void notificarInteresados(String mensaje) {
        List<String> telefonos = obtenerTelefonos();
        for (String telefono : telefonos) {
            Notificacion notificacion = new Notificacion();
            notificacion.setMensaje(mensaje);
            notificacion.setFecha(LocalDateTime.now());
            notificacion.setNrotelefono(telefono);
            notificacion.setEsEmpleado(false);
            notificacionRepository.save(notificacion);
        }
    }


}


