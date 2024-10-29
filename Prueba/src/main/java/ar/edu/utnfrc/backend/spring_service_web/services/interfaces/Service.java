package ar.edu.utnfrc.backend.spring_service_web.services.interfaces;

import java.util.List;

public interface Service<T, K> {
    T create(T entity);
    T update(Integer id, T entity);
    void delete(K id);
    T findById(K id);
    List<T> findAll();
}
