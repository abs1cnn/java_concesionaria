package ar.edu.utnfrc.backend.spring_service_web.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "interesados")  // Nombre de la tabla en tu base de datos
@Data
@NoArgsConstructor
public class Client {
    // Getters y Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // ID autoincremental
    @Column(name = "id")  // Nombre de la columna en la base de datos
    private Integer id;

    @Column(name = "tipo_documento", nullable = false)  // Añadiendo la columna con restricciones
    private String tipoDocumento;

    @Column(name = "documento", nullable = false, unique = true)  // Documento como único y no nulo
    private String documento;

    @Column(name = "nombre", nullable = false)  // El nombre no puede ser nulo
    private String nombre;

    @Column(name = "apellido", nullable = false)  // El apellido tampoco puede ser nulo
    private String apellido;

    @Column(name = "restringido", nullable = false)  // Booleano para saber si está restringido
    private Boolean restringido;

    @Column(name = "nro_licencia",nullable = false)  // Número de licencia
    private Integer nroLicencia;

    @Column(name = "fecha_vencimiento_licencia")  // Fecha de vencimiento de la licencia
    private String fechaVencimientoLicencia;

    @Transient
    @OneToMany(mappedBy = "client")
    private List<Prueba> pruebas;
}