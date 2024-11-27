package api.orden.services.implementations;

import api.orden.model.entities.Orden;
import api.orden.model.enums.Status;
import api.orden.model.interfaces.ProductClient;
import api.orden.repositories.OrdenRepository;
import api.orden.services.interfaces.IOrdenService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrdenServiceImpl implements IOrdenService {

    private final OrdenRepository ordenRepository;
    private final ProductClient productClient;

    @Override
    public Orden save(Orden orden) {
        return ordenRepository.save(orden);
    }

    @Override
    public Optional<Orden> findById(long id) {
        return ordenRepository.findById(id);
    }

    @Override
    public List<Orden> findAll() {
        return ordenRepository.findAll();
    }

    @Override
    public Orden updateStatus(Status status, long id) {
        Optional<Orden> orden = findById(id);
        orden.get().setStatus(status);
        return ordenRepository.save(orden.get());
    }

    @Override
    public Orden update(long id, Orden orden) {
        Optional<Orden> ordenOptional = ordenRepository.findById(id);
        if (!ordenOptional.isPresent()) {
            throw new EntityNotFoundException("Orden con ID " + id + " no encontrada");
        }
        Orden existingOrden = ordenOptional.get();
        if (orden.getProductoId() != null) {
            existingOrden.setProductoId(orden.getProductoId());
        }
        if (orden.getQuantity() != null) {
            existingOrden.setQuantity(orden.getQuantity());
        }
        if (orden.getStatus() != null) {
            existingOrden.setStatus(orden.getStatus());
        }
        if (orden.getUserId() != null) {
            existingOrden.setUserId(orden.getUserId());
        }
        return ordenRepository.save(existingOrden);
    }

    @Override
    public Boolean existProducto(Long id) {
        return productClient.existProducto(id);
    }


    @Override
    public void deleteById(long id) {
        Orden orden = ordenRepository.findById(id).get();
        ordenRepository.delete(orden);
    }
}
