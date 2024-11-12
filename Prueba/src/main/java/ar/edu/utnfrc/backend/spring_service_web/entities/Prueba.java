package ar.edu.utnfrc.backend.spring_service_web.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "pruebas")  // Nombre de la tabla en la base de datos
@Data
@NoArgsConstructor
public class Prueba {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // ID autoincremental
    @Column(name = "id")  // Nombre de la columna en la base de datos
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_vehiculo", nullable = false)
    private Vehiculo vehiculo;

    @ManyToOne
    @JoinColumn(name = "id_interesado", nullable = false)
    private Client cliente;

    @ManyToOne
    @JoinColumn(name = "id_empleado", nullable = false)  // ID del empleado, no puede ser nulo
    private Empleado idEmpleado;

    @Column(name = "fecha_hora_inicio", nullable = false)  // Fecha y hora de inicio de la prueba
    private String fechaHoraInicio;

    @Column(name = "fecha_hora_fin")  // Fecha y hora de fin de la prueba
    private String fechaHoraFin;

    @Column(name = "comentarios", length = 500)  // Comentarios con una longitud máxima de 500 caracteres
    private String comentarios;

    @Column(name = "kilometros")
    private Double kilometros;

    public String getInicio() {
        return fechaHoraInicio;
    }

    public String getFin() {
        return fechaHoraFin;
    }

    public double getKilometros() {
        return kilometros;
    }
}
