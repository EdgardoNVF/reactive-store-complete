package com.bancolombia.reactive_store.mapper;

import java.time.Instant;

import com.bancolombia.reactive_store.dto.ProductRequest;
import com.bancolombia.reactive_store.model.Product;
import com.bancolombia.reactive_store.model.ProductEntity;

public class ProductMapper {
    private ProductMapper() {
    }

    public static ProductEntity toEntity(ProductRequest request) {
        return ProductEntity.builder()
                .name(request.name())
                .description(request.description())
                .price(request.price())
                .stock(request.stock())
                .active(request.active() != null ? request.active() : true)
                .createdAt(Instant.now())
                .build();
    }

    public static Product toProduct(ProductEntity entity) {
        return new Product(
                entity.getId() != null ? entity.getId().toString() : null,
                entity.getName(),
                entity.getDescription(),
                entity.getPrice(),
                entity.getStock(),
                entity.getActive()
        );
    }

}
