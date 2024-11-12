package com.example.notificacion.entities;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;


import java.time.LocalDateTime;

@Entity
@Table(name = "notificacion")

public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; //pk

    @Column(name = "mensaje_texto", nullable = false)
    private String mensaje;

    @Column(name = "fecha", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")

    private LocalDateTime fecha;

    @Column(name = "nro_telefono", nullable = false)
    private String nrotelefono;

    @Column(name = "es_empleado", nullable = false)
    private Boolean esEmpleado;

    public Integer getId() {
        return id;
    }

    public String getMensaje() {
        return mensaje;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public String getNrotelefono() {
        return nrotelefono;
    }

    public Boolean getEsEmpleado() {
        return esEmpleado;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public void setNrotelefono(String nrotelefono) {
        this.nrotelefono = nrotelefono;
    }

    public void setEsEmpleado(Boolean esEmpleado) {
        this.esEmpleado = esEmpleado;
    }

    public Notificacion(Integer id, String mensaje, LocalDateTime fecha, String nrotelefono, Boolean esEmpleado) {
        this.id = id;
        this.mensaje = mensaje;
        this.fecha = fecha;
        this.nrotelefono = nrotelefono;
        this.esEmpleado = esEmpleado;
    }

    public Notificacion() {

    }
}

