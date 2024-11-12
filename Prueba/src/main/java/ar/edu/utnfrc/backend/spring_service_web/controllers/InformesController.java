package ar.edu.utnfrc.backend.spring_service_web.controllers;

import ar.edu.utnfrc.backend.spring_service_web.entities.Prueba;
import ar.edu.utnfrc.backend.spring_service_web.modelo.DetalleEmpleado;
import ar.edu.utnfrc.backend.spring_service_web.modelo.DetalleVehiculo;
import ar.edu.utnfrc.backend.spring_service_web.modelo.Incidente;
import ar.edu.utnfrc.backend.spring_service_web.services.IncidenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
@RestController
@RequestMapping("/informes")

public class InformesController {
    private final IncidenteService incidenteService;

    public InformesController(IncidenteService incidenteService) {
        this.incidenteService = incidenteService;
    }
//INFORME I
    @GetMapping("/incidentes")
    public ResponseEntity<List<Incidente>> obtenerInformeIncidentes() {
        List<Incidente> incidentes = incidenteService.generarInformeDeIncidentes();
        return ResponseEntity.ok(incidentes);
    }
//INFORME II
    @GetMapping("/detalle/empleado/{legajo}")
    public ResponseEntity<?> obtenerDetalleIncidentesPorEmpleado(@PathVariable int legajo) {
        List<DetalleEmpleado> detalleIncidentes = incidenteService.generarDetalleEmpleado(legajo);
        if (!detalleIncidentes.isEmpty()) {
            return ResponseEntity.ok(detalleIncidentes);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se encontraron incidentes para el empleado con legajo: " + legajo);
        }
    }

    //INFORME III
    @GetMapping("/distancia-tiempo/vehiculo/{id}")
    public ResponseEntity<?> obtenerDistanciaYTiempoPorVehiculo(@PathVariable Integer id) {
        IncidenteService.DistanciaYTiempo resultado = incidenteService.calcularDistanciaYTiempo(id);

        if (resultado.getDistanciaTotal() == 0 && resultado.getTiempoTotalMinutos() == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se encontraron pruebas finalizadas para el vehículo con id: " + id);
        }

        String mensaje = String.format("La distancia recorrida del vehículo con id %d es de %.2f km en un tiempo total de %d minutos.",
                id, resultado.getDistanciaTotal(), resultado.getTiempoTotalMinutos());
        return ResponseEntity.ok(mensaje);
    }

    //INFORME IV
@GetMapping("/detalle/vehiculo/{id}")
public ResponseEntity<?> obtenerDetalleVehiculo(@PathVariable Integer id) {
    int num = 1;
    List<Prueba> detalleVehiculo = incidenteService.generarDetalleVehiculo(id);
    List<DetalleVehiculo> detalleVehiculos = new ArrayList<>();

    if (!detalleVehiculo.isEmpty()) {
        for (Prueba p : detalleVehiculo) {
            DetalleVehiculo detalle = new DetalleVehiculo();
            detalle.setId_detalle(num++);
            detalle.setId_vehiculo(p.getVehiculo().getId());
            detalle.setId_prueba(p.getId());
            detalle.setPatente_vehiculo(p.getVehiculo().getPatente());
            detalle.setNombre_empleado(p.getIdEmpleado().getNombre());
            detalle.setNombre_interesado(p.getCliente().getNombre());
            detalle.setFechahora_inicio(p.getFechaHoraInicio());
            detalle.setFechahora_fin(p.getFechaHoraFin());
            detalleVehiculos.add(detalle);
        }
        // Devuelve la lista de detalleVehiculos en un ResponseEntity con status OK
        return ResponseEntity.ok(detalleVehiculos);
    } else {
        // Devuelve un mensaje de error en caso de no encontrar pruebas
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("No se encontraron pruebas para el vehiculo con id: " + id);
    }
}


}
