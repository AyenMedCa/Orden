package api.orden.model.interfaces;

import api.orden.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "producto", configuration = FeignConfig.class, path = "/producto")
public interface ProductClient {
    @GetMapping("{id}/exist")
    Boolean existProducto(@PathVariable("id") Long id);
}
