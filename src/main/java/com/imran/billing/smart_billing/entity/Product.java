package com.imran.billing.smart_billing.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Entity @Data
@Table(name = "products")
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String category;

    @Column(nullable = false)
    private BigDecimal price;
}
