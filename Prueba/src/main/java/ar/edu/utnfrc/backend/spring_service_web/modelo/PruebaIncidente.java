package ar.edu.utnfrc.backend.spring_service_web.modelo;

import ar.edu.utnfrc.backend.spring_service_web.configuracion.Coordenadas;
import ar.edu.utnfrc.backend.spring_service_web.entities.Client;
import ar.edu.utnfrc.backend.spring_service_web.entities.Empleado;
import ar.edu.utnfrc.backend.spring_service_web.entities.Vehiculo;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class PruebaIncidente {
    private Integer id;

    private Vehiculo vehiculo;

    private Client cliente;

    private Empleado empleado;

    private String fechaHoraInicio;

    private String fechaHoraFin;

    private String comentarios;

    private Coordenadas posicion;
}
