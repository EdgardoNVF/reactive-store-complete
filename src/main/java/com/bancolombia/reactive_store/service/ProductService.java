package com.bancolombia.reactive_store.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;



import com.bancolombia.reactive_store.dto.ProductRequest;
import com.bancolombia.reactive_store.exception.ProductNotFoundException;
import com.bancolombia.reactive_store.mapper.ProductMapper;
import com.bancolombia.reactive_store.model.Product;
import com.bancolombia.reactive_store.repository.IProductRepositorySQL;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final IProductRepositorySQL repository;

    public Mono<Product> findById(Long id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new ProductNotFoundException("Producto no encontrado: " + id)))
                .map(ProductMapper::toProduct);
    }


    public Flux<Product> streamAllProducts() {
        return repository.findAll()
                .delayElements(Duration.ofMillis(500))
                .doOnNext(product -> log.debug("Streaming producto: {}", product.getName()))
                .map(ProductMapper::toProduct);
    }

    @Transactional
    public Mono<Product> save(ProductRequest request) {
        return repository.save(ProductMapper.toEntity(request))
                .map(ProductMapper::toProduct);
    }

    @Transactional
    public Mono<Product> update(Long id, ProductRequest request) {
        //Mono<Mono<Mono<T>>>
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new ProductNotFoundException("Producto no encontrado: " + id)))
                .flatMap(existing -> {
                    existing.setName(request.name());
                    existing.setDescription(request.description());
                    existing.setPrice(request.price());
                    existing.setStock(request.stock());
                    existing.setActive(request.active() != null ? request.active() : existing.getActive());
                    return repository.save(existing); //Mono<Mono<T>>->Mono<T>
                })
                .map(ProductMapper::toProduct);
    }

    @Transactional
    public Mono<Void> delete(Long id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new ProductNotFoundException("Producto no encontrado: " + id)))
                .flatMap(repository::delete);
    }


}
