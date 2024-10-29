package ar.edu.utnfrc.backend.spring_service_web.controllers;

import ar.edu.utnfrc.backend.spring_service_web.services.interfaces.PosicionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posicion")
public class PosicionController {

    @Autowired
    private PosicionService posicionService;

}
