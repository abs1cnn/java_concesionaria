package ar.edu.utnfrc.backend.spring_service_web.configuracion;


public class ZonaRestringida {
    private Coordenadas noroeste;
    private Coordenadas sureste;

    public Coordenadas getNoroeste() {
        return noroeste;
    }

    public Coordenadas getSureste() {
        return sureste;
    }

    public void setNoroeste(Coordenadas noroeste) {
        this.noroeste = noroeste;
    }

    public void setSureste(Coordenadas sureste) {
        this.sureste = sureste;
    }

    public ZonaRestringida(Coordenadas noroeste, Coordenadas sureste) {
        this.noroeste = noroeste;
        this.sureste = sureste;
    }
}