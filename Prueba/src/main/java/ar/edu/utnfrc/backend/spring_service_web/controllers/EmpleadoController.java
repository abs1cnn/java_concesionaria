package ar.edu.utnfrc.backend.spring_service_web.controllers;

import ar.edu.utnfrc.backend.spring_service_web.entities.Empleado;
import ar.edu.utnfrc.backend.spring_service_web.services.interfaces.EmpleadoService; // Asegúrate de crear la interfaz EmpleadoService
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/empleado")  // Cambiado a /empleado para reflejar la entidad
@RequiredArgsConstructor
public class EmpleadoController {

    private final EmpleadoService empleadoService; // Cambiado a empleadoService

    // Obtener todos los empleados
    @GetMapping
    public ResponseEntity<List<Empleado>> findAll() {
        return ResponseEntity.ok(empleadoService.findAll());
    }

    // Agregar un nuevo empleado
    @PostMapping
    public ResponseEntity<Empleado> addEmpleado(@RequestBody Empleado empleado) {
        try {
            Empleado responseValue = empleadoService.create(empleado);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseValue);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // Obtener un empleado por legajo
    @GetMapping("/{legajo}")
    public ResponseEntity<Empleado> findByLegajo(@PathVariable Integer legajo) {
        try {
            Empleado responseValue = empleadoService.findById(legajo);
            return ResponseEntity.ok(responseValue);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Actualizar un empleado por legajo
    @PutMapping("/{legajo}")
    public ResponseEntity<Empleado> updateEmpleado(@PathVariable Integer legajo, @RequestBody Empleado empleado) {
        try {
            Empleado updatedEmpleado = empleadoService.update(legajo, empleado);
            return ResponseEntity.ok(updatedEmpleado);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // Eliminar un empleado por legajo
    @DeleteMapping("/{legajo}")
    public ResponseEntity<Void> deleteEmpleado(@PathVariable Integer legajo) {
        try {
            empleadoService.delete(legajo);
            return ResponseEntity.noContent().build();  // Retorna 204 No Content si se eliminó correctamente
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
