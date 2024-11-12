package ar.edu.utnfrc.backend.spring_service_web.modelo;


import lombok.Data;

@Data
public class DetalleVehiculo {
    private Integer id_detalle;
    private Integer id_vehiculo;
    private Integer id_prueba;
    private String patente_vehiculo;
    private String nombre_interesado;
    private String nombre_empleado;
    private String fechahora_inicio;
    private String fechahora_fin;
}
