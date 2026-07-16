package com.bancolombia.reactive_store.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.bancolombia.reactive_store.model.ProductEntity;

import reactor.core.publisher.Flux;

public interface IProductRepositorySQL extends ReactiveCrudRepository<ProductEntity, Long> {
   
    @Query("SELECT * FROM products WHERE category= :category")
    Flux<ProductEntity> findByCategory(String category);

}
