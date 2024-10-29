package ar.edu.utnfrc.backend.spring_service_web.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "posiciones")
public class Posicion {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "id_vehiculo", nullable = false)
    private Vehiculo vehiculo;
    @Column(name = "fecha_hora", nullable = false)
    private String fechaHora;
    @Column(name = "latitud", nullable = false)
    private Double latitud;
    @Column(name = "longitud", nullable = false)
    private Double longitud;

}
