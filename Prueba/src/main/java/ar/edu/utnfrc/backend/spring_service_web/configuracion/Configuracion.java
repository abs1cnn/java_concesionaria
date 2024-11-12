// Configuracion.java
package ar.edu.utnfrc.backend.spring_service_web.configuracion;

import java.util.List;

public class Configuracion {
    private Coordenadas coordenadasAgencia;
    private double radioAdmitidoKm;
    private List<ZonaRestringida> zonasRestringidas;

    // Constructor por defecto
    public Configuracion() {}

    // Constructor con parámetros
    public Configuracion(Coordenadas coordenadasAgencia, double radioAdmitidoKm, List<ZonaRestringida> zonasRestringidas) {
        this.coordenadasAgencia = coordenadasAgencia;
        this.radioAdmitidoKm = radioAdmitidoKm;
        this.zonasRestringidas = zonasRestringidas;
    }

    // Getter y Setter para coordenadasAgencia
    public Coordenadas getCoordenadasAgencia() {
        return coordenadasAgencia;
    }

    public void setCoordenadasAgencia(Coordenadas coordenadasAgencia) {
        this.coordenadasAgencia = coordenadasAgencia;
    }

    // Getter y Setter para radioAdmitidoKm
    public double getRadioAdmitidoKm() {
        return radioAdmitidoKm;
    }

    public void setRadioAdmitidoKm(double radioAdmitidoKm) {
        this.radioAdmitidoKm = radioAdmitidoKm;
    }

    // Getter y Setter para zonasRestringidas
    public List<ZonaRestringida> getZonasRestringidas() {
        return zonasRestringidas;
    }

    public void setZonasRestringidas(List<ZonaRestringida> zonasRestringidas) {
        this.zonasRestringidas = zonasRestringidas;
    }

    @Override
    public String toString() {
        return "Configuracion{" +
                "coordenadasAgencia=" + coordenadasAgencia +
                ", radioAdmitidoKm=" + radioAdmitidoKm +
                ", zonasRestringidas=" + zonasRestringidas +
                '}';
    }
}



