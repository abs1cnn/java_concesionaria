package com.example.notificacion.controllers;

import com.example.notificacion.entities.Notificacion;
import com.example.notificacion.services.NotificacionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notificacion")
public class NotificacionController {

    private final NotificacionService notificacionService;

    public NotificacionController(NotificacionService notificacionService) {
        this.notificacionService = notificacionService;
    }

    @PostMapping("/crear")
    public ResponseEntity<Notificacion> crearNotificacion(@RequestBody Notificacion notificacion) {
        Notificacion notificacionCreada = notificacionService.crearNotificacion(notificacion);
        return ResponseEntity.status(HttpStatus.CREATED).body(notificacionCreada);
    }

}

