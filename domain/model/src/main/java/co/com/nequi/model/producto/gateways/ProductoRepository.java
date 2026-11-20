package co.com.nequi.model.producto.gateways;

import co.com.nequi.model.producto.Product;
import reactor.core.publisher.Mono;

public interface ProductoRepository {
    Mono<Product> getProductById(String id);
}
