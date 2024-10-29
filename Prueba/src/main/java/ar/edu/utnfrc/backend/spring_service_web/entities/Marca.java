package ar.edu.utnfrc.backend.spring_service_web.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor  // Constructor sin argumentos
@Table(name = "marcas")  // Nombre de la tabla en plural, siguiendo buenas prácticas
public class Marca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nombre", nullable = false)  // Nombre no puede ser nulo
    private String nombre;
}