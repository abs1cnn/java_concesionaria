package ar.edu.utnfrc.backend.spring_service_web.entities;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Vehiculos")  // Cambiado a minúsculas para convenciones comunes de nombres de tablas
public class Vehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "PATENTE", nullable = false, unique = true)  // La patente debería ser única y no nula
    private String patente;

    @ManyToOne
    @JoinColumn(name = "ID_MODELO", nullable = false)  // Modelo no puede ser nulo
    private Modelo modelo;

    @Column(name = "ANIO", nullable = false)  // Año no puede ser nulo
    private Integer anio;

    @Transient
    @OneToMany(mappedBy = "vehiculo")
    private List<Prueba> pruebas;
}