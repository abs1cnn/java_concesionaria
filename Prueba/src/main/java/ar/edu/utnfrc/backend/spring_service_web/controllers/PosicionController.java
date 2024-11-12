package ar.edu.utnfrc.backend.spring_service_web.controllers;

import ar.edu.utnfrc.backend.spring_service_web.services.interfaces.PosicionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/posicion")
public class PosicionController {

    @Autowired
    private PosicionService posicionService;

}
