package ar.edu.utnfrc.backend.spring_service_web.controllers;

import ar.edu.utnfrc.backend.spring_service_web.entities.Client;
import ar.edu.utnfrc.backend.spring_service_web.services.interfaces.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/cliente")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    // Obtener todos los clientes
    @GetMapping
    public ResponseEntity<List<Client>> findAll() {
        return ResponseEntity.ok(clientService.findAll());
    }

    // Agregar un nuevo cliente
    @PostMapping
    public ResponseEntity<Client> addClient(@RequestBody Client client) {
        try {
            Client responseValue = clientService.create(client);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseValue);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // Obtener un cliente por ID
    @GetMapping("/{id}")
    public ResponseEntity<Client> findById(@PathVariable Integer id) {
        try {
            Client responseValue = clientService.findById(id);
            return ResponseEntity.ok(responseValue);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Actualizar un cliente por ID
    @PutMapping("/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable Integer id, @RequestBody Client client) {
        try {
            Client updatedClient = clientService.update(id, client);
            return ResponseEntity.ok(updatedClient);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // Eliminar un cliente por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Integer id) {
        try {
            clientService.delete(id);
            return ResponseEntity.noContent().build();  // Retorna 204 No Content si se eliminó correctamente
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
