package com.bancolombia.reactive_store.repository;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import com.bancolombia.reactive_store.dto.ProductRequest;
import com.bancolombia.reactive_store.model.Product;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class ProductRepository {
    private final Map<String, Product> store=new ConcurrentHashMap<>();

    public ProductRepository(){
        Product first =new Product(
            UUID.randomUUID().toString(),
            "Laptop Dell",
            "Equipo para gaming",
            new BigDecimal(1200),
            12,
            true
        );
        store.put(first.id(), first);
    }

    public Flux<Product> findAll(){
        return Flux.fromIterable(store.values());
    }

    public Mono<Product> findById(String id){
        return Mono.justOrEmpty(store.get(id));
    }

    public Mono<Product> save(ProductRequest request){

        return Mono.fromSupplier(()->{
                String id=UUID.randomUUID().toString();
                Product product=new Product(
                    id,
                    request.name(),
                    request.description(),
                    request.price(),
                    request.stock(),
                    request.active()
                );
                store.put(id, product);
                return product;
            });
    }

    public Mono<Product> update(String id, ProductRequest request){
        return findById(id).map(existing->{
            Product updated = new Product(
                id, 
                request.name(), 
                request.description(), 
                request.price(), 
                request.stock(), 
                request.active()
            );
            store.put(id, updated);
            return updated;
        });

    }

    public Mono<Boolean> deleteById(String id){
        return Mono.fromSupplier(()->store.remove(id)!=null);
    }


}
