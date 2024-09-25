package api.orden.controllers;


import api.orden.model.dtos.StatusDto;
import api.orden.model.entities.Orden;
import api.orden.services.implementations.OrdenServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orden")
public class OrdenController {
    private OrdenServiceImpl ordenService;

    @Autowired
    public OrdenController(OrdenServiceImpl ordenService) {
        this.ordenService = ordenService;
    }

    @PostMapping()
    public ResponseEntity<Orden> save(@RequestBody Orden orden) {
        return new ResponseEntity<>(ordenService.save(orden), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Orden>> findById(@PathVariable Long id) {
        return new ResponseEntity<>(ordenService.findById(id), HttpStatus.FOUND);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Orden> updateStatus(@RequestBody StatusDto status, @PathVariable long id) {
        Orden updatedOrden = ordenService.updateStatus(status.getStatus(), id);
        return new ResponseEntity<>(updatedOrden, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Orden> update(@PathVariable Long id,@RequestBody Orden orden) {
        return ResponseEntity.ok(ordenService.update(id, orden));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        ordenService.deleteById(id);
        return new ResponseEntity<>("Se borro la orden correctamente",HttpStatus.OK);
    }
}
