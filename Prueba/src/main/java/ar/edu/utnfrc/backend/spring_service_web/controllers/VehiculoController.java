package ar.edu.utnfrc.backend.spring_service_web.controllers;
import ar.edu.utnfrc.backend.spring_service_web.entities.Vehiculo;
import ar.edu.utnfrc.backend.spring_service_web.services.interfaces.VehiculoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/vehiculos")
@RequiredArgsConstructor
public class VehiculoController {

    private final VehiculoService vehiculoService;

    // Obtener todos los vehículos
    @GetMapping
    public ResponseEntity<List<Vehiculo>> findAll() {
        return ResponseEntity.ok(vehiculoService.findAll());
    }

    // Agregar un nuevo vehículo
    @PostMapping
    public ResponseEntity<Vehiculo> addVehiculo(@RequestBody Vehiculo vehiculo) {
        try {
            Vehiculo responseValue = vehiculoService.create(vehiculo);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseValue);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // Obtener un vehículo por ID
    @GetMapping("/{id}")
    public ResponseEntity<Vehiculo> findById(@PathVariable Integer id) {
        try {
            Vehiculo responseValue = vehiculoService.findById(id);
            return ResponseEntity.ok(responseValue);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Actualizar un vehículo por ID
    @PutMapping("/{id}")
    public ResponseEntity<Vehiculo> updateVehiculo(@PathVariable Integer id, @RequestBody Vehiculo vehiculo) {
        try {
            Vehiculo updatedVehiculo = vehiculoService.update(id, vehiculo);
            return ResponseEntity.ok(updatedVehiculo);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // Eliminar un vehículo por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehiculo(@PathVariable Integer id) {
        try {
            vehiculoService.delete(id);
            return ResponseEntity.noContent().build();  // Retorna 204 No Content si se eliminó correctamente
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
