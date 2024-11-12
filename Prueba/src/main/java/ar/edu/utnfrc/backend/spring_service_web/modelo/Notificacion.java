package ar.edu.utnfrc.backend.spring_service_web.modelo;

import jakarta.persistence.*;
import lombok.Data;


import java.time.LocalDateTime;


@Data
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; //pk

    @Column(name = "mensaje_texto", nullable = false)
    private String mensaje;

    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha;

    @Column(name = "nro_telefono", nullable = false)
    private String nrotelefono;

    @Column(name = "es_empleado", nullable = false)
    private Boolean esEmpleado;


}

