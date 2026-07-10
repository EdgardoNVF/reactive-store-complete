package com.bancolombia.reactive_store.controller;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bancolombia.reactive_store.dto.ProductRequest;
import com.bancolombia.reactive_store.exception.ProductNotFoundException;
import com.bancolombia.reactive_store.model.Product;
import com.bancolombia.reactive_store.repository.ProductRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
@RequestMapping("/memory/products")
public class ProductController {

    private final ProductRepository repository;

    public ProductController(ProductRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public Flux<Product> findAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Product> findById(@PathVariable String id) {
        return repository.findById(id)
            .switchIfEmpty(Mono.error(new ProductNotFoundException(id)));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Product> create(@Valid @RequestBody ProductRequest request) {
        return repository.save(request);
    }

    @PutMapping("/{id}")
    public Mono<Product> update(
            @PathVariable String id,
            @Valid @RequestBody ProductRequest request) {
        return repository.update(id, request)
            .switchIfEmpty(Mono.error(new ProductNotFoundException(id)));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable String id) {
        return repository.deleteById(id)
            .map(deleted -> deleted
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Product> stream() {
        return repository.findAll().delayElements(Duration.ofSeconds(1));
    }
}