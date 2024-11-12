package com.example.notificacion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import com.example.notificacion.services.NotificacionService;

import java.util.List;

@SpringBootApplication
public class NotificacionApplication {

    @Autowired
    private NotificacionService notificacionService; // Inyección de dependencia

    public static void main(String[] args) {
        // Arrancar la aplicación Spring Boot
        ApplicationContext context = SpringApplication.run(NotificacionApplication.class, args);

        // Obtener la instancia del servicio desde el contexto
        NotificacionService notificacionService1 = context.getBean(NotificacionService.class);

        // Llamar al método obtenerTelefonos y obtener la lista de números
        List<String> telefonos = notificacionService1.obtenerTelefonos();

        // Imprimir la lista de números de teléfono
        System.out.println("Números de teléfono: " + telefonos);


        notificacionService1.notificarInteresados("Estoy andando <3");


    }
}
