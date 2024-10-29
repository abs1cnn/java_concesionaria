package ar.edu.utnfrc.backend.spring_service_web.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "modelos")  // Especificamos el nombre de la tabla en plural para seguir buenas prácticas
public class Modelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "descripcion", nullable = false)  // Descripción no debería ser nula
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "id_marca", nullable = false)  // Marca no debería ser nulo
    private Marca marca;
}
