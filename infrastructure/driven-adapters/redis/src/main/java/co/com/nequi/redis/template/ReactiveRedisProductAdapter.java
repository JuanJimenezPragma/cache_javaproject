package co.com.nequi.redis.template;

import co.com.nequi.model.producto.Product;
import co.com.nequi.model.producto.cache.CacheGateway;
import co.com.nequi.redis.template.entity.ProductEntity;
import co.com.nequi.redis.template.helper.ReactiveTemplateAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class ReactiveRedisProductAdapter extends ReactiveTemplateAdapterOperations<Product, String, ProductEntity> implements CacheGateway{
    private final long expirationMillis;

    public ReactiveRedisProductAdapter(ReactiveRedisConnectionFactory connectionFactory, ObjectMapper mapper,
                                       @Value("${spring.data.redis.expirationMillis}") long expirationMillis) {
        this.expirationMillis = expirationMillis;
        super(connectionFactory, mapper, d -> mapper.map(d, Product.class));
    }

    @Override
    public Mono<Void> saveProduct(Product product) {
        return save(String.valueOf(product.getId()), product, expirationMillis)
                .then();
    }

    @Override
    public Mono<Product> getProductById(String id) {
        return findById(id);
    }
}
