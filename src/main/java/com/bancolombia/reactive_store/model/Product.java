package com.bancolombia.reactive_store.model;

import java.math.BigDecimal;

public record Product(
    String id,
    String name,
    String description,
    BigDecimal price,
    int stock,
    boolean active
) {
    
}
