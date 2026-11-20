package co.com.nequi.model.producto.cache;

import co.com.nequi.model.producto.Product;
import reactor.core.publisher.Mono;

public interface CacheGateway {
    Mono<Void> saveProduct(Product product);
    Mono<Product> getProductById(String id);
}