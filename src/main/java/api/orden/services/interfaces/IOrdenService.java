package api.orden.services.interfaces;

import api.orden.model.entities.Orden;
import api.orden.model.enums.Status;

import java.util.List;
import java.util.Optional;

public interface IOrdenService {
    public Orden save(Orden orden);
    public Optional<Orden> findById(long id);
    public List<Orden> findAll();

    Orden updateStatus(Status status, long id);
    Orden update(long id, Orden orden);
    Boolean existProducto(Long id);

    public void deleteById(long id);
}
