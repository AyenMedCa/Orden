    package api.orden.controllers;
    
    
    import api.orden.model.dtos.StatusDto;
    import api.orden.model.entities.Orden;
    import api.orden.services.implementations.OrdenServiceImpl;
    import lombok.RequiredArgsConstructor;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.security.access.prepost.PreAuthorize;
    import org.springframework.web.bind.annotation.*;

    import java.util.List;
    import java.util.Optional;
    
    @RestController
    @RequiredArgsConstructor
    @RequestMapping("orden")
    public class OrdenController {
        private OrdenServiceImpl ordenService;
    
        @Autowired
        public OrdenController(OrdenServiceImpl ordenService) {
            this.ordenService = ordenService;
        }
    
        @PostMapping()
        @PreAuthorize("hasAnyRole('SELLER', 'ADMIN', 'USER')")
        public ResponseEntity<?> save(@RequestBody Orden orden) {
            Boolean exist = ordenService.existProducto(orden.getProductoId());
            if (exist){
                return new ResponseEntity<>(ordenService.save(orden), HttpStatus.CREATED);
            }else {
                return new ResponseEntity<>("El producto no se encontro en la bd", HttpStatus.NOT_FOUND);
            }
        }
    
        @GetMapping("/{id}")
        @PreAuthorize("hasAnyRole('SELLER', 'ADMIN', 'USER')")
        public ResponseEntity<Optional<Orden>> findById(@PathVariable Long id) {
            Optional<Orden> orden = ordenService.findById(id);
            if (orden.isPresent()) {
                return new ResponseEntity<>(orden, HttpStatus.OK);
            }else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
    
        @PatchMapping("/{id}")
        @PreAuthorize("hasAnyRole('SELLER', 'ADMIN')")
        public ResponseEntity<Orden> updateStatus(@RequestBody StatusDto status, @PathVariable long id) {
            Optional<Orden> orden = ordenService.findById(id);
            if (orden.isPresent()) {
                Orden updatedOrden = ordenService.updateStatus(status.getStatus(), id);
                return new ResponseEntity<>(updatedOrden, HttpStatus.OK);
            }else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
    
        @PutMapping("/{id}")
        @PreAuthorize("hasAnyRole('SELLER', 'ADMIN', 'USER')")
        public ResponseEntity<?> update(@PathVariable Long id,@RequestBody Orden orden) {
            Optional<Orden> ordenUpdated = ordenService.findById(id);
            if (ordenUpdated.isPresent()) {
                return ResponseEntity.ok(ordenService.update(id, orden));
            }else {
                return new ResponseEntity<>("No se encontro la orden",HttpStatus.NOT_FOUND);
            }
        }
    
        @DeleteMapping("/{id}")
        @PreAuthorize("hasAnyRole('SELLER', 'ADMIN', 'USER')")
        public ResponseEntity<?> deleteById(@PathVariable Long id) {
            Optional<Orden> orden = ordenService.findById(id);
            if (orden.isPresent()) {
                ordenService.deleteById(id);
                return new ResponseEntity<>("Se borro la orden correctamente",HttpStatus.OK);
            }else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }

        @GetMapping()
        @PreAuthorize("hasAnyRole('SELLER', 'ADMIN')")
        public ResponseEntity<?> findAll() {
            List<Orden> ordens = ordenService.findAll();
            if (ordens.isEmpty()){
                return new ResponseEntity<>("No hay ordenes en la bd",HttpStatus.NO_CONTENT);
            }else{
                return new ResponseEntity<>(ordens, HttpStatus.OK);
            }
        }
    }
