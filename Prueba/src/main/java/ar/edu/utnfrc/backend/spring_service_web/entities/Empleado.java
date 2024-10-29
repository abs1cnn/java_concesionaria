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
@Table(name = "empleados")  // Nombre de la tabla en tu base de datos
@Data
@NoArgsConstructor
public class Empleado {
    // Getters y Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // ID autoincremental
    @Column(name = "legajo")  // Nombre de la columna en la base de datos
    private Integer legajo;

    @Column(name = "nombre", nullable = false, length = 30)  // Nombre del empleado (máximo 30 caracteres)
    private String nombre;

    @Column(name = "apellido", nullable = false, length = 30)  // Apellido del empleado (máximo 30 caracteres)
    private String apellido;

    @Column(name = "telefono_contacto")  // Teléfono de contacto del empleado
    private Integer telefonoContacto;

    @Transient
    @OneToMany(mappedBy = "empleado")
    private List<Prueba> pruebas;
}
