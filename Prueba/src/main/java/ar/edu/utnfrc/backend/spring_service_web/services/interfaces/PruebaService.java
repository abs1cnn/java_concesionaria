package ar.edu.utnfrc.backend.spring_service_web.services.interfaces;

import ar.edu.utnfrc.backend.spring_service_web.entities.Prueba;

import java.util.List;

public interface PruebaService extends Service<Prueba, Integer> {
    List<Prueba> listarPruebasEnCurso(); // Método para listar pruebas en curso

    Prueba update(Integer id, Prueba entity); // Método para actualizar una prueba

    Prueba finalizarPrueba(Integer pruebaId, String comentario);
}
