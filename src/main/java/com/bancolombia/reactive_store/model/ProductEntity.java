package com.bancolombia.reactive_store.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("products")
public class ProductEntity {

    @Id
    private Long id;

    @Column("name")
    private String name;

    @Column("description")
    private String description;

    @Column("category")
    private String category;

    @Column("price")
    private BigDecimal price;

    @Column("stock")
    private Integer stock;

    @Column("active")
    private Boolean active;

    @Column("created_at")
    private Instant createdAt;
}
