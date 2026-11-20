package co.com.nequi.usecase.producto;

import co.com.nequi.model.producto.Product;
import co.com.nequi.model.producto.cache.CacheGateway;
import co.com.nequi.model.producto.gateways.ProductoRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class ProductoUseCase {
    private final ProductoRepository productoRepository;
    private final CacheGateway cacheGateway;

    public Mono<Product> getProducto(String id){
        return cacheGateway.getProductById(id)
                .switchIfEmpty(productoRepository.getProductById(id)
                        .flatMap(product -> cacheGateway.saveProduct(product)
                                .thenReturn(product)));
    }
}
