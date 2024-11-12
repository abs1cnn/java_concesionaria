package ar.edu.utnfrc.backend.spring_service_web.configuracion;


public class Coordenadas {
    private double lat;
    private double lon;

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public Coordenadas(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }
    public Coordenadas() {

    }
}
