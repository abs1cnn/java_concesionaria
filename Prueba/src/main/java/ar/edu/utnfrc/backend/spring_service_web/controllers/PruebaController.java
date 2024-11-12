package ar.edu.utnfrc.backend.spring_service_web.controllers;

import ar.edu.utnfrc.backend.spring_service_web.entities.Prueba;
import ar.edu.utnfrc.backend.spring_service_web.services.interfaces.PruebaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/prueba")

public class PruebaController {

    private final PruebaService pruebaService;

    // Obtener todas las pruebas
    @GetMapping
    public ResponseEntity<List<Prueba>> findAll() {
        return ResponseEntity.ok(pruebaService.findAll());
    }

    // Agregar una nueva prueba
    @PostMapping
    public ResponseEntity<Prueba> addPrueba(@RequestBody Prueba prueba) {
        try {
            Prueba responseValue = pruebaService.create(prueba);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseValue);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // Obtener una prueba por ID
    @GetMapping("/{id}")
    public ResponseEntity<Prueba> findById(@PathVariable Integer id) {
        try {
            Prueba responseValue = pruebaService.findById(id);
            return ResponseEntity.ok(responseValue);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Actualizar una prueba por ID
    @PutMapping("/{id}")
    public ResponseEntity<Prueba> updatePrueba(@PathVariable Integer id, @RequestBody Prueba prueba) {
        try {
            Prueba updatedPrueba = pruebaService.update(id, prueba);
            return ResponseEntity.ok(updatedPrueba);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    //Finalizar prueba
    @PutMapping("/{id}/finalizar")
    public ResponseEntity<Prueba> finalizarPrueba(
            @PathVariable Integer id,
            @RequestBody Map<String, String> body) {
        String comentario = body.get("comentario");
        Prueba pruebaFinalizada = pruebaService.finalizarPrueba(id, comentario);
        return ResponseEntity.ok(pruebaFinalizada);
    }

    // Eliminar una prueba por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePrueba(@PathVariable Integer id) {
        try {
            pruebaService.delete(id);
            return ResponseEntity.noContent().build();  // Retorna 204 No Content si se eliminó correctamente
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }



    public PruebaController(PruebaService pruebaService) {
        this.pruebaService = pruebaService;
    }

    @GetMapping("/en-curso")
    public List<Prueba> listarPruebasEnCurso() {
        return pruebaService.listarPruebasEnCurso();
    }



    // Validar la posición de un vehículo usando PathVariable
    @GetMapping("/validar-posicion/{lat}/{lon}/{id}")
    public ResponseEntity<String> validarPosicionVehiculo(
            @PathVariable double lat,
            @PathVariable double lon,
            @PathVariable Integer id) {
        try {
            pruebaService.validarPosicionVehiculo(lat, lon, id);
            return ResponseEntity.ok("El vehiculo se encuentra en zona segura.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


}
