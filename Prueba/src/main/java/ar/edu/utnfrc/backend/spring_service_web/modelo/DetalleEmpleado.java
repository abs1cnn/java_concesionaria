package ar.edu.utnfrc.backend.spring_service_web.modelo;

import ar.edu.utnfrc.backend.spring_service_web.configuracion.Coordenadas;
import ar.edu.utnfrc.backend.spring_service_web.entities.Empleado;
import ar.edu.utnfrc.backend.spring_service_web.entities.Prueba;
import lombok.Data;

@Data
public class DetalleEmpleado {
    private int id_detalle;
    private String nombre;
    private String apellido;
    private Prueba prueba;
    private Coordenadas coordsIncidente;
}
