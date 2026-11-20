package co.com.nequi.caffeinecache;

import co.com.nequi.model.producto.Product;
import co.com.nequi.model.producto.cache.CacheGateway;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;

@Component
public class ReactiveCaffeineProductAdapter implements CacheGateway {

    private final Cache<String, Product> cache;

    public ReactiveCaffeineProductAdapter(@Value("${caffeine.expiration-millis}") long expirationMillis) {
        this.cache = Caffeine.newBuilder()
                .expireAfterWrite(expirationMillis, TimeUnit.MILLISECONDS)
                .build();
    }

    @Override
    public Mono<Void> saveProduct(Product product) {
        return Mono.fromRunnable(() -> cache.put(product.getId(), product));
    }

    @Override
    public Mono<Product> getProductById(String id) {
        return Mono.justOrEmpty(cache.getIfPresent(id));
    }
}
