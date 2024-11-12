package ar.edu.utnfrc.backend.spring_service_web.modelo;


import lombok.Data;

@Data
public class Incidente {
    private int id_incidente;
    private int id_prueba;
    private String motivo;
}
